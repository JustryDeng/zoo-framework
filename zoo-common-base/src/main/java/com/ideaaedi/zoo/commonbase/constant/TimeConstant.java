package com.ideaaedi.zoo.commonbase.constant;

/**
 * 时间常量类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface TimeConstant {
    
    /**
     * 1分钟（以秒为单位）
     */
    int MINUTE_1 = 60;
    
    /**
     * 1小时（以秒为单位）
     */
    int HOUR_1 = MINUTE_1 * 60;
    
    /**
     * 1天（以秒为单位）
     */
    int DAY_1 = HOUR_1 * 24;
    
    
    /**
     * 7天（以秒为单位）
     */
    int DAY_7 = DAY_1 * 7;
    
}
