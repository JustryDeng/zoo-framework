package com.ideaaedi.zoo.diy.artifact.generator.face.detector;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.ZooKnife4jAutoConfiguration;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.PojoConfig;
import com.ideaaedi.zoo.diy.artifact.generator.properties.ZooGeneratorProperties;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.ZooMybatisPlusAutoConfiguration;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.injector.MybatisPlusSqlInjector;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

/**
 * 侦测器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class Detector {
    
    @ConditionalOnClass(ZooKnife4jAutoConfiguration.class)
    @AutoConfigureAfter(ZooKnife4jAutoConfiguration.class)
    public static class Knife4jExtDetector {
        
        @Bean
        public InitializingBean knife4jExtDetector(ZooGeneratorProperties zooGeneratorProperties) {
            return () -> {
                ZooGeneratorProperties.JavaCurdCode javaCurdCode = zooGeneratorProperties.getJavaCurdCode();
                Boolean existKnife4jExt = javaCurdCode.getExistKnife4jExt();
                if (existKnife4jExt != null) {
                    return;
                }
                javaCurdCode.setExistKnife4jExt(true);
                PojoConfig.DEFAULT_HAS_ZOO_KNIFE4J_EXT = true;
            };
        }
        
    }
    
    @ConditionalOnClass(ZooMybatisPlusAutoConfiguration.class)
    @AutoConfigureAfter(ZooMybatisPlusAutoConfiguration.class)
    public static class MybatisPlusExtDetector {
        
        @Bean
        @ConditionalOnBean(MybatisPlusSqlInjector.class)
        public InitializingBean mybatisPlusExtDetector(ZooGeneratorProperties zooGeneratorProperties) {
            return () -> {
                ZooGeneratorProperties.JavaCurdCode javaCurdCode = zooGeneratorProperties.getJavaCurdCode();
                Boolean existMybatisPlusExt = javaCurdCode.getExistMybatisPlusExt();
                if (existMybatisPlusExt != null) {
                    return;
                }
                javaCurdCode.setExistMybatisPlusExt(true);
            };
        }
    }
    
    
    @ConditionalOnClass(ExcelProperty.class)
    public static class EasyExcelDetector {
        
        @Bean
        public InitializingBean easyExcelDetector(ZooGeneratorProperties zooGeneratorProperties) {
            return () -> {
                ZooGeneratorProperties.JavaCurdCode javaCurdCode = zooGeneratorProperties.getJavaCurdCode();
                Boolean existEasyExcel = javaCurdCode.getExistEasyExcel();
                if (existEasyExcel != null) {
                    return;
                }
                javaCurdCode.setExistEasyExcel(true);
            };
        }
    }
}