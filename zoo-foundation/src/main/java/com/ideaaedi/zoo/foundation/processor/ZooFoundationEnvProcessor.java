package com.ideaaedi.zoo.foundation.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooFoundationEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-foundation.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-foundation.default.yml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
