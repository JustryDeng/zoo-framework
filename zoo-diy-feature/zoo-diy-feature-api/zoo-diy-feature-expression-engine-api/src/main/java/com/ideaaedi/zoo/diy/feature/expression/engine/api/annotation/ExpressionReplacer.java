package com.ideaaedi.zoo.diy.feature.expression.engine.api.annotation;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.entity.ExpressionScriptInfoDTO;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.spi.ExpressionReplaceExecutor;
import com.ideaaedi.zoo.diy.feature.expression.engine.api.spi.ExpressionScriptLoader;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 表达式逻辑替换器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpressionReplacer {
    
    /**
     * 业务键
     *
     * <p>
     * 如果此键对应的表达式脚本（见：{@link ExpressionScriptLoader#load(String)}），
     * 支持的话（见：{@link ExpressionReplaceExecutor#support(String, Method, Map)}）,
     * 那么本次对方法的调用逻辑，将会走表达式{@link ExpressionScriptInfoDTO#getExpressionScript()}的逻辑并返回（不会走原方法的逻辑）
     * </p>
     */
    String value();
}
