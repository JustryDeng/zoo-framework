package com.ideaaedi.zoo.diy.feature.auditlog.api.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 * zoo对openapi3的增强配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = "zoo.audit-log")
public class ZooAuditLogApiProperties {
    
    /*
     * 记录请求参数时，要忽略的参数类型
     */
    private Set<Class<?>> ignoreRequestTypes;
    
    /*
     * 记录响应结果时，要忽略的结果类型
     */
    private Set<Class<?>> ignoreResponseTypes;

}
