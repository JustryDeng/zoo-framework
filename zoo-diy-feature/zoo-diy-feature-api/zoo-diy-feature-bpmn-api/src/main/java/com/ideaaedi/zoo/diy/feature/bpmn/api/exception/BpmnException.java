package com.ideaaedi.zoo.diy.feature.bpmn.api.exception;

/**
 * bpm异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BpmnException extends RuntimeException {
    public BpmnException() {
        super();
    }
    
    public BpmnException(String message) {
        super(message);
    }
    
    public BpmnException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BpmnException(Throwable cause) {
        super(cause);
    }
    
    protected BpmnException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
