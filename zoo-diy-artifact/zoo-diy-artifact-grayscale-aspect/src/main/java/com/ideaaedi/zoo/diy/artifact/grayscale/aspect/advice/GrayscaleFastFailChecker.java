package com.ideaaedi.zoo.diy.artifact.grayscale.aspect.advice;

import com.ideaaedi.zoo.diy.artifact.grayscale.aspect.annotation.Grayscale;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;

/**
 * 启动时检查{@link Grayscale}使用方式是否正确
 */
@Slf4j
public class GrayscaleFastFailChecker implements BeanPostProcessor {
    
    @Override
    public Object postProcessBeforeInitialization(@Nonnull Object bean, @Nonnull String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Grayscale grayscale = AnnotatedElementUtils.findMergedAnnotation(method, Grayscale.class);
            if (grayscale == null) {
                continue;
            }
            String gray = grayscale.gray();
            String condition = grayscale.condition();
            if (StringUtils.isBlank(gray)) {
                throw new IllegalArgumentException("gray cannot be blank. see "  + method.toGenericString());
            }
            Method grayMethod = GrayscaleAspect.findMethod(clazz, gray, method.getReturnType(), method.getParameterTypes());
            if (grayMethod == null) {
                throw new IllegalArgumentException(
                        String.format("""
                        gray-method must satisfy:
                        1. gray-method must exist.
                        2. gray-method and origin-method must be the same method parameter type.
                        3. gray-method must has same method return type(or sub-type of return-type) with origin-method.
                        
                        Please check at %s
                        """, method.toGenericString())
                );
            }
            if (StringUtils.isBlank(condition)) {
                throw new IllegalArgumentException("condition cannot be blank. see " + method.toGenericString());
            }
            Method conditionMethod = GrayscaleAspect.findMethod(clazz, condition, boolean.class, method.getParameterTypes());
            if (conditionMethod == null) {
                throw new IllegalArgumentException(
                        String.format("""
                        condition-method must satisfy:
                        1. condition-method must exist.
                        2. condition-method and origin-method must be the same method parameter type.
                        3. condition-method return type must be 'boolean'.
                        
                        Please check at %s
                        """, method.toGenericString())
                );
            }
        }
        return bean;
    }
}
