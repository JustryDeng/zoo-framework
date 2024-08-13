package com.ideaaedi.zoo.diy.artifact.generator.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooGeneratorEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-generator.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-generator.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
