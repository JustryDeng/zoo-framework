package com.ideaaedi.zoo.diy.feature.config.impl.nacos.listener;

import com.ideaaedi.zoo.commonbase.zoo_entity.LoggerLevelDTO;
import com.ideaaedi.zoo.commonbase.zoo_event.LoggerLevelChangeEvent;
import com.ideaaedi.zoo.diy.feature.config.impl.nacos.properties.NacosLoggerLevelProperties;
import org.springframework.boot.logging.LogLevel;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NacosConfigChangeListener implements ApplicationListener<RefreshEvent> {
    
    private final ApplicationContext applicationContext;
    
    private final NacosLoggerLevelProperties nacosLoggerLevelProperties;
    
    public NacosConfigChangeListener(ApplicationContext applicationContext,
                                     NacosLoggerLevelProperties nacosLoggerLevelProperties) {
        this.applicationContext = applicationContext;
        this.nacosLoggerLevelProperties = nacosLoggerLevelProperties;
    }
    
    @Override
    public void onApplicationEvent(@Nonnull RefreshEvent event) {
        Map<String, LogLevel> loggerLevelMap = nacosLoggerLevelProperties.getLevel();
        if (CollectionUtils.isEmpty(loggerLevelMap)) {
            return;
        }
    
        /*
         * RefreshEventListener#handle(org.springframework.cloud.endpoint.event.RefreshEvent) 动作在此方法之后，
         *
         * 这里延迟几秒再异步 发送LoggerLevelChangeEvent事件
         */
        Thread delayThread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(nacosLoggerLevelProperties.getDelayRefresh());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            applicationContext.publishEvent(
                    new LoggerLevelChangeEvent(loggerLevelMap.entrySet().stream().map(x -> LoggerLevelDTO.of(x.getKey(), x.getValue())).toList())
            );
        });
        delayThread.start();
    }
}
