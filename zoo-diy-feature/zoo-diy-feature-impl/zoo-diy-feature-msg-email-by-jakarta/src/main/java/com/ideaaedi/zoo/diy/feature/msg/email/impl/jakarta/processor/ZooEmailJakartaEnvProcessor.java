package com.ideaaedi.zoo.diy.feature.msg.email.impl.jakarta.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooEmailJakartaEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-msg-email-jakarta.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-msg-email-jakarta.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
