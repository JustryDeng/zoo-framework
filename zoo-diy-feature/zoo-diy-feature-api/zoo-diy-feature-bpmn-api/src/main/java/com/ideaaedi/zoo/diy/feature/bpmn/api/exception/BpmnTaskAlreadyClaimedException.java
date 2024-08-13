package com.ideaaedi.zoo.diy.feature.bpmn.api.exception;

/**
 * 任务已被认领异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BpmnTaskAlreadyClaimedException extends BpmnException {
    public BpmnTaskAlreadyClaimedException() {
        super();
    }
    
    public BpmnTaskAlreadyClaimedException(String message) {
        super(message);
    }
    
    public BpmnTaskAlreadyClaimedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BpmnTaskAlreadyClaimedException(Throwable cause) {
        super(cause);
    }
    
    protected BpmnTaskAlreadyClaimedException(String message, Throwable cause, boolean enableSuppression,
                                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
