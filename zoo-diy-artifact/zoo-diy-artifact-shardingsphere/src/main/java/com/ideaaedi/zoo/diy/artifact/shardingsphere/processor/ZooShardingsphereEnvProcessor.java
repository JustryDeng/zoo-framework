package com.ideaaedi.zoo.diy.artifact.shardingsphere.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooShardingsphereEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-shardingsphere.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-shardingsphere.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
