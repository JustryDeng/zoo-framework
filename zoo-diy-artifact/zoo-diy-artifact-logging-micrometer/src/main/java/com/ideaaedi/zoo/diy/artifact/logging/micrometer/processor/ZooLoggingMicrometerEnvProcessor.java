package com.ideaaedi.zoo.diy.artifact.logging.micrometer.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooLoggingMicrometerEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-micrometer.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-micrometer.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
