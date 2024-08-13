package com.ideaaedi.zoo.commonbase.zoo_util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

/**
 * spel工具类
 * <p>
 * 基于以下版本的common-spring进行的改造
 * </p>
 *
 * <pre>
 * {@code
 *   <dependency>
 *       <groupId>com.idea-aedi</groupId>
 *       <artifactId>common-spring</artifactId>
 *       <artifactId>2100.9.2</artifactId>
 *   </dependency>
 * }
 * </pre>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class SpelUtil {
    
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();
    
    private static final StandardReflectionParameterNameDiscoverer DISCOVERER =
            new StandardReflectionParameterNameDiscoverer();
    
    private static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext();

    private static BeanResolver beanResolver;
    
    /**
     * 初始化bean解析器
     * <br />
     * 当需要调用spring-bean的方法时，需要先初始化 bean解析器
     */
    public static void initBeanResolver(BeanResolver beanResolver) {
        if (SpelUtil.beanResolver != null && !SpelUtil.beanResolver.equals(beanResolver)) {
            throw new IllegalStateException("beanResolver already been initialized.");
        }
        synchronized (SpelUtil.class) {
            if (SpelUtil.beanResolver != null) {
                if (SpelUtil.beanResolver.equals(beanResolver)) {
                    return;
                }
                throw new IllegalStateException("beanResolver already been initialized.");
            }
            SpelUtil.beanResolver = beanResolver;
        }
    }
    
    /**
     * 解析spel
     *
     * @see SpelUtil#parseSpel(Method, Object[], Class, String)
     */
    @Nullable
    public static <T> T parseSpel(Method method, Object[] argValues, String spel) {
        //noinspection unchecked
        return (T) parseSpel(method, argValues, Object.class, spel);
    }
    
    /**
     * 解析spel
     *
     * @see SpelUtil#parseSpel(Map, Method, Object[], Class, String)
     */
    @Nullable
    public static <T> T parseSpel(Method method, Object[] argValues, Class<T> clazz, String spel) {
        return parseSpel(Collections.emptyMap(), method, argValues, clazz, spel);
    }
    
    /**
     * 解析spel
     *
     * @param appendContext
     *         附加的上下文解析参数
     * @param method
     *         方法
     * @param argValues
     *         方法的参数值
     * @param clazz
     *         解析结果的数据类型
     * @param spel
     *         spel表达式（注：因为本方法中用到了{@link TemplateParserContext}， 所以需要使用#{}将原生的spel包起来，形成最终的表达式）
     *           <br/>&emsp;
     *           1、获取属性示例：#{#param1.fieldA}
     *           <br/>&emsp;
     *           2、调用方法示例：#{#param2.methodA()}
     *           <br/>&emsp;
     *           3、调用spring-bean示例：#{@userService.getUsername(#userId)},
     *           <br/>&emsp;&emsp;&emsp;注：使用之前需要初始化bean解析器{@link SpelUtil#initBeanResolver(BeanResolver)}
     *           <br/>&emsp;&emsp;&emsp;初始化bean解析器示例：SpelUtil.initBeanResolver(new BeanFactoryResolver(applicationContext));
     *           <br/>&emsp;
     *           4、调用静态字段示例：#{T(org.springframework.core.Ordered).HIGHEST_PRECEDENCE}
     *           <br/>&emsp;&emsp;&emsp;注：若调用的是java.lang.包下的类，可以不指定包名
     *           <br/>&emsp;
     *           5、调用静态方法示例：#{T(java.util.Objects).nonNull(#returnObj)}
     *           <br/>&emsp;&emsp;&emsp;注：若调用的是java.lang.包下的类，可以不指定包名
     *           <br/>&emsp;
     *           6、判断示例1：#{#code == 200}
     *           <br/>&emsp;
     *           7、判断示例2：#{#user == null}
     *           <br/>&emsp;
     *           8、......
     *
     * @return 解析结果
     */
    @Nullable
    public static <T> T parseSpel(Map<String, Object> appendContext, Method method, Object[] argValues, Class<T> clazz, String spel) {
        try {
            String[] params = DISCOVERER.getParameterNames(method);
            StandardEvaluationContext context = new StandardEvaluationContext();
            if (beanResolver != null) {
                context.setBeanResolver(beanResolver);
            }
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    context.setVariable(params[i], argValues[i]);
                }
            }
            if (appendContext != null && appendContext.size() > 0) {
                appendContext.forEach(context::setVariable);
            }
            Expression expression = EXPRESSION_PARSER.parseExpression(spel, TEMPLATE_PARSER_CONTEXT);
            return expression.getValue(context, clazz);
        } catch (Exception e) {
            log.warn("method -> {}, argValues -> {}, clazz -> {}, parser -> {}", method, argValues, clazz, spel);
            throw e;
        }
    }
    
    /**
     * 解析spel
     *
     * @see SpelUtil#parseSpel(Map, Class, String)
     */
    @Nullable
    public static <T> T parseSpel(Map<String, Object> argNameValueMap, String spel) {
        //noinspection unchecked
        return (T) parseSpel(argNameValueMap, Object.class, spel);
    }
    
    /**
     * 解析spel
     * <br />
     * 提示：在获取实例字段时，是通过getter去反向解析字段的。如果获取失败，请确保有对应的getter方法
     *
     * @param argNameValueMap
     *         参数名-参数值 map
     * @param clazz
     *         解析结果的数据类型
     * @param spel
     *         spel表达式（注：因为本方法中用到了{@link TemplateParserContext}， 所以需要使用#{}将原生的spel包起来，形成最终的表达式）
     *           <br/>&emsp;
     *           1、获取属性示例：#{#param1.fieldA}
     *           <br/>&emsp;
     *           2、调用方法示例：#{#param2.methodA()}
     *           <br/>&emsp;
     *           3、调用spring-bean示例：#{@userService.getUsername(#userId)},
     *           <br/>&emsp;&emsp;&emsp;注：使用之前需要初始化bean解析器{@link SpelUtil#initBeanResolver(BeanResolver)}
     *           <br/>&emsp;&emsp;&emsp;初始化bean解析器示例：SpelUtil.initBeanResolver(new BeanFactoryResolver(applicationContext));
     *           <br/>&emsp;
     *           4、调用静态字段示例：#{T(org.springframework.core.Ordered).HIGHEST_PRECEDENCE}
     *           <br/>&emsp;&emsp;&emsp;注：若调用的是java.lang.包下的类，可以不指定包名
     *           <br/>&emsp;
     *           5、调用静态方法示例：#{T(java.util.Objects).nonNull(#returnObj)}
     *           <br/>&emsp;&emsp;&emsp;注：若调用的是java.lang.包下的类，可以不指定包名
     *           <br/>&emsp;
     *           6、枚举示例1：{T(com.ideaaedi.demo.enums.CachePrefixEnum).USER_ACCOUNT_PHONE.name()}
     *           <br/>&emsp;&emsp;&emsp;注：和调用静态方法是一样的
     *           <br/>&emsp;
     *           7、枚举示例2：#{T(com.ideaaedi.demo.enums.CachePrefixEnum).USER_ACCOUNT_PHONE.key(#user.account, #user.phone)}
     *           <br/>&emsp;&emsp;&emsp;注：和调用静态方法是一样的
     *           <br/>&emsp;
     *           8、判断示例1：#{#code == 200}
     *           <br/>&emsp;
     *           9、判断示例2：#{#user == null}
     *           <br/>&emsp;
     *           10、......
     *
     * @return 解析结果
     */
    @Nullable
    public static <T> T parseSpel(Map<String, Object> argNameValueMap, Class<T> clazz, String spel) {
        try {
            StandardEvaluationContext context = new StandardEvaluationContext();
            if (beanResolver != null) {
                context.setBeanResolver(beanResolver);
            }
            if (!CollectionUtils.isEmpty(argNameValueMap)) {
                argNameValueMap.forEach(context::setVariable);
            }
            Expression expression = EXPRESSION_PARSER.parseExpression(spel, TEMPLATE_PARSER_CONTEXT);
            return expression.getValue(context, clazz);
        } catch (Exception e) {
            log.warn("argNameValueMap -> {}, clazz -> {}, parser -> {}", argNameValueMap, clazz, spel);
            throw e;
        }
    }
    
}
