package com.ideaaedi.zoo.commonbase.constant;

import org.springframework.core.Ordered;

/**
 * AutoConfiguration 的顺序
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface AutoConfigurationConstant {
    
    /*
     * order for ZooMybatisPlusAutoConfiguration
     */
    int ZOO_MYBATIS_PLUS_AUTO_CONFIGURATION_ORDER = 100;
    
    /*
     * order for ZooTenantLikeAutoConfiguration
     */
    int ZOO_TENANT_LIKE_AUTO_CONFIGURATION_ORDER = ZOO_MYBATIS_PLUS_AUTO_CONFIGURATION_ORDER - 1;
    
    /*
     * bean-name for ZooTenantLikeAutoConfiguration
     */
    String ZOO_TENANT_LIKE_AUTO_CONFIGURATION_BEAN_NAME = "com.ideaaedi.zoo.diy.artifact.tenantlike.ZooTenantLikeAutoConfiguration";
    
    /*
     * bean-name for MybatisPlusInterceptor
     */
    String MYBATIS_PLUS_INTERCEPTOR_BEAN_NAME = "mybatisPlusInterceptor";
    
    /*
     * order for DefaultExceptionHandler
     */
    int DEFAULT_EXCEPTION_HANDLER_ORDER = Ordered.LOWEST_PRECEDENCE;
    
    /*
     * order for ZooSaTokenExceptionHandler
     */
    int ZOO_SA_TOKEN_EXCEPTION_HANDLER_ORDER = DEFAULT_EXCEPTION_HANDLER_ORDER - 1;
    
    /*
     * order for logging.micrometer TraceXdFilter
     */
    int ZOO_LOGGING_MICROMETER_PRIORITY_ORDER = Ordered.HIGHEST_PRECEDENCE;
    
    /*
     * order for sa-token SaServletFilter
     */
    int ZOO_SA_TOKEN_AUTH_FILTER_ORDER = 100;
    
    /*
     * order for audit log
     */
    int ZOO_AUDIT_LOG_COLLECTOR_ORDER = Ordered.HIGHEST_PRECEDENCE + 100;
    
    /*
     * order for field permission
     */
    int ZOO_FIELD_PERM_VALIDATOR_ORDER = Ordered.HIGHEST_PRECEDENCE + 200;
    
    /*
     * order for field LockAdvice
     */
    int ZOO_LITE_FEATURE_LOCK_ADVICE_ORDER = Ordered.HIGHEST_PRECEDENCE + 10000;
    
    /*
     * grayscale
     */
    int ZOO_GRAYSCALE_ASPECT_ORDER = Ordered.LOWEST_PRECEDENCE - 100;
    
    /*
     * order for expression interceptor
     */
    int ZOO_EXPRESSION_INTERCEPTOR_ORDER = Ordered.LOWEST_PRECEDENCE - 1;
    
    /*
     * order for ZooKnife4jAutoConfiguration
     */
    int ZOO_KNIFE4J_AUTO_CONFIGURATION_ORDER = 0;
    
    /*
     * order for ZooGeneratorAutoConfiguration
     */
    int ZOO_GENERATOR_AUTO_CONFIGURATION_ORDER = ZOO_MYBATIS_PLUS_AUTO_CONFIGURATION_ORDER + 1;
    
    /**
     * ZooKnife4jAutoConfiguration 的全类名
     */
    String ZOO_KNIFE4J_AUTO_CONFIGURATION_CLASS_LONG_NAME = "com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.ZooKnife4jAutoConfiguration";
}
