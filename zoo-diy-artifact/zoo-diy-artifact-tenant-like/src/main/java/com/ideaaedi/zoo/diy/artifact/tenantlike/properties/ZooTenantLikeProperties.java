package com.ideaaedi.zoo.diy.artifact.tenantlike.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 租户相关配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "zoo.tenant")
public class ZooTenantLikeProperties {
    /**
     * 作为bizTenant的tenant的层级
     * <p>
     * 注：bizTenantLevel的值应大于1
     * </p>
     * <pre>
     * 如：租户 '1,'        的level=1
     * 如：租户 '1,2,'      的level=2
     * 如：租户 '1,2,10,'   的level=3
     * </pre>
     */
    private int bizTenantLevel = 2;
}
