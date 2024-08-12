package com.ideaaedi.zoo.diy.feature.fieldperm.impl.openapi;

import com.ideaaedi.zoo.diy.feature.fieldperm.impl.openapi.core.OpenapiMethodArgFieldParser;
import com.ideaaedi.zoo.diy.feature.fieldperm.impl.openapi.properties.ZooFpByOpenapiDIYGuideProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterNameDiscoverer;

/**
 * 基于openapi的字段权限 自动配置
 */
@EnableConfigurationProperties({ZooFpByOpenapiDIYGuideProperties.class})
public class ZooFpByOpenapiAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public OpenapiMethodArgFieldParser openapiMethodArgFieldParser(@Autowired(required = false) ParameterNameDiscoverer parameterNameDiscoverer) {
        return new OpenapiMethodArgFieldParser(parameterNameDiscoverer);
    }
    
}