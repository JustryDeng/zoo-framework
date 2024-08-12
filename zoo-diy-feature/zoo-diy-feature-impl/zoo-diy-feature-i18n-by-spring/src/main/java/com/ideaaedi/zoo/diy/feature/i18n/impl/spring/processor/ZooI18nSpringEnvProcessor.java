package com.ideaaedi.zoo.diy.feature.i18n.impl.spring.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooI18nSpringEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-i18n-spring.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-i18n-spring.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
