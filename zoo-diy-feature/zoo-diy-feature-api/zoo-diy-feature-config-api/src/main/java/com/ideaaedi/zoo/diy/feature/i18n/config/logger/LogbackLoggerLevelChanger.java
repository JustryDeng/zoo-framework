package com.ideaaedi.zoo.diy.feature.i18n.config.logger;

import ch.qos.logback.classic.Level;
import com.ideaaedi.zoo.commonbase.zoo_entity.LoggerLevelDTO;
import com.ideaaedi.zoo.commonbase.zoo_event.LoggerLevelChangeEvent;
import com.ideaaedi.zoo.diy.feature.i18n.config.properties.AppStartedLoggerLevelProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * logback 日志级别修改器
 */
@Slf4j
public class LogbackLoggerLevelChanger implements ApplicationListener<LoggerLevelChangeEvent>, SmartInitializingSingleton {
    
    /**
     * <pre>
     * key - logger
     * value - level
     * </pre>
     */
    private final Map<String, String> prevLoggerLevelMap = new ConcurrentHashMap<>(8);
    
    /**
     * 应用启动时的logger level
     */
    private final AppStartedLoggerLevelProperties appStartedLoggerLevelProperties;
    
    public LogbackLoggerLevelChanger(AppStartedLoggerLevelProperties appStartedLoggerLevelProperties) {
        this.appStartedLoggerLevelProperties = appStartedLoggerLevelProperties;
    }
    
    @Async
    @Override
    public synchronized void onApplicationEvent(@Nonnull LoggerLevelChangeEvent loggerLevelChangeEvent) {
        try {
            doChange(loggerLevelChangeEvent);
        } catch (Exception e) {
            log.warn("change logger level occur exception", e);
        }
    }
    
    /**
     * 修改日志级别
     */
    private void doChange(LoggerLevelChangeEvent loggerLevelChangeEvent) {
        List<LoggerLevelDTO> lastestLoggerLevelList = loggerLevelChangeEvent.getLastestLoggerLevelList();
        if (CollectionUtils.isEmpty(lastestLoggerLevelList)) {
            return;
        }
        Map<String, LogLevel> lastestLoggerLevelMap = lastestLoggerLevelList.stream()
                .collect(
                        Collectors.toMap(LoggerLevelDTO::getLogger, LoggerLevelDTO::getLevel, (pre, next) -> next)
                );
        Map<String, LogLevel> changedLoggerMap = determineChangeLogger(lastestLoggerLevelMap);
        if (CollectionUtils.isEmpty(changedLoggerMap)) {
            log.info("logger level no change. ignore curr logger-level-change-event.");
            return;
        }
        Map<String, String> finalChangedLoggerMap = new HashMap<>(16);
        ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
        changedLoggerMap.forEach((logger, level) -> {
            Logger loggerInstance = iLoggerFactory.getLogger(logger);
            if (loggerInstance instanceof ch.qos.logback.classic.Logger logbackLogger) {
                Level newLevel = Level.toLevel(level.name());
                logbackLogger.setLevel(newLevel);
                String newLevelStr = String.valueOf(newLevel);
                prevLoggerLevelMap.put(logger, newLevelStr);
                finalChangedLoggerMap.put(logger, newLevelStr);
            }
        });
        log.info("change logger level. {}", finalChangedLoggerMap);
    }
    
    /**
     * 获取发生了变化的日志logger
     */
    private Map<String, LogLevel> determineChangeLogger(@Nonnull Map<String, LogLevel> lastestLoggerLevelMap) {
        Map<String, LogLevel> hasChangeMap = new TreeMap<>(Comparator.comparing(String::length));
        Set<Map.Entry<String, LogLevel>> entrySet = lastestLoggerLevelMap.entrySet();
        for (Map.Entry<String, LogLevel> entry : entrySet) {
            String logger = entry.getKey();
            LogLevel level = entry.getValue();
            String prevLevel = prevLoggerLevelMap.get(logger);
            if (level.name().equalsIgnoreCase(prevLevel)) {
                continue;
            }
            hasChangeMap.put(logger, level);
        }
        return hasChangeMap;
    }
    
    /**
     * init
     */
    @Override
    public void afterSingletonsInstantiated() {
        Map<String, LogLevel> level = appStartedLoggerLevelProperties.getLevel();
        if (CollectionUtils.isEmpty(level)) {
            return;
        }
        level.forEach((logger, levelInstance) -> {
            prevLoggerLevelMap.put(logger, levelInstance.name());
        });
    }
}
