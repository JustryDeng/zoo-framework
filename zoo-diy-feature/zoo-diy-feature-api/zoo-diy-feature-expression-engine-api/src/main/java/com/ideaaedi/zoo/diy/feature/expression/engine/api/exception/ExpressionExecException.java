package com.ideaaedi.zoo.diy.feature.expression.engine.api.exception;

/**
 * 规则执行异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class ExpressionExecException extends RuntimeException {
    
    public ExpressionExecException() {
        super();
    }
    
    public ExpressionExecException(String message) {
        super(message);
    }
    
    public ExpressionExecException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ExpressionExecException(Throwable cause) {
        super(cause);
    }
    
    protected ExpressionExecException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
