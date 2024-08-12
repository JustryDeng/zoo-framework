package com.ideaaedi.zoo.diy.artifact.generator;

import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.diy.artifact.generator.face.GeneratorInitializer;
import com.ideaaedi.zoo.diy.artifact.generator.face.detector.Detector;
import com.ideaaedi.zoo.diy.artifact.generator.properties.ZooGeneratorDIYGuideProperties;
import com.ideaaedi.zoo.diy.artifact.generator.properties.ZooGeneratorProperties;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@ImportAutoConfiguration({
        Detector.MybatisPlusExtDetector.class,
        Detector.Knife4jExtDetector.class,
        Detector.EasyExcelDetector.class
})
@AutoConfigureOrder(AutoConfigurationConstant.ZOO_GENERATOR_AUTO_CONFIGURATION_ORDER)
@EnableConfigurationProperties({ZooGeneratorProperties.class, ZooGeneratorDIYGuideProperties.class})
public class ZooGeneratorAutoConfiguration {
    
    @Bean
    public GeneratorInitializer generatorInitializer(ZooGeneratorProperties zooGeneratorProperties) {
        return new GeneratorInitializer(zooGeneratorProperties);
    }
}