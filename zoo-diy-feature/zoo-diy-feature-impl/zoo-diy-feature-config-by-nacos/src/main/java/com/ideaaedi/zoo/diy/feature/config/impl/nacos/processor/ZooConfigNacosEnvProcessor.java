package com.ideaaedi.zoo.diy.feature.config.impl.nacos.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooConfigNacosEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-config-nacos.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-config-nacos.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
