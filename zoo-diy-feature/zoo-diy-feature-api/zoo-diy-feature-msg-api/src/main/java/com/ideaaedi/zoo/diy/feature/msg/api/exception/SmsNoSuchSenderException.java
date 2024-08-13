package com.ideaaedi.zoo.diy.feature.msg.api.exception;

/**
 * sms发送器不存在异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class SmsNoSuchSenderException extends SmsException {
    public SmsNoSuchSenderException() {
        super();
    }
    
    public SmsNoSuchSenderException(String message) {
        super(message);
    }
    
    public SmsNoSuchSenderException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SmsNoSuchSenderException(Throwable cause) {
        super(cause);
    }
    
    protected SmsNoSuchSenderException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
