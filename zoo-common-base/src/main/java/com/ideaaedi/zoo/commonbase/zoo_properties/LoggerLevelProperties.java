package com.ideaaedi.zoo.commonbase.zoo_properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.logging.LogLevel;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "logging")
@SuppressWarnings("ConfigurationProperties")
public class  LoggerLevelProperties {
    
    /**
     * 日志及日志级别
     * <pre>
     *  key   - logger
     *  value - 该logger的日志级别
     * </pre>
     */
    private Map<String, LogLevel> level;
}
