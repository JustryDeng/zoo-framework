package com.ideaaedi.zoo.diy.feature.expression.engine.api.spi;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.entity.ExpressionScriptInfoDTO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 表达式逻辑替代器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface ExpressionReplaceExecutor {
    
    /**
     * 是否支持对原代码进行逻辑替换
     *
     * @param bizKey 业务键
     * @param params 方法
     * @param method 方法的参数信息
     *
     * @return 要运行的表达式信息（为null则表示不替换，依旧走原方法代码）
     */
    @Nullable
    ExpressionScriptInfoDTO support(@Nonnull String bizKey, @Nonnull Method method,
                                    @Nonnull Map<String, Object> params);
    
    /**
     * 执行表达式脚本（替换原代码逻辑）
     *
     * @param expressionScriptInfo 表达式信息
     * @param params 方法的参数信息
     *
     * @return 方法的参数字段信息
     */
    Object replace(@Nonnull ExpressionScriptInfoDTO expressionScriptInfo, @Nonnull Method method,
                   @Nonnull Map<String, Object> params);
    
}
