package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.commonbase.zoo_component.auth.AuthUrlWhitelist;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.annotation.ApiTag;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.apiinfo.ApiInfoCollector;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.apiinfo.ApiInfoHandler;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.customizer.EnumDescCustomizer;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.customizer.FieldTypeFormatCustomizer;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.customizer.ZooKnife4jOpenApiCustomizer;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.markdown.OpenApiExtendMarkdownProvider;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.properties.ZooKnife4jDIYGuideProperties;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.properties.ZooKnife4jProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.configuration.SpringDocConfiguration;
import org.springdoc.core.customizers.OpenApiBuilderCustomizer;
import org.springdoc.core.customizers.ServerBaseUrlCustomizer;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.JavadocProvider;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.SecurityService;
import org.springdoc.core.utils.PropertyResolverUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.springdoc.core.utils.Constants.SPRINGDOC_ENABLED;

/**
 * knife4j 自动配置
 */
@ConditionalOnWebApplication
@AutoConfigureBefore({SpringDocConfiguration.class, Knife4jAutoConfiguration.class})
@ConditionalOnProperty(name = SPRINGDOC_ENABLED, matchIfMissing = true)
@EnableConfigurationProperties({ZooKnife4jDIYGuideProperties.class, ZooKnife4jProperties.class})
@AutoConfigureOrder(AutoConfigurationConstant.ZOO_KNIFE4J_AUTO_CONFIGURATION_ORDER)
public class ZooKnife4jAutoConfiguration {
    
    static {
        Assert.isTrue(
                AutoConfigurationConstant.ZOO_KNIFE4J_AUTO_CONFIGURATION_CLASS_LONG_NAME.equals(ZooKnife4jAutoConfiguration.class.getName()),
                "ZOO_KNIFE4J_AUTO_CONFIGURATION_CLASS_LONG_NAME value is not correct, please revise it to '" + ZooKnife4jAutoConfiguration.class.getName() + "'"
        );
    }
    
    @Bean
    @Order
    @ConditionalOnMissingBean
    @ConditionalOnWebApplication
    @ConditionalOnBean(RequestMappingHandlerMapping.class)
    public ApiInfoCollector apiInfoCollector(ApplicationContext applicationContext,
                                             Optional<List<ApiInfoHandler>> apiInfoHandlerList) {
        return new ApiInfoCollector(applicationContext, apiInfoHandlerList);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public EnumDescCustomizer enumPropertyCustomizer() {
        return new EnumDescCustomizer();
    }
    
    @Bean
    @ConditionalOnMissingBean
    public FieldTypeFormatCustomizer fieldTypeFormatCustomizer() {
        return new FieldTypeFormatCustomizer();
    }
    
    /*
     * 通过@AutoConfigureBefore指定Knife4jAutoConfiguration.class，触发
     * com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration.knife4jOpenApiCustomizer
     * 上的@ConditionalOnMissingBean，使只加载此bean
     */
    @Bean
    @ConditionalOnMissingBean
    public ZooKnife4jOpenApiCustomizer zooKnife4jOpenApiCustomizer(
            @Autowired ZooKnife4jProperties zooKnife4jProperties,
            @Autowired(required = false) AuthUrlWhitelist authUrlWhitelist,
            @Autowired(required = false) OpenApiExtendMarkdownProvider openApiExtendMarkdownProvider,
            @Autowired(required = false) Knife4jProperties knife4jProperties
    ) {
        return new ZooKnife4jOpenApiCustomizer(zooKnife4jProperties, authUrlWhitelist, openApiExtendMarkdownProvider, knife4jProperties);
    }
    
    @Bean
    @Lazy(false)
    @ConditionalOnMissingBean
    public OpenAPIService openAPIBuilder(Optional<OpenAPI> openAPI,
                                         SecurityService securityParser,
                                         SpringDocConfigProperties springDocConfigProperties,
                                         PropertyResolverUtils propertyResolverUtils,
                                         Optional<List<OpenApiBuilderCustomizer>> openApiBuilderCustomisers,
                                         Optional<List<ServerBaseUrlCustomizer>> serverBaseUrlCustomisers,
                                         Optional<JavadocProvider> javadocProvider) {
        return new OpenAPIService(openAPI, securityParser, springDocConfigProperties, propertyResolverUtils,
                openApiBuilderCustomisers, serverBaseUrlCustomisers, javadocProvider) {
            @Override
            public Operation buildTags(HandlerMethod handlerMethod, Operation operation, OpenAPI openAPI,
                                       Locale locale) {
                Operation operationRes = super.buildTags(handlerMethod, operation, openAPI, locale);
                // 这部分为增强逻辑，对ApiTag order进行支持
                ApiTag apiTag = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), ApiTag.class);
                if (apiTag != null && StringUtils.isNotBlank(apiTag.name())) {
                    String name = apiTag.name();
                    int order = apiTag.order();
                    String description = apiTag.description();
                    List<Tag> tags = openAPI.getTags();
                    if (CollectionUtils.isEmpty(tags)) {
                        tags = new ArrayList<>();
                        openAPI.setTags(tags);
                    }
                    boolean exist = tags.stream().anyMatch(x -> name.equals(x.getName()));
                    if (!exist) {
                        Tag tag = new Tag();
                        openAPI.getTags().add(tag.name(name).description(StringUtils.defaultIfBlank(description,
                                name)).extensions(Map.of("x-order", order)));
                    }
                }
                return operationRes;
            }
        };
    }
}