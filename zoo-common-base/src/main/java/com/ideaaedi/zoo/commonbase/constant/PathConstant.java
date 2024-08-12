package com.ideaaedi.zoo.commonbase.constant;

import java.time.format.DateTimeFormatter;

/**
 * 路径常量类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.1.E
 */
public interface PathConstant {
    
    /** 文件路径 日期格式 */
    DateTimeFormatter FILE_DATE_PATH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM/dd/HHmmss");
    
}