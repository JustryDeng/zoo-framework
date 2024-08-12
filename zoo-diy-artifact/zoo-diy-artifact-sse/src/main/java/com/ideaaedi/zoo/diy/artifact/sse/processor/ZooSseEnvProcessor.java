package com.ideaaedi.zoo.diy.artifact.sse.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooSseEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-sse.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-sse.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
