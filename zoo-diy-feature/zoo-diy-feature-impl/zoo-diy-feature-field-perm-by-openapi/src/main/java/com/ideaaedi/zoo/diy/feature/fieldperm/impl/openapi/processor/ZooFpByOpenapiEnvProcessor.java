package com.ideaaedi.zoo.diy.feature.fieldperm.impl.openapi.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooFpByOpenapiEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-fieldperm-openapi.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-fieldperm-openapi.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
