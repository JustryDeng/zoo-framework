package com.ideaaedi.zoo.commonbase.exception;

import com.ideaaedi.zoo.commonbase.entity.CodeMsgProvider;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 基类异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
@ToString
public class BaseException extends RuntimeException implements Serializable {
    
    protected final CodeMsgProvider codeMsgProvider;
    
    @Setter
    protected Object[] placeholders;
    
    @Setter
    protected Object data;
    
    public BaseException(BaseException baseException) {
        super(baseException.getMessage(), baseException.getCause());
        this.codeMsgProvider = baseException.getCodeMsgProvider();
        this.placeholders = baseException.getPlaceholders();
        this.data = baseException.getData();
    }
    
    public BaseException(CodeMsgProvider codeMsgProvider) {
        super(codeMsgProvider == null ? "" : StringUtils.isEmpty(codeMsgProvider.message()) ? "" : codeMsgProvider.message());
        this.codeMsgProvider = codeMsgProvider;
    }
    
    public BaseException(CodeMsgProvider codeMsgProvider, Object... placeholder) {
        super(codeMsgProvider == null ? "" : StringUtils.isEmpty(codeMsgProvider.message()) ? "" : codeMsgProvider.message());
        this.codeMsgProvider = codeMsgProvider;
        this.placeholders = placeholder;
    }
    
    public BaseException(String message, CodeMsgProvider codeMsgProvider) {
        super(message);
        this.codeMsgProvider = codeMsgProvider;
    }
    
    public BaseException(String message, CodeMsgProvider codeMsgProvider, Object... placeholder) {
        super(message);
        this.codeMsgProvider = codeMsgProvider;
        this.placeholders = placeholder;
    }
    
    public BaseException(String message, CodeMsgProvider codeMsgProvider, Throwable cause) {
        super(message, cause);
        this.codeMsgProvider = codeMsgProvider;
    }
    
    public BaseException(String message, CodeMsgProvider codeMsgProvider, Throwable cause, Object... placeholder) {
        super(message, cause);
        this.codeMsgProvider = codeMsgProvider;
        this.placeholders = placeholder;
    }
    
    public BaseException(CodeMsgProvider codeMsgProvider, Throwable cause) {
        super(codeMsgProvider.message(), cause);
        this.codeMsgProvider = codeMsgProvider;
    }
    
    public BaseException(CodeMsgProvider codeMsgProvider, Throwable cause, Object... placeholder) {
        super(codeMsgProvider.message(), cause);
        this.codeMsgProvider = codeMsgProvider;
        this.placeholders = placeholder;
    }
    
    public static BaseException createWithData(Object data, CodeMsgProvider codeMsgProvider) {
        BaseException baseException = new BaseException(codeMsgProvider);
        baseException.setData(data);
        return baseException;
    }
    
    public static BaseException createWithData(Object data, CodeMsgProvider codeMsgProvider, Object... placeholder) {
        BaseException baseException = new BaseException(codeMsgProvider, placeholder);
        baseException.setData(data);
        return baseException;
    }
    
    public static BaseException createWithData(Object data, String message, CodeMsgProvider codeMsgProvider) {
        BaseException baseException = new BaseException(message, codeMsgProvider);
        baseException.setData(data);
        return baseException;
    }
    
    public static BaseException createWithData(Object data, String message, CodeMsgProvider codeMsgProvider, Object... placeholder) {
        BaseException baseException = new BaseException(message, codeMsgProvider, placeholder);
        baseException.setData(data);
        return baseException;
    }
    
    public static BaseException createWithData(Object data, String message, CodeMsgProvider codeMsgProvider, Throwable cause) {
        BaseException baseException = new BaseException(message, codeMsgProvider, cause);
        baseException.setData(data);
        return baseException;
    }
    
    public static BaseException createWithData(Object data, String message, CodeMsgProvider codeMsgProvider, Throwable cause, Object... placeholder) {
        BaseException baseException = new BaseException(message, codeMsgProvider, cause, placeholder);
        baseException.setData(data);
        return baseException;
    }
    
    public static BaseException createWithData(Object data, CodeMsgProvider codeMsgProvider, Throwable cause) {
        BaseException baseException = new BaseException(codeMsgProvider, cause);
        baseException.setData(data);
        return baseException;
    }
    
    public static BaseException createWithData(Object data, CodeMsgProvider codeMsgProvider, Throwable cause, Object... placeholder) {
        BaseException baseException = new BaseException(codeMsgProvider, cause, placeholder);
        baseException.setData(data);
        return baseException;
    }
    
}
