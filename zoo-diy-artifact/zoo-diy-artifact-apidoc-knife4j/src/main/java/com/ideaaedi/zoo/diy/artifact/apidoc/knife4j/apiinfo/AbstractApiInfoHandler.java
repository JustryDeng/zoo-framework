package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.apiinfo;

import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.annotation.ApiTag;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.entity.ApiInfoDTO;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.entity.DefaultApiDetailDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * api信息处理器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public abstract class AbstractApiInfoHandler implements ApiInfoHandler {
    
    @Override
    public void handle(@Nonnull List<ApiInfoDTO> apiInfoList) {
        List<DefaultApiDetailDTO> apiDetailList = apiInfoList.stream().map(apiInfo -> {
            Class<?> clazz = apiInfo.getClazz();
            Method method = apiInfo.getMethod();
            RequestMappingInfo requestMappingInfo = apiInfo.getRequestMappingInfo();
            Set<RequestMethod> methods = requestMappingInfo.getMethodsCondition().getMethods();
            if (CollectionUtils.isEmpty(methods)) {
                methods = Arrays.stream(RequestMethod.values()).collect(Collectors.toSet());
            }
            Set<String> requestPathSet = requestMappingInfo.getDirectPaths();
            if (CollectionUtils.isEmpty(requestPathSet)) {
                requestPathSet = Optional.ofNullable(requestMappingInfo.getPathPatternsCondition())
                        .map(PathPatternsRequestCondition::getPatternValues)
                        .orElse(Collections.emptySet());
            }
            if (CollectionUtils.isEmpty(requestPathSet)) {
                requestPathSet = Optional.ofNullable(requestMappingInfo.getPatternsCondition())
                        .map(PatternsRequestCondition::getPatterns)
                        .orElse(Collections.emptySet());
            }
            DefaultApiDetailDTO defaultApiDetail = new DefaultApiDetailDTO();
            defaultApiDetail.setClazz(clazz);
            defaultApiDetail.setMethod(method);
            defaultApiDetail.setClassDesc(determineClassDesc(clazz));
            defaultApiDetail.setMethodDesc(determineMethodDesc(method));
            defaultApiDetail.setRequestPathSet(requestPathSet);
            defaultApiDetail.setRequestMethodSet(methods);
            return defaultApiDetail;
        }).toList();
        apiDetailList(apiDetailList);
    }
    
    /*
     * api详情列表
     */
    public abstract void apiDetailList(@Nonnull List<DefaultApiDetailDTO> apiDetailInfoList);
    
    /**
     * 获取类说明
     */
    protected String determineClassDesc(Class<?> clazz) {
        ApiTag apiTag = AnnotationUtils.findAnnotation(clazz, ApiTag.class);
        if (apiTag != null) {
            return apiTag.name();
        }
        Tag tag = AnnotationUtils.findAnnotation(clazz, Tag.class);
        if (tag == null) {
            Tags tags = AnnotationUtils.findAnnotation(clazz, Tags.class);
            tag = Optional.ofNullable(tags)
                    .map(Tags::value)
                    .map(x -> x[0])
                    .orElse(null);
        }
        return Optional.ofNullable(tag)
                .map(Tag::name)
                .orElse(null);
    }
    
    /**
     * 获取方法说明
     */
    protected String determineMethodDesc(Method method) {
        Operation operation = AnnotationUtils.findAnnotation(method, Operation.class);
        return Optional.ofNullable(operation)
                .map(x -> {
                    String summary = operation.summary();
                    return StringUtils.defaultIfBlank(summary, operation.description());
                })
                .orElse(null);
    }
}
