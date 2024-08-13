package com.ideaaedi.zoo.diy.artifact.grayscale.aspect.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooGrayscaleAspectEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-artifact-grayscale-aspect.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-artifact-grayscale-aspect.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
