package com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.Ordered;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

/**
 * zoo对请求响应的加解密配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = "zoo.req-resp-enc-dec")
public class ZooReqrespEncdecProperties {
    
    /**
     * 响应加密
     */
    @Valid
    @NotNull
    @NestedConfigurationProperty
    private RespEncryptProperties respEncrypt = new RespEncryptProperties();
    
    /**
     * 请求解密
     */
    @Valid
    @NotNull
    @NestedConfigurationProperty
    private ReqDecryptProperties reqDecrypt = new ReqDecryptProperties();
    
    /**
     * 响应加密
     */
    @Data
    public static class RespEncryptProperties {
        /**
         * 是否开启响应加密
         */
        private boolean enable = false;
        
        /**
         * 设置相关加密实现的spring-bean优先级（如果实现支持order的话）
         */
        private int order = Ordered.LOWEST_PRECEDENCE;
        
        /**
         * 加密使用的aes-key
         */
        private String aesKey = "0000000000000000";
        
        /**
         * 要加密的host
         * <p>
         * 支持ant匹配
         * </p>
         */
        private Set<String> includeHosts = new HashSet<>();
        
        /**
         * 不加密的host
         * <p>
         * 支持ant匹配
         * </p>
         */
        private Set<String> excludeHosts = new HashSet<>();
        
        /**
         * 要加密的path
         * <p>
         * 支持ant匹配
         * </p>
         */
        private Set<String> includePaths = new HashSet<>();
        
        /**
         * 不加密的path
         * <p>
         * 支持ant匹配
         * </p>
         */
        private Set<String> excludePaths = new HashSet<>();
        
        /**
         * 当存在指定的响应头时，加密
         */
        private Set<String> includeExistRespHeaders = new HashSet<>();
        
        /**
         * 当存在指定的响应头时，不加密
         */
        private Set<String> excludeExistRespHeaders = new HashSet<>();
        
    }
    
    /**
     * 请求解密
     */
    @Data
    public static class ReqDecryptProperties {
        
        /**
         * 是否开启请求解密
         */
        private boolean enable = false;
    
        /**
         * 设置相关解密实现的spring-bean优先级（如果实现支持order的话）
         */
        private int order = Ordered.HIGHEST_PRECEDENCE + 1;
    
        /**
         * 解密使用的aes-key
         */
        private String aesKey = "0000000000000000";
    
        /**
         * 要解密的host
         * <p>
         * 支持ant匹配
         * </p>
         */
        private Set<String> includeHosts = new HashSet<>();
        
        /**
         * 不解密的host
         * <p>
         * 支持ant匹配
         * </p>
         */
        private Set<String> excludeHosts = new HashSet<>();
    
        /**
         * 要解密的path
         * <p>
         * 支持ant匹配
         * </p>
         */
        private Set<String> includePaths = new HashSet<>();
        
        /**
         * 不解密的path
         * <p>
         * 支持ant匹配
         * </p>
         */
        private Set<String> excludePaths = new HashSet<>();
    
        /**
         * 当存在指定的请求头时，解密
         */
        private Set<String> includeExistReqHeaders = new HashSet<>();
    
        /**
         * 当存在指定的请求头时，不解密
         */
        private Set<String> excludeExistReqHeaders = new HashSet<>();
    }
    
}
