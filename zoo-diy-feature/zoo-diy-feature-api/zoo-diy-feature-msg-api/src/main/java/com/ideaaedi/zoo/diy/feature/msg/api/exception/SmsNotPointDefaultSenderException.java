package com.ideaaedi.zoo.diy.feature.msg.api.exception;

/**
 * 未指定默认的短信 发送器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class SmsNotPointDefaultSenderException extends SmsException {
    public SmsNotPointDefaultSenderException() {
        super();
    }
    
    public SmsNotPointDefaultSenderException(String message) {
        super(message);
    }
    
    public SmsNotPointDefaultSenderException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SmsNotPointDefaultSenderException(Throwable cause) {
        super(cause);
    }
    
    protected SmsNotPointDefaultSenderException(String message, Throwable cause, boolean enableSuppression,
                                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
