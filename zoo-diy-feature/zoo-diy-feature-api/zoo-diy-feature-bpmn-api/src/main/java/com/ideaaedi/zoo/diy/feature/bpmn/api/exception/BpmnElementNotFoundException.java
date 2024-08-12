package com.ideaaedi.zoo.diy.feature.bpmn.api.exception;

/**
 * 元素未发现
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BpmnElementNotFoundException extends BpmnException {
    public BpmnElementNotFoundException() {
        super();
    }
    
    public BpmnElementNotFoundException(String message) {
        super(message);
    }
    
    public BpmnElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BpmnElementNotFoundException(Throwable cause) {
        super(cause);
    }
    
    protected BpmnElementNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
