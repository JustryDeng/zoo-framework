package com.ideaaedi.zoo.diy.artifact.auth.satoken.config;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.filter.SaFilterErrorStrategy;
import com.alibaba.fastjson2.JSON;
import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.commonbase.entity.BaseCodeMsgEnum;
import com.ideaaedi.zoo.commonbase.entity.CodeMsgProvider;
import com.ideaaedi.zoo.commonbase.entity.Result;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.properties.ZooAuthProperties;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.Ordered;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * spring-mvc 异常处理器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@AutoConfiguration
@RestControllerAdvice
public class ZooSaTokenExceptionHandler implements SaFilterErrorStrategy, Ordered {
    @Resource
    private ZooAuthProperties zooAuthProperties;
    
    /**
     * 处理认证鉴权相关异常
     */
    @ExceptionHandler(SaTokenException.class)
    public Result<Object> handleSaTokenException(SaTokenException e) {
        ZooAuthProperties.ZooAuthBasic basic = zooAuthProperties.getBasic();
        Map<Integer, CodeMsgProvider> errorMapping = basic == null ? null : basic.getErrorMapping();
        int code = e.getCode();
        Result<Object> result = null;
        if (errorMapping != null) {
            CodeMsgProvider codeMsgProvider = errorMapping.get(code);
            if (codeMsgProvider != null) {
                result = Result.failure(codeMsgProvider);
            }
        }
        result = result == null ? Result.failure(BaseCodeMsgEnum.AUTH_OCCUR_EXCEPTION) : result;
        result.setData(code + "." + e.getMessage());
        log.error("ZooSaTokenExceptionHandler#handleSaTokenException e.getMessage() -> {}, Result -> {}",
                e.getMessage(), result, e);
        return result;
    }
    
    
    @Override
    public int getOrder() {
        return AutoConfigurationConstant.ZOO_SA_TOKEN_EXCEPTION_HANDLER_ORDER;
    }
    
    @Override
    public Object run(Throwable e) {
        if (e instanceof SaTokenException) {
            return handleSaTokenException((SaTokenException) e);
        }
        Result<Object> result = Result.failure(BaseCodeMsgEnum.FILTER_AUTH_OCCUR_EXCEPTION);
        result.setData(e.getMessage());
        log.error("SaFilterErrorStrategy occur exception e.getMessage() -> {}, Result -> {}", e.getMessage(), result,
                e);
        return JSON.toJSONString(result);
    }
}
