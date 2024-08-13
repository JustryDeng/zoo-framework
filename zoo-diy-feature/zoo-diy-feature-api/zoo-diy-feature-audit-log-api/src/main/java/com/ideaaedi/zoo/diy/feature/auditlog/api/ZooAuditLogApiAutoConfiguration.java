package com.ideaaedi.zoo.diy.feature.auditlog.api;

import com.ideaaedi.zoo.diy.feature.auditlog.api.advice.AuditLogPointcutAdvisor;
import com.ideaaedi.zoo.diy.feature.auditlog.api.converter.ZooStringClassConverter;
import com.ideaaedi.zoo.diy.feature.auditlog.api.properties.ZooAuditLogApiProperties;
import com.ideaaedi.zoo.diy.feature.auditlog.api.spi.AuditLogCollector;
import com.ideaaedi.zoo.diy.feature.auditlog.api.spi.AuditLogRecorder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 审计日志抽象逻辑 自动配置
 */
@EnableConfigurationProperties(ZooAuditLogApiProperties.class)
public class ZooAuditLogApiAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public ZooStringClassConverter zooStringClassConverter() {
        return new ZooStringClassConverter();
    }
    
    @Bean
    public AuditLogPointcutAdvisor auditLogPointcutAdvisor(
            @SuppressWarnings("rawtypes") AuditLogCollector auditLogCollector,
            AuditLogRecorder auditLogRecorder, ZooAuditLogApiProperties zooAuditLogApiEnvProcessor) {
        return new AuditLogPointcutAdvisor(auditLogCollector, auditLogRecorder, zooAuditLogApiEnvProcessor);
    }
}
