package com.ideaaedi.zoo.foundation.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * guide 配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "zoo.guide")
public class ZooFoundationGuideProperties {
    
    /** 是否打印概览 */
    private boolean enable;
    
     /** 是否打印概览详情 */
    private boolean showDetail;
    
}
