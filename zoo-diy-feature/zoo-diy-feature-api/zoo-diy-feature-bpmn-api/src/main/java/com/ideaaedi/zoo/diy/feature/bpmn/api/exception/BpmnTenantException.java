package com.ideaaedi.zoo.diy.feature.bpmn.api.exception;

/**
 * 工作流租户相关异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BpmnTenantException extends BpmnException {
    public BpmnTenantException() {
        super();
    }
    
    public BpmnTenantException(String message) {
        super(message);
    }
    
    public BpmnTenantException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BpmnTenantException(Throwable cause) {
        super(cause);
    }
    
    protected BpmnTenantException(String message, Throwable cause, boolean enableSuppression,
                                  boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
