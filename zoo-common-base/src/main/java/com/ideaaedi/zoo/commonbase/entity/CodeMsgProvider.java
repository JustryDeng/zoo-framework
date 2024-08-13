package com.ideaaedi.zoo.commonbase.entity;

/**
 * code message
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface CodeMsgProvider {
    
    /**
     * 获取code
     *
     * @return code
     */
    String code();
    
    /**
     * 获取message
     *
     * @return message
     */
    String message();
    
}
