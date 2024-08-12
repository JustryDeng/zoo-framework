package com.ideaaedi.zoo.diy.feature.msg.api.exception;

/**
 * 未指定默认的邮件发送器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class MailNotPointDefaultSenderException extends SmsException {
    public MailNotPointDefaultSenderException() {
        super();
    }
    
    public MailNotPointDefaultSenderException(String message) {
        super(message);
    }
    
    public MailNotPointDefaultSenderException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MailNotPointDefaultSenderException(Throwable cause) {
        super(cause);
    }
    
    protected MailNotPointDefaultSenderException(String message, Throwable cause, boolean enableSuppression,
                                                 boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
