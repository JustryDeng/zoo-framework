package com.ideaaedi.zoo.diy.feature.litefeature.api.exception;

/**
 * MultiLockSupport不存在异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class LockSupplierNoSuchException extends LockSupplierException {
    public LockSupplierNoSuchException() {
        super();
    }
    
    public LockSupplierNoSuchException(String message) {
        super(message);
    }
    
    public LockSupplierNoSuchException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public LockSupplierNoSuchException(Throwable cause) {
        super(cause);
    }
    
    protected LockSupplierNoSuchException(String message, Throwable cause, boolean enableSuppression,
                                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
