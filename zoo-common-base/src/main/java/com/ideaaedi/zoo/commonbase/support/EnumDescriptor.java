package com.ideaaedi.zoo.commonbase.support;

/**
 * 枚举说明器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.1.E
 */
public interface EnumDescriptor {
    
    /**
     * 获取枚举项说明
     *
     * @return 枚举项说明
     */
    String obtainDescription();
}
