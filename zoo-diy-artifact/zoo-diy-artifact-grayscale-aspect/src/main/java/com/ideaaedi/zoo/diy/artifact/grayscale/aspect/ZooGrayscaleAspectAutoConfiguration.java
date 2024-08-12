package com.ideaaedi.zoo.diy.artifact.grayscale.aspect;

import com.ideaaedi.zoo.diy.artifact.grayscale.aspect.advice.GrayscaleAspect;
import com.ideaaedi.zoo.diy.artifact.grayscale.aspect.advice.GrayscaleFastFailChecker;
import com.ideaaedi.zoo.diy.artifact.grayscale.aspect.properties.ZooGrayscaleAspectDIYGuideProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * aspect灰度 自动配置
 */
@EnableConfigurationProperties({ZooGrayscaleAspectDIYGuideProperties.class})
public class ZooGrayscaleAspectAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public GrayscaleFastFailChecker grayscaleFastFailChecker() {
        return new GrayscaleFastFailChecker();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public GrayscaleAspect grayscaleAspect(@Autowired(required = false)
                                           GrayscaleAspect.GrayscaleAspectOrderProvider grayscaleAspectOrderProvider) {
        return new GrayscaleAspect(grayscaleAspectOrderProvider);
    }
}