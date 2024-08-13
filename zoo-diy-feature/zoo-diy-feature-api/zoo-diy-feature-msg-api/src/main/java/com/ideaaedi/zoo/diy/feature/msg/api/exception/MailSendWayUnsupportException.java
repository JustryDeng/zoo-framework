package com.ideaaedi.zoo.diy.feature.msg.api.exception;

/**
 * 邮件发送方式不支持异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class MailSendWayUnsupportException extends SmsException {
    public MailSendWayUnsupportException() {
        super();
    }
    
    public MailSendWayUnsupportException(String message) {
        super(message);
    }
    
    public MailSendWayUnsupportException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MailSendWayUnsupportException(Throwable cause) {
        super(cause);
    }
    
    protected MailSendWayUnsupportException(String message, Throwable cause, boolean enableSuppression,
                                            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
