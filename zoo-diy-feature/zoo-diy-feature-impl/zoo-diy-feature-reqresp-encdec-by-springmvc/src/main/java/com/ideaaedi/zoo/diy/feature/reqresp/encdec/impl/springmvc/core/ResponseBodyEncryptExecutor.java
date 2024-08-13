package com.ideaaedi.zoo.diy.feature.reqresp.encdec.impl.springmvc.core;

import com.alibaba.fastjson2.JSON;
import com.ideaaedi.commonds.security.AES;
import com.ideaaedi.commonds.security.Base64;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.executor.AbstractRespEncryptExecutor;
import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties.ZooReqrespEncdecProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ResponseBodyEncryptExecutor extends AbstractRespEncryptExecutor implements ResponseBodyAdvice<Object> {
    
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    public ResponseBodyEncryptExecutor(ZooReqrespEncdecProperties zooReqrespEncdecProperties) {
        super(zooReqrespEncdecProperties);
    }
    
    @Override
    public String encrypt(String responseData) {
        if (StringUtils.isBlank(responseData)) {
            return responseData;
        }
        ZooReqrespEncdecProperties.RespEncryptProperties encryptProperties = getEncryptProperties();
        return AES.encryptSilently(responseData,
                Base64.encode(encryptProperties.getAesKey().getBytes(StandardCharsets.UTF_8)));
    }
    
    @Override
    public boolean supports(@Nonnull MethodParameter returnType, @Nonnull Class<? extends HttpMessageConverter<?>> converterType) {
        // 请求响应为空的话，跳过
        HttpServletResponse httpServletResponse = ZooContext.Http.httpServletResponse();
        if (httpServletResponse == null) {
            log.debug("httpServletResponse is null. ignore encrypt response body.");
            return false;
        }
        HttpServletRequest httpServletRequest = ZooContext.Http.httpServletRequest();
        if (httpServletRequest == null) {
            log.debug("httpServletRequest is null. ignore encrypt response body.");
            return false;
        }
        // 未设置加密项的话，跳过
        ZooReqrespEncdecProperties.RespEncryptProperties encryptProperties = getEncryptProperties();
        Set<String> includeExistRespHeaders = encryptProperties.getIncludeExistRespHeaders().stream()
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        Set<String> includeHosts = encryptProperties.getIncludeHosts().stream()
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        Set<String> includePaths = encryptProperties.getIncludePaths().stream()
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        boolean includeExistRespHeadersIsEmpty = CollectionUtils.isEmpty(includeExistRespHeaders);
        boolean includeHostsIsEmpty = CollectionUtils.isEmpty(includeHosts);
        boolean includePathsIsEmpty = CollectionUtils.isEmpty(includePaths);
        if (includeExistRespHeadersIsEmpty && includeHostsIsEmpty && includePathsIsEmpty) {
            log.debug("not config encrypt item. ignore encrypt response body.");
            return false;
        }
        // 命中排除项（存在指定的响应头）的话，跳过
        Set<String> excludeExistRespHeaders = encryptProperties.getExcludeExistRespHeaders();
        if (!CollectionUtils.isEmpty(excludeExistRespHeaders)) {
            boolean hitExcludeHeader = excludeExistRespHeaders.stream().anyMatch(httpServletResponse::containsHeader);
            if (hitExcludeHeader) {
                log.debug("hitExcludeHeader. ignore encrypt response body.");
                return false;
            }
        }
        // 命中排除项（存在指定的uri）的话，跳过
        String requestUri = httpServletRequest.getRequestURI();
        Set<String> excludePaths = encryptProperties.getExcludePaths();
        if (!CollectionUtils.isEmpty(excludePaths)) {
            boolean hitExcludePaths = excludePaths.stream().anyMatch(excludePath -> antPathMatcher.match(excludePath, requestUri));
            if (hitExcludePaths) {
                log.debug("hitExcludePaths. ignore encrypt response body.");
                return false;
            }
        }
        // 命中排除项（存在指定的host）的话，跳过
        String remoteHost = httpServletRequest.getRemoteHost();
        Set<String> excludeHosts = encryptProperties.getExcludeHosts();
        if (!CollectionUtils.isEmpty(excludeHosts)) {
            boolean hitExcludeHosts = excludeHosts.stream().anyMatch(host -> antPathMatcher.match(host, remoteHost));
            if (hitExcludeHosts) {
                log.debug("hitExcludeHosts. ignore encrypt response body.");
                return false;
            }
        }
    
        // 命中包含项（存在指定的响应头）的话，需要加密
        if (!includeExistRespHeadersIsEmpty) {
            boolean hitIncludeHeader = includeExistRespHeaders.stream().anyMatch(httpServletResponse::containsHeader);
            if (hitIncludeHeader) {
                log.debug("hitIncludeHeader. do encrypt.");
                return true;
            }
        }
        // 命中包含项（存在指定的uri）的话，需要加密
        if (!includePathsIsEmpty) {
            boolean hitIncludePaths = includePaths.stream().anyMatch(includePath -> antPathMatcher.match(includePath, requestUri));
            if (hitIncludePaths) {
                log.debug("hitIncludePaths. do encrypt.");
                return true;
            }
        }
        // 命中包含（存在指定的host）的话，需要加密
        if (!includeHostsIsEmpty) {
            boolean hitIncludeHosts = includeHosts.stream().anyMatch(host -> antPathMatcher.match(host, remoteHost));
            if (hitIncludeHosts) {
                log.debug("hitIncludeHosts. do encrypt.");
                return true;
            }
        }
        log.debug("don't hit include item. ignore encrypt response body.");
        return false;
    }
    
    @Override
    public Object beforeBodyWrite(@Nullable Object body, @Nonnull MethodParameter returnType, @Nonnull MediaType selectedContentType,
                                  @Nonnull Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  @Nonnull ServerHttpRequest request, @Nonnull ServerHttpResponse response) {
        if (body == null) {
            return null;
        }
        String plainTextBodyStr = JSON.toJSONString(body);
        log.debug("before encrypt response body, {}", plainTextBodyStr);
        String encryptBodyStr = encrypt(plainTextBodyStr);
        log.debug("after  encrypt response body, {}", encryptBodyStr);
        return encryptBodyStr;
    }
}
