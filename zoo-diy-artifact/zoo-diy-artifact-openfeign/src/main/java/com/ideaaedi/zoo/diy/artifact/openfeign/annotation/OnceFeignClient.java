package com.ideaaedi.zoo.diy.artifact.openfeign.annotation;

import com.ideaaedi.commonds.function.NoArgFunction;
import com.ideaaedi.zoo.diy.artifact.openfeign.constant.OpenfeignConstant;
import com.ideaaedi.zoo.diy.artifact.openfeign.holder.OnceFeignProvider;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 线程级Feign客户端，支持动态指定url前缀
 * <pre>
 *  指定url前缀的方式：
 *  1. 使用时直接通过{@link OnceFeignClient#url()}指定
 *  2. 运行时通过{@link OnceFeignProvider#exec(NoArgFunction, String)}等方法指定
 * </pre>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Inherited
@Documented
@FeignClient
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OnceFeignClient {
    
    /**
     * 因为是直接指定url的http请求，所以不需要服务名称（这里弄一个不存在的服务）
     */
    @AliasFor(annotation = FeignClient.class)
    String value() default OpenfeignConstant.ONCE_FEIGN_DEFAULT_NAME;
    
    @AliasFor(annotation = FeignClient.class)
    String contextId() default "";
    
    /**
     * 因为是直接指定url的http请求，所以不需要服务名称（这里弄一个不存在的服务）
     */
    @AliasFor(annotation = FeignClient.class)
    String name() default OpenfeignConstant.ONCE_FEIGN_DEFAULT_NAME;
    
    @AliasFor(annotation = FeignClient.class)
    String[] qualifiers() default {};
    
    /**
     * 调用前请使用{@link OnceFeignProvider}提供url，如：
     * <pre>
     *  {@code
     *  String result = OnceFeignProvider.exec(() -> demo2FeignRpc.welcome(), "http://127.0.0.1:8080");
     *  }
     * </pre>
     * 如果不指定，那么将使用默认的url值
     */
    @AliasFor(annotation = FeignClient.class)
    String url() default OpenfeignConstant.ONCE_FEIGN_DEFAULT_URL;
    
    @AliasFor(annotation = FeignClient.class)
    boolean dismiss404() default false;
    
    @AliasFor(annotation = FeignClient.class)
    Class<?>[] configuration() default {};
    
    @AliasFor(annotation = FeignClient.class)
    Class<?> fallback() default void.class;
    
    @AliasFor(annotation = FeignClient.class)
    Class<?> fallbackFactory() default void.class;
    
    @AliasFor(annotation = FeignClient.class)
    String path() default "";
    
    @AliasFor(annotation = FeignClient.class)
    boolean primary() default true;
}