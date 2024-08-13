package com.ideaaedi.zoo.diy.artifact.generator.code.template;

import com.ideaaedi.zoo.diy.artifact.generator.code.config.OutputConfig;

import javax.annotation.Nonnull;

/**
 * 模板信息提供器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface TemplateProvider {
    
    /**
     * 获取模板
     *
     * @param outputType 输出文件类型 {@link OutputConfig#outputType()}
     *
     * @return  模板（或指向模板的id、路径等， 具体是什么取决于实现）
     */
    String provide(@Nonnull String outputType);
}
