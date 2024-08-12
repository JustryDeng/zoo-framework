package com.ideaaedi.zoo.diy.feature.msg.sms.impl.sms4j.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooSmsSms4jEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-msg-sms4j.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-msg-sms4j.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
