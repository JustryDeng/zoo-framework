package com.ideaaedi.zoo.diy.feature.reqresp.encdec.impl.springmvc.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooReByBodyAdviceEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-reqresp-encdec-aspect.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-reqresp-encdec-springmvc.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
