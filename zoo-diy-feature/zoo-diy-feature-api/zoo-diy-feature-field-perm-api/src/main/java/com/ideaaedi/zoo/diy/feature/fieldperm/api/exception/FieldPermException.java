package com.ideaaedi.zoo.diy.feature.fieldperm.api.exception;

import com.ideaaedi.commonds.exception.ValidateException;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.MethodArgFieldInfo;
import lombok.Getter;

import javax.annotation.Nonnull;

/**
 * 字段权限校验不通过异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class FieldPermException extends ValidateException {
    
    @Nonnull
    @Getter
    private final MethodArgFieldInfo methodArgFieldInfo;
    
    public FieldPermException(@Nonnull MethodArgFieldInfo methodArgFieldInfo) {
        this.methodArgFieldInfo = methodArgFieldInfo;
    }
    
    public FieldPermException(String message, @Nonnull MethodArgFieldInfo methodArgFieldInfo) {
        super(message);
        this.methodArgFieldInfo = methodArgFieldInfo;
    }
    
    public FieldPermException(String message, Throwable cause, @Nonnull MethodArgFieldInfo methodArgFieldInfo) {
        super(message, cause);
        this.methodArgFieldInfo = methodArgFieldInfo;
    }
    
    public FieldPermException(Throwable cause, @Nonnull MethodArgFieldInfo methodArgFieldInfo) {
        super(cause);
        this.methodArgFieldInfo = methodArgFieldInfo;
    }
    
    public FieldPermException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace,
                              @Nonnull MethodArgFieldInfo methodArgFieldInfo) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.methodArgFieldInfo = methodArgFieldInfo;
    }
}
