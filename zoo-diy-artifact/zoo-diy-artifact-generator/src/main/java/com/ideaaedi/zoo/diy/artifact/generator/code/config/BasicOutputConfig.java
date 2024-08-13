package com.ideaaedi.zoo.diy.artifact.generator.code.config;

import com.ideaaedi.zoo.diy.artifact.generator.code.entity.PojoInfo;

import javax.annotation.Nonnull;

/**
 * 文件输出基础配置
 */
public interface BasicOutputConfig {
    
    /**
     * 输出文件类型
     * <pre>
     *  自定义即可，示例{@link DefaultOutputConfig#outputType()}
     * </pre>
     */
    String outputType();
    
    /**
     * 输出文件的文件后缀
     * <pre>
     * .java、.xml等，示例{@link DefaultOutputConfig#outputType()}
     *
     * 注：
     * 文件的最终位置 = {@link PojoConfig#getOutputRootDir()}
     *                 + {@link PojoConfig#getPkgRoot()}
     *                 + {@link BasicOutputConfig#relativePkg()}
     *                 + {@link BasicOutputConfig#outputName(String)}
     *                 + {@code outputSuffix()}
     * </pre>
     */
    String outputSuffix();
    
    /**
     * 输出文件名（不带后缀）
     * <pre>
     * 自定义即可，示例{@link DefaultOutputConfig#outputName(String)}
     *
     * 注：
     * 文件的最终位置 = {@link PojoConfig#getOutputRootDir}
     *                 + {@link PojoConfig#getPkgRoot}
     *                 + {@link BasicOutputConfig#relativePkg()}
     *                 + {@code outputName(String)}
     *                 + {@link BasicOutputConfig#outputSuffix()}
     * </pre>
     *
     * @param pojoName {@link PojoInfo#getName()}模型名称（不带后缀）
     */
    String outputName(@Nonnull String pojoName);
    
    /**
     * 输出文件的相对包路径（相对于{@link PojoConfig#getPkgRoot()} ）
     * <pre>
     * 自定义即可，示例{@link DefaultOutputConfig#relativePkg()}
     *
     * 注：
     * 文件的最终位置 = {@link PojoConfig#getOutputRootDir}
     *                 + {@link PojoConfig#getPkgRoot}
     *                 + {@code relativePkg()}
     *                 + {@link BasicOutputConfig#outputName(String)}
     *                 + {@link BasicOutputConfig#outputSuffix()}
     * </pre>
     */
    String relativePkg();
}
