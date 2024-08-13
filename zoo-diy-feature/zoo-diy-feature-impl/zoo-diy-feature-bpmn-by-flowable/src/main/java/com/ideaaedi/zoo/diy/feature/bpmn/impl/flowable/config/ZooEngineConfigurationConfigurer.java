package com.ideaaedi.zoo.diy.feature.bpmn.impl.flowable.config;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;

/**
 * Customizing Engine Configuration
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class ZooEngineConfigurationConfigurer implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {
    
    public void configure(SpringProcessEngineConfiguration processEngineConfiguration) {
         // Customizing Engine Configuration
    }
    
}