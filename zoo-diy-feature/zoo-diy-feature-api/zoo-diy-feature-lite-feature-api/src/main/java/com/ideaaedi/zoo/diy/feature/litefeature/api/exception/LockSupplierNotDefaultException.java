package com.ideaaedi.zoo.diy.feature.litefeature.api.exception;

/**
 * 未指定默认的MultiLockSupport
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class LockSupplierNotDefaultException extends LockSupplierException {
    public LockSupplierNotDefaultException() {
        super();
    }
    
    public LockSupplierNotDefaultException(String message) {
        super(message);
    }
    
    public LockSupplierNotDefaultException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public LockSupplierNotDefaultException(Throwable cause) {
        super(cause);
    }
    
    protected LockSupplierNotDefaultException(String message, Throwable cause, boolean enableSuppression,
                                              boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
