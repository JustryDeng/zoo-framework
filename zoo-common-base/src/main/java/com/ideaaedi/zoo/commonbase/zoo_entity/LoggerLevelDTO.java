package com.ideaaedi.zoo.commonbase.zoo_entity;

import lombok.Data;
import org.springframework.boot.logging.LogLevel;

/**
 * 日志级别信息
 */
@Data
public class LoggerLevelDTO {
    
    /**
     * 日志记录器
     */
    private String logger;
    
    /**
     * 日志级别
     */
    private LogLevel level;
    
    public static LoggerLevelDTO of(String logger, LogLevel level) {
        LoggerLevelDTO loggerLevel = new LoggerLevelDTO();
        loggerLevel.setLogger(logger);
        loggerLevel.setLevel(level);
        return loggerLevel;
    }
}
