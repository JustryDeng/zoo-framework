package com.ideaaedi.zoo.diy.feature.auditlog.impl.openapi.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooAuditLogByOpenapiEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-auditlog-by-openapi.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-auditlog-by-openapi.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}