package com.ideaaedi.zoo.diy.feature.config.impl.nacos.properties;

import com.ideaaedi.zoo.commonbase.zoo_properties.LoggerLevelProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@Data
@RefreshScope
@EqualsAndHashCode(callSuper = true)
public class NacosLoggerLevelProperties extends LoggerLevelProperties {
    
    /**
     * 在nacos上修改日志级别配置后，延迟几秒刷新日志级别
     * <p>
     * 注：延迟是为了等context.refresh完成，确保最新获取到的日志级别就是refresh之后的
     * </p>
     */
    private int delayRefresh = 5;
}
