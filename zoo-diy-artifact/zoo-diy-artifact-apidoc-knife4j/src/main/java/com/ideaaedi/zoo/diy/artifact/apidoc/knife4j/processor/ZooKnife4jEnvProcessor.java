package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooKnife4jEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-knife4j.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-knife4j.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
