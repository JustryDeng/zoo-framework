package com.ideaaedi.zoo.diy.feature.expression.engine.api.advice;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.annotation.ExpressionReplacer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * 表达式逻辑替换切点
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true)
public class ExpressionReplacePointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
    
    public ExpressionReplacePointcutAdvisor(ExpressionReplaceInterceptor expressionReplaceInterceptor) {
        // 设置切面
        setAdvice(expressionReplaceInterceptor);
        // 设置优先级
        setOrder(expressionReplaceInterceptor.getOrder());
    }
    
    @Override
    public boolean matches(@Nonnull Method method, @Nonnull Class<?> clazz) {
        return AnnotatedElementUtils.findMergedAnnotation(method, ExpressionReplacer.class) != null;
    }
}
