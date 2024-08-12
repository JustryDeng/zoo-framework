package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooBpmnByFlowableEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-bpmn-by-flowable.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-bpmn-by-flowable.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
