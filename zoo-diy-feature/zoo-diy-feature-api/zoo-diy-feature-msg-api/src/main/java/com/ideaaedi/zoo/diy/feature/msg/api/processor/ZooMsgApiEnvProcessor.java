package com.ideaaedi.zoo.diy.feature.msg.api.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooMsgApiEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-msg-api.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-msg-api.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
