package com.ideaaedi.zoo.commonbase.exception;

import com.ideaaedi.zoo.commonbase.entity.CodeMsgProvider;
import lombok.Getter;
import lombok.ToString;

/**
 * rpc调用异常
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
@ToString
public class RpcException extends BaseException {
    
    public RpcException(BaseException baseException) {
        super(baseException);
    }
    
    public RpcException(CodeMsgProvider codeMsgProvider) {
        super(codeMsgProvider);
    }
    
    public RpcException(CodeMsgProvider codeMsgProvider, Object... placeholder) {
        super(codeMsgProvider, placeholder);
    }
    
    public RpcException(String message, CodeMsgProvider codeMsgProvider) {
        super(message, codeMsgProvider);
    }
    
    public RpcException(String message, CodeMsgProvider codeMsgProvider, Object... placeholder) {
        super(message, codeMsgProvider, placeholder);
    }
    
    public RpcException(String message, CodeMsgProvider codeMsgProvider, Throwable cause) {
        super(message, codeMsgProvider, cause);
    }
    
    public RpcException(String message, CodeMsgProvider codeMsgProvider, Throwable cause, Object... placeholder) {
        super(message, codeMsgProvider, cause, placeholder);
    }
    
    public RpcException(CodeMsgProvider codeMsgProvider, Throwable cause) {
        super(codeMsgProvider, cause);
    }
    
    public RpcException(CodeMsgProvider codeMsgProvider, Throwable cause, Object... placeholder) {
        super(codeMsgProvider, cause, placeholder);
    }
}
