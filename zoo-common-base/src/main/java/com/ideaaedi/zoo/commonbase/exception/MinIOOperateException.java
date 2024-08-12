package com.ideaaedi.zoo.commonbase.exception;

/**
 * min-io操作异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@SuppressWarnings({"AlibabaClassNamingShouldBeCamel"})
public class MinIOOperateException extends RuntimeException {
    
    public MinIOOperateException() {
        super();
    }
    
    public MinIOOperateException(String message) {
        super(message);
    }
    
    public MinIOOperateException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MinIOOperateException(Throwable cause) {
        super(cause);
    }
    
    protected MinIOOperateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
