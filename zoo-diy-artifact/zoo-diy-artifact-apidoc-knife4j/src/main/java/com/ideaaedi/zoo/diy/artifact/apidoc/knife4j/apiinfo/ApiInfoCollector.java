package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.apiinfo;

import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.entity.ApiInfoDTO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * api信息采集器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class ApiInfoCollector implements ApplicationListener<ContextRefreshedEvent> {
    
    @Resource
    private final ApplicationContext applicationContext;
    
    private final Optional<List<ApiInfoHandler>> apiInfoHandlerList;
    
    public ApiInfoCollector(ApplicationContext applicationContext, Optional<List<ApiInfoHandler>> apiInfoHandlerList) {
        this.applicationContext = applicationContext;
        this.apiInfoHandlerList = apiInfoHandlerList;
    }
    
    @Override
    public void onApplicationEvent(@Nonnull ContextRefreshedEvent event) {
        List<ApiInfoDTO> apiInfoList = new ArrayList<>();
        applicationContext.getBeansOfType(RequestMappingHandlerMapping.class).values().forEach(
                requestMappingHandlerMapping -> {
                    Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                    handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
                        ApiInfoDTO apiInfo = new ApiInfoDTO();
                        Class<?> clazz = handlerMethod.getBeanType();
                        Method method = handlerMethod.getMethod();
                        apiInfo.setClazz(clazz);
                        apiInfo.setMethod(method);
                        apiInfo.setRequestMappingInfo(requestMappingInfo);
                        apiInfoList.add(apiInfo);
                    });
                }
        );
        apiInfoHandlerList.ifPresent(apiInfoHandlerList -> apiInfoHandlerList.forEach(apiInfoHandler -> apiInfoHandler.handle(apiInfoList)));
    }
}
