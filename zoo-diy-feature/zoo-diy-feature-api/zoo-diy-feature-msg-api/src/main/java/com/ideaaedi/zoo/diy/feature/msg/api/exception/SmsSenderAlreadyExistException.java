package com.ideaaedi.zoo.diy.feature.msg.api.exception;

/**
 * sms发送器已存在异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class SmsSenderAlreadyExistException extends SmsException {
    public SmsSenderAlreadyExistException() {
        super();
    }
    
    public SmsSenderAlreadyExistException(String message) {
        super(message);
    }
    
    public SmsSenderAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public SmsSenderAlreadyExistException(Throwable cause) {
        super(cause);
    }
    
    protected SmsSenderAlreadyExistException(String message, Throwable cause, boolean enableSuppression,
                                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
