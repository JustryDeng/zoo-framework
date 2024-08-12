package com.ideaaedi.zoo.diy.feature.bpmn.api.exception;

/**
 * 租户数据阈不匹配异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BpmnTenantNotMatchException extends BpmnTenantException {
    public BpmnTenantNotMatchException() {
        super();
    }
    
    public BpmnTenantNotMatchException(String message) {
        super(message);
    }
    
    public BpmnTenantNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BpmnTenantNotMatchException(Throwable cause) {
        super(cause);
    }
    
    protected BpmnTenantNotMatchException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
