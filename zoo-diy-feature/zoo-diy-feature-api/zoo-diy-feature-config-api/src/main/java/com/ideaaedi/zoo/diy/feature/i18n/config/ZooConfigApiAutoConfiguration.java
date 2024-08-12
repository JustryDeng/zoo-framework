package com.ideaaedi.zoo.diy.feature.i18n.config;

import com.ideaaedi.zoo.diy.feature.i18n.config.logger.LogbackLoggerLevelChanger;
import com.ideaaedi.zoo.diy.feature.i18n.config.properties.AppStartedLoggerLevelProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * config 自动配置
 */
@EnableAsync
@EnableConfigurationProperties(AppStartedLoggerLevelProperties.class)
public class ZooConfigApiAutoConfiguration {
    
    @Bean
    @ConditionalOnClass(ch.qos.logback.classic.Level.class)
    public LogbackLoggerLevelChanger logbackLoggerLevelChanger(AppStartedLoggerLevelProperties appStartedLoggerLevelProperties) {
        return new LogbackLoggerLevelChanger(appStartedLoggerLevelProperties);
    }
}