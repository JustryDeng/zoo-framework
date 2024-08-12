package com.ideaaedi.zoo.foundation;

import com.ideaaedi.zoo.foundation.guide.DIYGuidePrinter;
import com.ideaaedi.zoo.foundation.properties.ZooFoundationGuideProperties;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 自动配置
 */
@ImportAutoConfiguration(DIYGuidePrinter.class)
@EnableConfigurationProperties(ZooFoundationGuideProperties.class)
public class ZooFoundationAutoConfiguration {
    
}
