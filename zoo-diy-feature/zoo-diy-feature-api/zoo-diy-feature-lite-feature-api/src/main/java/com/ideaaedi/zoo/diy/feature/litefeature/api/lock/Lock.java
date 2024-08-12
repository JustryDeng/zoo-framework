package com.ideaaedi.zoo.diy.feature.litefeature.api.lock;

import com.ideaaedi.commonds.lock.NotAcquiredLockException;
import com.ideaaedi.zoo.commonbase.zoo_util.SpelUtil;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.common.TemplateParserContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Lock {
    
    /**
     * 锁 key （支持spel）
     * <pre>
     *  spel表达式（注：因为本方法中用到了{@link TemplateParserContext}， 所以需要使用#{}将原生的spel包起来，形成最终的表达式）
     *  1、获取属性示例：{@code #{#param1.fieldA} }
     *  2、调用方法示例：{@code #{#param2.methodA()} }
     *  3、调用spring-bean示例：{@code #{@userService.getUsername(#userId)}},
     *     注：使用之前需要初始化bean解析器{@link SpelUtil#initBeanResolver(BeanResolver)}
     *        初始化bean解析器示例：{@code SpelUtil.initBeanResolver(new BeanFactoryResolver(applicationContext)); }
     *  4、调用静态字段示例：{@code #{T(org.springframework.core.Ordered).HIGHEST_PRECEDENCE} }
     *     注：若调用的是java.lang.包下的类，可以不指定包名
     *  5、调用静态方法示例：{@code #{T(java.util.Objects).nonNull(#returnObj)} }
     *     注：若调用的是java.lang.包下的类，可以不指定包名
     *  6、枚举示例1：{@code {T(com.ideaaedi.demo.enums.CachePrefixEnum).USER_ACCOUNT_PHONE.name()} }
     *     注：和调用静态方法是一样的
     *  7、枚举示例2：{@code #{T(com.ideaaedi.demo.enums.CachePrefixEnum).USER_ACCOUNT_PHONE.key(#user.account, #user.phone)} }
     *     注：和调用静态方法是一样的
     *  8、判断示例1：{@code #{#code == 200} }
     *  9、判断示例2：{@code #{#user == null} }
     *  10、......
     *
     *  更多spel语法见{@link SpelUtil}
     * </pre>
     */
    String lockKey();
    
    /**
     * 分布式锁提供器id（为空则使用默认的提供器）
     */
    String lockSupplierId() default "";
    
    /**
     * 锁等待时长 （取值范围：waitTime >= 0）
     *  <br />0：表示不等待，没获取到将直接抛出{@link NotAcquiredLockException}
     */
    long waitTime() default LockSupplier.DEFAULT_WAIT_TIME;
    
    /**
     * 持有锁的最大时长 （取值范围：leaseTime > 0 || leaseTime == -1）
     * <br />-1：表示自动续期
     */
    long leaseTime() default LockSupplier.DEFAULT_LEASE_TIME;
    
    /**
     * {@link #waitTime()}和{@link #leaseTime()}的时间单位
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
