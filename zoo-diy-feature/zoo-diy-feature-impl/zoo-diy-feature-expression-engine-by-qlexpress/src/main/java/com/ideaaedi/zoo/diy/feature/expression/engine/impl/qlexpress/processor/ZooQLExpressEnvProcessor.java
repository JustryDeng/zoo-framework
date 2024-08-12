package com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooQLExpressEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-expengine-qlexpress.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-expengine-qlexpress.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
