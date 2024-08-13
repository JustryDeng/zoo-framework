package com.ideaaedi.zoo.diy.feature.config.impl.nacos;

import com.ideaaedi.zoo.diy.feature.config.impl.nacos.listener.NacosConfigChangeListener;
import com.ideaaedi.zoo.diy.feature.config.impl.nacos.properties.NacosLoggerLevelProperties;
import com.ideaaedi.zoo.diy.feature.config.impl.nacos.properties.ZooConfigNacosDIYGuideProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * nacos config 自动配置
 */
@EnableConfigurationProperties({ZooConfigNacosDIYGuideProperties.class, NacosLoggerLevelProperties.class})
public class ZooConfigNacosAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public NacosConfigChangeListener nacosConfigChangeListener(ApplicationContext applicationContext, NacosLoggerLevelProperties nacosLoggerLevelProperties) {
        return new NacosConfigChangeListener(applicationContext, nacosLoggerLevelProperties);
    }
}