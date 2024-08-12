package com.ideaaedi.zoo.diy.feature.litefeature.api.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooLiteFeatureApiEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-litefeature-api.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-litefeature-api.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
