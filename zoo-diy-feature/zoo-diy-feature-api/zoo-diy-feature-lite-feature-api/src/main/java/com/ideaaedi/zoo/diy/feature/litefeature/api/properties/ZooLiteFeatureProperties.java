package com.ideaaedi.zoo.diy.feature.litefeature.api.properties;

import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = "zoo.lite-feature")
public class ZooLiteFeatureProperties {
    
    @Valid
    @NestedConfigurationProperty
    private MultiLockProperties lock = new MultiLockProperties();
    
    /**
     * 分布式锁相关配置
     */
    @Data
    public static class MultiLockProperties {
        
        /**
         * 默认的锁实现器id
         */
        private String defaultSupplierId;
    }
}
