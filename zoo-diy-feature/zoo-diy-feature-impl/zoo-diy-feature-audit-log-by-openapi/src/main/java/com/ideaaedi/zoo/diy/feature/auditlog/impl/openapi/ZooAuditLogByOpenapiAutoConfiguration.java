package com.ideaaedi.zoo.diy.feature.auditlog.impl.openapi;

import com.ideaaedi.zoo.diy.feature.auditlog.api.ZooAuditLogApiAutoConfiguration;
import com.ideaaedi.zoo.diy.feature.auditlog.impl.openapi.collector.OpenapiAuditLogCollector;
import com.ideaaedi.zoo.diy.feature.auditlog.impl.openapi.properties.ZooAuditLogByOpenapiDIYGuideProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 基于openapi3的审计日志 自动配置
 */
@Slf4j
@AutoConfigureAfter(ZooAuditLogApiAutoConfiguration.class)
@EnableConfigurationProperties({ZooAuditLogByOpenapiDIYGuideProperties.class})
public class ZooAuditLogByOpenapiAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public OpenapiAuditLogCollector openapiAuditLogCollector() {
        return new OpenapiAuditLogCollector();
    }
    
}
