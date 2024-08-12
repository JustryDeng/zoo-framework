package com.ideaaedi.zoo.diy.feature.file.impl.xfilestorage.processor;


import com.ideaaedi.zoo.commonbase.processor.ZooEnvironmentPostProcessor;

/*
 * 默认配置文件加载
 */
public class ZooXFileStorageEnvProcessor extends ZooEnvironmentPostProcessor {
    
    @Override
    protected String propertySourceName() {
        return "zoo-diy-feature-file-xfilestorage.default";
    }
    
    @Override
    protected String propertySourceClasspath() {
        return "zoo-diy-feature-file-xfilestorage.default.yaml";
    }
    
    @Override
    protected boolean highestPriority() {
        return false;
    }
}
