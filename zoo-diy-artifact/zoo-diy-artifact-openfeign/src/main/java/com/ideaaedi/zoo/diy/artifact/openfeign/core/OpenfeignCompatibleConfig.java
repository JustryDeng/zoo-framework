package com.ideaaedi.zoo.diy.artifact.openfeign.core;

import feign.MethodMetadata;
import feign.Util;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理{@link FeignClient}与{@link RequestMapping}的兼容问题
 * <p>
 * 解决{@link SpringMvcContract#processAnnotationOnClass(MethodMetadata, Class)}的报错：@RequestMapping annotation not allowed on @FeignClient interfaces
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@AutoConfiguration(OpenfeignCompatibleConfig.OPENFEIGN_COMPATIBLE_CONFIG_BEAN_NAME)
@ConditionalOnMissingBean(name = OpenfeignCompatibleConfig.OPENFEIGN_COMPATIBLE_CONFIG_BEAN_NAME)
public class OpenfeignCompatibleConfig {
    
    public static final String OPENFEIGN_COMPATIBLE_CONFIG_BEAN_NAME = "openfeignCompatibleConfig";
    
    @Component
    public static class MySvcSpringContract extends SpringMvcContract {
        
        private ResourceLoader resourceLoader = new DefaultResourceLoader();
        
        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }
        
        /**
         * 兼容处理：@RequestMapping annotation not allowed on @FeignClient interfaces
         */
        @Override
        protected void processAnnotationOnClass(MethodMetadata data, Class<?> clz) {
            if (data != null && clz.getInterfaces().length == 0) {
                RequestMapping classAnnotation = AnnotatedElementUtils.findMergedAnnotation(clz, RequestMapping.class);
                if (classAnnotation != null && classAnnotation.value().length > 0) {
                    String pathValue = Util.emptyToNull(classAnnotation.value()[0]);
                    pathValue = this.resolve(pathValue);
                    if (!pathValue.startsWith("/")) {
                        pathValue = "/" + pathValue;
                    }
                    data.template().uri(pathValue);
                }
            }
        }
        
        private String resolve(String value) {
            if (StringUtils.hasText(value) && resourceLoader instanceof ConfigurableApplicationContext) {
                return ((ConfigurableApplicationContext) resourceLoader).getEnvironment().resolvePlaceholders(value);
            }
            return value;
        }
    }
}
