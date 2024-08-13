package com.ideaaedi.zoo.commonbase.zoo_support;

import javax.annotation.Nullable;

/**
 * 原始对象
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface RawSupport {
    
    /**
     * 获取原始实例
     *
     * @return 原始实例
     */
    @Nullable
    default <T> T getRaw() {
        return null;
    }
}
