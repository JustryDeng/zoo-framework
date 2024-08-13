package com.ideaaedi.zoo.diy.artifact.websocket.processor;

import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooWebsocketEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-websocket.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-websocket.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
