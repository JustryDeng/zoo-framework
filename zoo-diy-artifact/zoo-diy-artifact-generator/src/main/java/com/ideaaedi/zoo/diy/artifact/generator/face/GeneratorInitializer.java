package com.ideaaedi.zoo.diy.artifact.generator.face;

import com.ideaaedi.zoo.diy.artifact.generator.properties.ZooGeneratorProperties;

/**
 * 生成器初始化器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class GeneratorInitializer {
    
    public GeneratorInitializer(ZooGeneratorProperties zooGeneratorProperties) {
        init(zooGeneratorProperties);
    }
    
    protected void init(ZooGeneratorProperties zooGeneratorProperties) {
        ZooGeneratorProperties.DatabaseDoc databaseDoc = zooGeneratorProperties.getDatabaseDoc();
        if (databaseDoc != null) {
            GeneratorFace4DatabaseDoc.databaseDoc = databaseDoc;
        }
        ZooGeneratorProperties.JavaCurdCode javaCurdCode = zooGeneratorProperties.getJavaCurdCode();
        if (javaCurdCode != null) {
            GeneratorFace4JavaCurdCode.javaCurdCode = javaCurdCode;
        }
    }
}
