package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.customizer;

import com.github.xiaoymin.knife4j.core.conf.GlobalConstants;
import com.github.xiaoymin.knife4j.core.extend.OpenApiExtendMarkdownFile;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jSetting;
import com.github.xiaoymin.knife4j.spring.extension.Knife4jOpenApiCustomizer;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import com.ideaaedi.zoo.commonbase.zoo_component.auth.AuthUrlWhitelist;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.markdown.OpenApiExtendMarkdownProvider;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.properties.ZooKnife4jProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 定义文档信息、安全策略
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class ZooKnife4jOpenApiCustomizer extends Knife4jOpenApiCustomizer {
    
    private static final AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    private final ZooKnife4jProperties zooKnife4jProperties;
    
    private final AuthUrlWhitelist authUrlWhitelist;
    
    private final OpenApiExtendMarkdownProvider openApiExtendMarkdownProvider;
    
    private final Knife4jProperties knife4jProperties;
    
    public ZooKnife4jOpenApiCustomizer(ZooKnife4jProperties zooKnife4jProperties,
                                       @Nonnull AuthUrlWhitelist authUrlWhitelist,
                                       @Nonnull OpenApiExtendMarkdownProvider openApiExtendMarkdownProvider,
                                       @Nonnull Knife4jProperties knife4jProperties) {
        super(knife4jProperties);
        this.zooKnife4jProperties = zooKnife4jProperties;
        this.authUrlWhitelist = authUrlWhitelist;
        this.openApiExtendMarkdownProvider = openApiExtendMarkdownProvider;
        this.knife4jProperties = knife4jProperties;
    }
    
    @Override
    public void customise(OpenAPI openApi) {
        // step1. 文档信息
        settingApiInfo(openApi);
    
        // step2. 文档鉴权Security
        settingSecurityScheme(openApi);
    
        // step3. knife4j扩展
        enhanceKnife4j(openApi);
    }
    
    /**
     * 设置文档信息
     */
    private void settingApiInfo(OpenAPI openApi) {
        Info info = zooKnife4jProperties.getInfo();
        if (info != null) {
            boolean alreadySet = Optional.of(openApi)
                    .map(OpenAPI::getInfo)
                    .map(Info::getTitle)
                    .map(title -> !"OpenAPI definition".equals(title))
                    .orElse(false);
            if (!alreadySet) {
                openApi.setInfo(info);
            }
        }
    }
    
    /**
     * 设置安全策略
     */
    private void settingSecurityScheme(OpenAPI openApi) {
        ZooKnife4jProperties.SecurityScheme security = zooKnife4jProperties.getSecurityScheme();
        if (security != null) {
            // 添加鉴权scheme
            String schemeName = security.getSchemeName();
            String headerKey = security.getHeaderKey();
            String scheme = security.getScheme();
            String bearerFormat = security.getBearerFormat();
            SecurityScheme securityScheme = new SecurityScheme();
            securityScheme
                    .name(headerKey)
                    .type(SecurityScheme.Type.HTTP)
                    .in(SecurityScheme.In.HEADER);
            if (StringUtils.isNotBlank(scheme)) {
                securityScheme.scheme(scheme);
            }
            if (StringUtils.isNotBlank(scheme)) {
                securityScheme.bearerFormat(bearerFormat);
            }
            openApi.components(new Components().addSecuritySchemes(schemeName, securityScheme));
            
            // 应用鉴权scheme
            SecurityRequirement defaultSecurityRequirement = new SecurityRequirement().addList(schemeName);
            Collection<String> whitelist =
                    Optional.ofNullable(authUrlWhitelist).map(AuthUrlWhitelist::whitelist).orElse(Collections.emptyList());
            Optional.ofNullable(openApi.getPaths())
                    .map(Paths::entrySet)
                    .ifPresent(entrySet -> entrySet.forEach(entry -> {
                                String url = entry.getKey();
                                // 白名单中的url不需要鉴权
                                boolean allow = whitelist.stream()
                                        .anyMatch(allowUrlPattern -> (antPathMatcher.match(allowUrlPattern, url)));
                                if (allow) {
                                    return;
                                }
                                PathItem pathItem = entry.getValue();
                                readOperations(pathItem).forEach(operation -> {
                                    if (operation == null) {
                                        return;
                                    }
                                    List<SecurityRequirement> securityRequirementList = operation.getSecurity();
                                    // 如果已经有了 安全策略，则不添加默认的
                                    if (!CollectionUtils.isEmpty(securityRequirementList)) {
                                        return;
                                    }
                                    if (securityRequirementList == null) {
                                        securityRequirementList = new ArrayList<>(1);
                                        operation.setSecurity(securityRequirementList);
                                    }
                                    securityRequirementList.add(defaultSecurityRequirement);
                                });
                            })
                    );
        }
    }
    
    /**
     * 增强 Knife4j
     */
    private void enhanceKnife4j(OpenAPI openApi) {
        Map<String, Object> objectMap = new HashMap<>();
        List<OpenApiExtendMarkdownFile> allMarkdownFileList = new ArrayList<>(16);
        if (knife4jProperties != null && knife4jProperties.isEnable()) {
            Knife4jSetting setting = knife4jProperties.getSetting();
            OpenApiExtensionResolver openApiExtensionResolver = new OpenApiExtensionResolver(setting, knife4jProperties.getDocuments());
            openApiExtensionResolver.start();
            objectMap.put(GlobalConstants.EXTENSION_OPEN_SETTING_NAME, setting);
            List<OpenApiExtendMarkdownFile> markdownFileList = openApiExtensionResolver.getMarkdownFiles();
            if (!CollectionUtils.isEmpty(markdownFileList)) {
                allMarkdownFileList.addAll(markdownFileList);
            }
        }
        if (openApiExtendMarkdownProvider != null) {
            List<OpenApiExtendMarkdownFile> markdownFiles = openApiExtendMarkdownProvider.provideMarkdowns();
            if (!CollectionUtils.isEmpty(markdownFiles)) {
                allMarkdownFileList.addAll(markdownFiles);
            }
        }
        objectMap.put(GlobalConstants.EXTENSION_OPEN_MARKDOWN_NAME, allMarkdownFileList);
        openApi.addExtension(GlobalConstants.EXTENSION_OPEN_API_NAME, objectMap);
    }
    
    /*
     * 获取相关操作
     */
    @Nonnull
    public List<Operation> readOperations(PathItem pathItem) {
        if (pathItem == null) {
            return Collections.emptyList();
        }
        List<Operation> list = new ArrayList<>();
        list.add(pathItem.getGet());
        list.add(pathItem.getDelete());
        list.add(pathItem.getPut());
        list.add(pathItem.getPost());
        list.add(pathItem.getGet());
        return list.stream().filter(Objects::nonNull).toList();
    }
}