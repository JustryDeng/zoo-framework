package com.ideaaedi.zoo.diy.feature.bpmn.api.exception;

/**
 * 参数相关异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BpmnIllegalArgumentException extends BpmnException {
    public BpmnIllegalArgumentException() {
        super();
    }
    
    public BpmnIllegalArgumentException(String message) {
        super(message);
    }
    
    public BpmnIllegalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BpmnIllegalArgumentException(Throwable cause) {
        super(cause);
    }
    
    protected BpmnIllegalArgumentException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
