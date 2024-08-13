package com.ideaaedi.zoo.diy.artifact.openfeign.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooOpenfeignEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-openfeign.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-openfeign.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
