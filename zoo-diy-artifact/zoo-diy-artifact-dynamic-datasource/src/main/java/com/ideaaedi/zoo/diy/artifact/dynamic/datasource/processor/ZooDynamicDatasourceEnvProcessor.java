package com.ideaaedi.zoo.diy.artifact.dynamic.datasource.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooDynamicDatasourceEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-dynamic-datasource.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-dynamic-datasource.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
