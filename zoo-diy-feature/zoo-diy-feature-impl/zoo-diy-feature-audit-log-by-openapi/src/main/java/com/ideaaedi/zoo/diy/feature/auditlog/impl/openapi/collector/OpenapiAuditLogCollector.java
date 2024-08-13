package com.ideaaedi.zoo.diy.feature.auditlog.impl.openapi.collector;

import cn.hutool.core.util.IdUtil;
import com.ideaaedi.commonds.exception.ExceptionUtil;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import com.ideaaedi.zoo.diy.feature.auditlog.api.entity.AuditDTO;
import com.ideaaedi.zoo.diy.feature.auditlog.api.properties.ZooAuditLogApiProperties;
import com.ideaaedi.zoo.diy.feature.auditlog.api.spi.AuditLogCollector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class OpenapiAuditLogCollector implements AuditLogCollector<AuditDTO> {
    
    @Autowired(required = false)
    private ParameterNameDiscoverer parameterNameDiscoverer;
    
    private static final Map<String, String> METHOD_AND_DESC_CACHE = new ConcurrentHashMap<>(64);
    
    @PostConstruct
    private void init() {
        if (parameterNameDiscoverer == null) {
            log.info("Set StandardReflectionParameterNameDiscoverer as parameterNameDiscoverer.");
            parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
        }
    }
    
    @Override
    public boolean staticMatches(@Nonnull Method method, @Nonnull Class<?> targetClass) {
        Operation operationAnno = AnnotationUtils.findAnnotation(method, Operation.class);
        if (operationAnno == null) {
            return false;
        }
        cacheMethodDescription(method.getDeclaringClass(), method, operationAnno);
        return true;
    }
    
    @Override
    public AuditDTO doBefore(@Nonnull MethodInvocation invocation, @Nonnull ZooAuditLogApiProperties zooAuditLogApiProperties) {
        Set<Class<?>> ignoreRequestTypes = Optional.ofNullable(zooAuditLogApiProperties.getIgnoreRequestTypes())
                .orElse(Collections.emptySet());
        Method method = invocation.getMethod();
        Class<?> declaringClass = method.getDeclaringClass();
        AuditDTO auditInfo = new AuditDTO();
        auditInfo.setTraceId(MDC.get(BaseConstant.TRACE_ID));
        auditInfo.setTraceXd(MDC.get(BaseConstant.TRACE_XD));
        auditInfo.setUserId(ZooContext.Auth.currUserId());
        auditInfo.setOperationTime(LocalDateTime.now());
        // operationDesc
        auditInfo.setOperationDesc(METHOD_AND_DESC_CACHE.get(determineMethodKey(declaringClass, method)));
        // requestParamMap
        Object[] parameterValues = invocation.getArguments();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames != null) {
            Map<String, Object> requestParamMap = new LinkedHashMap<>();
            int length = parameterNames.length;
            for (int i = 0; i < length; i++) {
                Object parameterValue = parameterValues[i];
                if (parameterValue != null && ignoreRequestTypes.stream().anyMatch(x -> x.isAssignableFrom(parameterValue.getClass()))) {
                    requestParamMap.put(parameterNames[i], "<Hit-Ignore-Request-Type>");
                } else {
                    requestParamMap.put(parameterNames[i], parameterValue);
                }
            }
            auditInfo.setRequestParamMap(requestParamMap);
        }
        HttpServletRequest request = ZooContext.Http.httpServletRequest();
        if (request != null) {
            auditInfo.setClientIpAddress(ZooContext.Http.clientIpAddress(request));
            auditInfo.setClientUserAgent(request.getHeader("USER-AGENT"));
            auditInfo.setContextInfo(ZooContext.unmodifiableContext());
            auditInfo.setRequestMethod(request.getMethod());
            auditInfo.setRequestUri(request.getRequestURI());
        }
    
        Long latestAuditLogId = ZooContext.AUDIT_LOG.latestAuditLogId();
        auditInfo.setPid(latestAuditLogId);
        auditInfo.setId(IdUtil.getSnowflakeNextId());
        ZooContext.AUDIT_LOG.setLatestAuditLogId(auditInfo.getId());
        return auditInfo;
    }
    
    @Override
    public AuditDTO doAfter(@Nonnull MethodInvocation invocation, Throwable throwable,
                            AuditDTO beforeResult, Object targetPointResult,
                            @Nonnull ZooAuditLogApiProperties zooAuditLogApiProperties) {
        if (beforeResult == null) {
            log.warn("beforeResult is null. skip doAfter.");
            return null;
        }
        Set<Class<?>> ignoreResponseTypes = Optional.ofNullable(zooAuditLogApiProperties.getIgnoreResponseTypes())
                .orElse(Collections.emptySet());
        if (beforeResult.getUserId() == null) {
            beforeResult.setUserId(ZooContext.Auth.currUserId());
        }
        if (targetPointResult != null && ignoreResponseTypes.stream().anyMatch(x -> x.isAssignableFrom(targetPointResult.getClass()))) {
            beforeResult.setResponseResult("<Hit-Ignore-Response-Type>");
        } else {
            beforeResult.setResponseResult(targetPointResult);
        }
        beforeResult.setIfSuccess(throwable == null);
        beforeResult.setExceptionInfo(throwable == null ? null : ExceptionUtil.getStackTraceMessage(throwable));
        return beforeResult;
    }
    
    /**
     * 缓存方法描述
     */
    private static void cacheMethodDescription(@Nonnull Class<?> targetClass, @Nonnull Method method, @Nonnull Operation operationAnno) {
        String methodUk = determineMethodKey(targetClass, method);
        Tag tagAnno = null;
        // AnnotatedElementUtils.findAllMergedAnnotations 支持获取父类注解、支持识别继承的注解
        Set<Tag> tagAnnoSet = AnnotatedElementUtils.findAllMergedAnnotations(targetClass, Tag.class);
        if (!CollectionUtils.isEmpty(tagAnnoSet)) {
            tagAnno = tagAnnoSet.iterator().next();
        }
        if (tagAnno != null) {
            tagAnnoSet = AnnotatedElementUtils.findAllMergedAnnotations(targetClass, Tags.class)
                    .stream().flatMap(x -> Stream.of(x.value())).collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(tagAnnoSet)) {
                tagAnno = tagAnnoSet.iterator().next();
            }
        }
        String tagInfo = "";
        if (tagAnno != null) {
            tagInfo = tagAnno.name();
            String tagDescription = tagAnno.description();
            if (StringUtils.isNotBlank(tagDescription)) {
                tagInfo = tagInfo + "(" + tagDescription + ")";
            }
        }
    
        String operationInfo = "";
        String summary = operationAnno.summary();
        String description = operationAnno.description();
        if (StringUtils.isNotBlank(summary)) {
            operationInfo = summary;
        }
        if (StringUtils.isNotBlank(description)) {
            if (operationInfo.length() > 0) {
                operationInfo = operationInfo + "\t";
            }
            operationInfo = operationInfo + description;
        }
        String methodDesc;
        if (StringUtils.isAnyBlank(tagInfo, operationInfo)) {
            methodDesc = tagInfo + operationInfo;
        } else {
            methodDesc = tagInfo + " | " + operationInfo;
        }
        METHOD_AND_DESC_CACHE.put(methodUk, methodDesc);
    }
    
    /**
     * 获取方法唯一名
     */
    private static String determineMethodKey(Class<?> targetClass, Method method) {
        String paramsJoinStr = null;
        Parameter[] parameters = method.getParameters();
        if (parameters != null && parameters.length > 0) {
            paramsJoinStr = Arrays.stream(parameters).map(Parameter::getName)
                    .collect(Collectors.joining(", "));
        }
        String methodUk = targetClass.getName() + "#" + method.getName();
        if (paramsJoinStr != null) {
            methodUk = methodUk + "(" + paramsJoinStr + ")";
        }
        return methodUk;
    }
}
