package com.ideaaedi.zoo.diy.artifact.openfeign;

import com.ideaaedi.zoo.diy.artifact.openfeign.core.OpenfeignCompatibleConfig;
import com.ideaaedi.zoo.diy.artifact.openfeign.core.OpenfeignRequestInterceptor;
import com.ideaaedi.zoo.diy.artifact.openfeign.properties.ZooOpenfeignDIYGuideProperties;
import com.ideaaedi.zoo.diy.artifact.openfeign.properties.ZooOpenfeignProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * open feign 自动配置
 */
@Slf4j
@EnableConfigurationProperties({ZooOpenfeignDIYGuideProperties.class, ZooOpenfeignProperties.class})
@ImportAutoConfiguration(classes = OpenfeignCompatibleConfig.class)
public class ZooOpenfeignAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public OpenfeignRequestInterceptor openfeignRequestInterceptor(ZooOpenfeignProperties zooOpenfeignProperties,
                                                                   Environment environment) {
        return new OpenfeignRequestInterceptor(zooOpenfeignProperties, environment);
    }
}