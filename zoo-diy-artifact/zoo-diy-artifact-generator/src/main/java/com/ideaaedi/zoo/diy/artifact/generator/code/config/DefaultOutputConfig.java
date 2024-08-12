package com.ideaaedi.zoo.diy.artifact.generator.code.config;

import javax.annotation.Nonnull;
import java.util.Map;


/**
 * 具体的文件输出配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class DefaultOutputConfig extends AbstractOutputConfig {
    
    protected final BasicOutputConfig basicConfigProxy;
    
    public DefaultOutputConfig(BasicOutputConfig currBasicConfig, Map<String, BasicOutputConfig> relatedConfig) {
        super(relatedConfig);
        this.basicConfigProxy = currBasicConfig;
    }
    
    @Override
    public String outputType() {
        return basicConfigProxy.outputType();
    }
    
    @Override
    public String outputSuffix() {
        return basicConfigProxy.outputSuffix();
    }
    
    @Override
    public String outputName(@Nonnull String pojoName) {
        return basicConfigProxy.outputName(pojoName);
    }
    
    @Override
    public String relativePkg() {
        return basicConfigProxy.relativePkg();
    }
}
