package com.ideaaedi.zoo.diy.feature.expression.engine.api.properties;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.annotation.ExpressionReplacer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "zoo.expression-engine")
public class ZooExpressionEngineProperties {
    
    /**
     * 是否启用{@link ExpressionReplacer}注解支持
     * <p>
     *  这个注解允许你可选的对被标注方法进行逻辑替换（以执行表达式的方式）
     * </p>
     *
     */
    private boolean expReplacerEnable = true;
    
}
