package com.ideaaedi.zoo.diy.artifact.openfeign.core;

import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import com.ideaaedi.zoo.diy.artifact.openfeign.constant.OpenfeignConstant;
import com.ideaaedi.zoo.diy.artifact.openfeign.holder.OnceFeignInfo;
import com.ideaaedi.zoo.diy.artifact.openfeign.holder.OnceFeignInfoHolder;
import com.ideaaedi.zoo.diy.artifact.openfeign.properties.ZooOpenfeignProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * feign请求拦截器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class OpenfeignRequestInterceptor implements RequestInterceptor {
    
    private final ZooOpenfeignProperties zooOpenfeignProperties;
    
    private final Environment environment;
    
    public OpenfeignRequestInterceptor(ZooOpenfeignProperties zooOpenfeignProperties, Environment environment) {
        this.zooOpenfeignProperties = zooOpenfeignProperties;
        this.environment = environment;
    }
    
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 配置的信息
        List<ZooOpenfeignProperties.HeaderTrans> appendHeaders = zooOpenfeignProperties.getAppendHeaders();
        if (!CollectionUtils.isEmpty(appendHeaders)) {
            HttpServletRequest httpServletRequest = ZooContext.Http.httpServletRequest();
            for (ZooOpenfeignProperties.HeaderTrans appendHeader : appendHeaders) {
                String header = appendHeader.getHeader();
                String value = appendHeader.getValue();
                ZooOpenfeignProperties.HeaderValueSource source = appendHeader.getSource();
                String defaultValue = appendHeader.getDefaultValue();
                boolean encodeValue = appendHeader.isEncodeValue();
                switch (source) {
                    case MDC -> value = MDC.get(value);
                    case REQUEST_HEADER -> {
                        value = httpServletRequest == null ? null : httpServletRequest.getHeader(value);
                    }
                    case SPRING_ENV -> {
                        value = environment.getProperty(value);
                    }
                }
                if (StringUtils.isBlank(value)) {
                    value = defaultValue;
                }
                if (StringUtils.isNotBlank(value)) {
                    if (encodeValue) {
                        value = URLEncoder.encode(value, StandardCharsets.UTF_8);
                    }
                    requestTemplate.header(header, value);
                }
            }
        }
    
        // 线程级的信息
        OnceFeignInfo onceFeignInfo = OnceFeignInfoHolder.TEMP_FEIGN_HOLDER.get().peekFirst();
        boolean isOnceFeignClientDefaultUrl = OpenfeignConstant.ONCE_FEIGN_DEFAULT_URL.equals(requestTemplate.feignTarget().url());
        if (isOnceFeignClientDefaultUrl && onceFeignInfo == null) {
            throw new IllegalArgumentException("You should provide url by 'point @OnceFeignClient.url' or 'use OnceFeignProvider'.");
        }
        if (onceFeignInfo != null) {
            String urlPrefix = onceFeignInfo.getUrlPrefix();
            Map<String, Object> headers = onceFeignInfo.getHeaders();
            if (StringUtils.isNotBlank(urlPrefix)) {
                requestTemplate.target(urlPrefix);
            }
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach((header, value) -> {
                    if (value == null) {
                        return;
                    }
                    requestTemplate.header(header, value.toString());
                });
            }
        }
    }
}
