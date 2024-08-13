package com.ideaaedi.zoo.diy.artifact.generator.code;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface CodeGenerator<T, R> extends Generator {
    
    /**
     * 生成代码
     *
     * @param t 生成代码用到的数据
     *
     * @return 生成的代码 或者 生成的代码的相关信息
     */
    @Nullable
    R generate(@Nonnull T t);
}
