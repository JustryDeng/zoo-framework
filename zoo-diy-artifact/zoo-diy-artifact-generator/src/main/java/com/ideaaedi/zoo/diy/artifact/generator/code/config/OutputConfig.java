package com.ideaaedi.zoo.diy.artifact.generator.code.config;

import com.ideaaedi.zoo.diy.artifact.generator.code.entity.PojoInfo;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * 文件输出配置汇总
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface OutputConfig extends BasicOutputConfig {
    
    /**
     * 关联的生成项的基础输出配置
     * <pre>
     *  如：生成同一对象的 entity、controller、service，那么对于entity来说，controller、service就是他关联的生成项
     *  注：这里面包不包含自身取决于具体实现，可包含可不包含
     * </pre>
     * <hr />
     * <pre>
     *  key  -输出类型{@link BasicOutputConfig#outputType()}
     *  value-关联输出类型配置
     * </pre>
     */
    @Nonnull
    Map<String, BasicOutputConfig> relatedConfig();
    
    /**
     * 当前模型的上下文
     * <pre>
     * 输出文件时，可能会用到的参数上下文。 如：使用模板生成文件时，模板中的参数信息
     * </pre>
     *
     * @param pojoInfo 模型信息
     */
    @Nonnull
    Map<String, Object> context(@Nonnull PojoInfo pojoInfo);
}
