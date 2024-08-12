package com.ideaaedi.zoo.diy.feature.fieldperm.api.advice;

import com.ideaaedi.zoo.diy.feature.fieldperm.api.spi.MethodArgFieldParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * 字段权限切面
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true)
public class FieldPermPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
    
    private final MethodArgFieldParser methodArgFieldParser;
    
    private final FieldPermValidator fieldPermValidator;
    
    public FieldPermPointcutAdvisor(MethodArgFieldParser methodArgFieldParser, FieldPermValidator fieldPermValidator) {
        this.methodArgFieldParser = methodArgFieldParser;
        this.fieldPermValidator = fieldPermValidator;
        // 设置切面
        setAdvice(fieldPermValidator);
        // 设置优先级
        setOrder(fieldPermValidator.getOrder());
    }
    
    @Override
    public boolean matches(@Nonnull Method method, @Nonnull Class<?> clazz) {
        if (methodArgFieldParser.support(method, clazz)) {
            fieldPermValidator.addArgFieldInfos(methodArgFieldParser.parse(method, clazz));
            return true;
        }
        return false;
    }
}
