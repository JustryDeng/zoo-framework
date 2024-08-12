package com.ideaaedi.zoo.commonbase.entity;

import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.MDC;

import java.io.Serializable;

/**
 * 统一响应类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements CodeMsgProvider, Serializable {
    
    /** 标识代码 */
    @Schema(description = "标识代码")
    private String code;
    
    /** 提示信息 */
    @Schema(description = "提示信息")
    private String msg;
    
    /** traceId */
    @Schema(description = "traceXd")
    private String traceXd;
    
    /** 返回的数据 */
    @Schema(description = "返回的数据")
    private T data;
    
    public static <T> Result<T> success() {
        return new Result<>(BaseCodeMsgEnum.SUCCESS.getCode(), BaseCodeMsgEnum.SUCCESS.getMessage(), MDC.get(BaseConstant.TRACE_XD), null);
    }
    
    public static <T> Result<T> success(T data) {
        return new Result<>(BaseCodeMsgEnum.SUCCESS.getCode(), BaseCodeMsgEnum.SUCCESS.getMessage(), MDC.get(BaseConstant.TRACE_XD), data);
    }
    
    public static <T> Result<T> success(CodeMsgProvider codeMsgProvider) {
        return new Result<>(codeMsgProvider.code(), codeMsgProvider.message(), MDC.get(BaseConstant.TRACE_XD), null);
    }
    
    public static <T> Result<T> success(T data, CodeMsgProvider codeMsgProvider) {
        return new Result<>(codeMsgProvider.code(), codeMsgProvider.message(), MDC.get(BaseConstant.TRACE_XD), data);
    }
    
    /**
     * 占位符解析支持
     */
    public static <T> Result<T> success(T data, CodeMsgProvider codeMsgProvider, Object... placeholder) {
        return new Result<>(codeMsgProvider.code(), String.format(codeMsgProvider.message(), placeholder), MDC.get(BaseConstant.TRACE_XD), data);
    }
    
    public static <T> Result<T> failure() {
        return new Result<>(BaseCodeMsgEnum.FAILURE.getCode(), BaseCodeMsgEnum.FAILURE.getMessage(), MDC.get(BaseConstant.TRACE_XD), null);
    }
    
    public static <T> Result<T> failure(T data) {
        return new Result<>(BaseCodeMsgEnum.FAILURE.getCode(), BaseCodeMsgEnum.FAILURE.getMessage(), MDC.get(BaseConstant.TRACE_XD), data);
    }
    
    public static <T> Result<T> failure(CodeMsgProvider codeMsgProvider) {
        return new Result<>(codeMsgProvider.code(), codeMsgProvider.message(), MDC.get(BaseConstant.TRACE_XD), null);
    }
    
    public static <T> Result<T> failure(T data, CodeMsgProvider codeMsgProvider) {
        return new Result<>(codeMsgProvider.code(), codeMsgProvider.message(), MDC.get(BaseConstant.TRACE_XD), data);
    }
    
    /**
     * 占位符解析支持
     */
    public static <T> Result<T> failure(T data, CodeMsgProvider codeMsgProvider, Object... placeholder) {
        String formatResult = String.format(codeMsgProvider.message(), placeholder);
        if (formatResult != null) {
            // 移除解析失败的占位符
            formatResult = formatResult.replace("%s", BaseConstant.EMPTY);
        }
        return new Result<>(codeMsgProvider.code(), formatResult, MDC.get(BaseConstant.TRACE_XD), data);
    }
    
    @Override
    public String code() {
        return getCode();
    }
    
    @Override
    public String message() {
        return getMsg();
    }
}
