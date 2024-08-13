package com.ideaaedi.zoo.diy.artifact.tenantlike.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooTenantLikeEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-tenant-like.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-tenant-like.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
