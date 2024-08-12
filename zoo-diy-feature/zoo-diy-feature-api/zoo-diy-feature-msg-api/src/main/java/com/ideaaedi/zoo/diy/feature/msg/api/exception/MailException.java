package com.ideaaedi.zoo.diy.feature.msg.api.exception;

/**
 * mail相关异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class MailException extends RuntimeException{
    public MailException() {
        super();
    }
    
    public MailException(String message) {
        super(message);
    }
    
    public MailException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MailException(Throwable cause) {
        super(cause);
    }
    
    protected MailException(String message, Throwable cause, boolean enableSuppression,
                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
