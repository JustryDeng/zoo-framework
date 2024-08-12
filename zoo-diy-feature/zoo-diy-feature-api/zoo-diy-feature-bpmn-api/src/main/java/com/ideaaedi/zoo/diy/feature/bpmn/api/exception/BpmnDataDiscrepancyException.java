package com.ideaaedi.zoo.diy.feature.bpmn.api.exception;

/**
 * 数据不一致异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BpmnDataDiscrepancyException extends BpmnException {
    public BpmnDataDiscrepancyException() {
        super();
    }
    
    public BpmnDataDiscrepancyException(String message) {
        super(message);
    }
    
    public BpmnDataDiscrepancyException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BpmnDataDiscrepancyException(Throwable cause) {
        super(cause);
    }
    
    protected BpmnDataDiscrepancyException(String message, Throwable cause, boolean enableSuppression,
                                           boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
