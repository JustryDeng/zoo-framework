package com.ideaaedi.zoo.commonbase.exception;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 直接提示指定文本的异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
@ToString
public class TextTipException extends RuntimeException implements Serializable {
    
    private final String tips;
    
    public TextTipException(String tips) {
        super(tips);
        this.tips = tips;
    }
    
    public TextTipException(String tips, Throwable e) {
        super(tips, e);
        this.tips = tips;
    }
    
    public TextTipException(String message, String tips) {
        super(message);
        this.tips = tips;
    }
    
    public TextTipException(String message, String tips, Throwable e) {
        super(message, e);
        this.tips = tips;
    }
}
