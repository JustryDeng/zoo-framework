package com.ideaaedi.zoo.diy.artifact.auth.satoken.properties;

import cn.dev33.satoken.error.SaErrorCode;
import com.ideaaedi.zoo.commonbase.entity.CodeMsgProvider;
import com.ideaaedi.zoo.commonbase.zoo_component.auth.AuthUrlWhitelist;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * sa-token相关配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = "sa-token.zoo-auth")
public class ZooAuthProperties implements AuthUrlWhitelist {
    
    @Valid
    @NestedConfigurationProperty
    private ZooAuthBasic basic;
    
    @Valid
    @NestedConfigurationProperty
    @NotNull(message = "zoo-token filter cannot be blank.")
    private ZooAuthToken token;
    
    @Override
    public Collection<String> whitelist() {
        if (this.basic == null) {
            return Collections.emptyList();
        }
        return this.basic.whitelist();
    }
    
    
    /**
     * token配置
     */
    @Data
    public static class ZooAuthToken {
        
        /**
         * jwt密钥
         */
        @NotBlank(message = "jwtSecret cannot be blank.")
        private String jwtSecret;
    }
    
    /**
     * 基础鉴权配置
     */
    @Data
    public static class ZooAuthBasic implements AuthUrlWhitelist {
        
        /**
         * 是否对那些认证之后的请求，再进行鉴权
         */
        private boolean validUrlPermit = true;
        
        /**
         * 可以无需token的url白名单
         */
        private List<String> urlWhitelist;
        
        /**
         * 错误信息转义
         * <pre>
         * key：sa_token错误码， 详见 {@link SaErrorCode}
         * value：本系统的错误码，格式为：枚举全类名.枚举值
         * </pre>
         */
        private Map<Integer, CodeMsgProvider> errorMapping;
        
        @Override
        public boolean validUrlPermit() {
            return validUrlPermit;
        }
        
        @Override
        public Collection<String> whitelist() {
            return urlWhitelist;
        }
    }
}
