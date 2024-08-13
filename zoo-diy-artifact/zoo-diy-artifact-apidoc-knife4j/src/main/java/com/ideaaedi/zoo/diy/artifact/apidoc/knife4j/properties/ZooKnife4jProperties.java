package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.properties;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.info.Info;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * zoo对openapi3的增强配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = "springdoc.zoo-knife4j")
public class ZooKnife4jProperties {
    
    /*
     * java代码 与 文档中的字段类型映射
     */
    @Valid
    @NestedConfigurationProperty
    private List<FileTypeMapping> fileTypeMappings;
    
    /*
     * 接口文档信息
     */
    @NestedConfigurationProperty
    private Info info;
    
    /*
     * 接口鉴权能力
     */
    @Valid
    @NestedConfigurationProperty
    private SecurityScheme securityScheme;
    
    /**
     * java代码 与 文档中的字段类型映射
     */
    @Data
    public static class FileTypeMapping {
        
        @NotBlank(message = "类型不能为空")
        @Schema(description = "类型全类名", example = "java.time.LocalTime")
        private String type;
        
        @NotNull(message = "转换类型不能为空")
        @Schema(description = "转换类型", example = "string")
        @Pattern(regexp = "^string|number|integer|boolean$", message = "只能为string、number、integer、boolean")
        private String targetType;
        
        @Schema(description = "数据格式说明。 当不为空时，将在类型后面加上括号，并在括号里暂时此内容")
        private String format;
    }
    
    /**
     * 文档鉴权能力
     */
    @Data
    public static class SecurityScheme {
        
        @NotBlank(message = "当前鉴权策略的名称")
        @Schema(description = "当前鉴权策略的名称")
        private String schemeName = "defaultBearerAuth";
        
        @NotBlank(message = "请求头key不能为空")
        @Schema(description = "请求头 - key", example = "Authentication")
        private String headerKey;
        
        /**
         * @see io.swagger.v3.oas.models.security.SecurityScheme#getScheme()
         */
        @Schema(description = "安全方案的名称", example = "basic、bearer、digest...")
        private String scheme;
    
        /**
         * @see io.swagger.v3.oas.models.security.SecurityScheme#getBearerFormat()
         */
        @Schema(description = "当前权限的风格")
        private String bearerFormat;
 
    }
}
