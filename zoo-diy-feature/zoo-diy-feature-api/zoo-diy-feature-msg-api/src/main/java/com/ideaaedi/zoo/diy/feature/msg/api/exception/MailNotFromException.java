package com.ideaaedi.zoo.diy.feature.msg.api.exception;

/**
 * 邮件发送找不到发件人异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class MailNotFromException extends SmsException {
    public MailNotFromException() {
        super();
    }
    
    public MailNotFromException(String message) {
        super(message);
    }
    
    public MailNotFromException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MailNotFromException(Throwable cause) {
        super(cause);
    }
    
    protected MailNotFromException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
