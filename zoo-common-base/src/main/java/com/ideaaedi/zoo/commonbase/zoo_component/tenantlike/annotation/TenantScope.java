package com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.annotation;

import com.ideaaedi.zoo.commonbase.constant.BaseConstant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 租户和数据范围注解
 * <p>
 * 支持指定 租户、可读数据范围、可写数据范围
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface TenantScope {
    
    /**
     * 数据（增删改查）过滤范围
     * <p>
     * 支持spel. spel示例："#{#sys.username}"）
     */
    String value() default BaseConstant.EMPTY;
    
    /**
     * 插入（insert）时的租户值（一般为：用户所属租户）
     * <br />
     * 注：优先级高于value
     * <p>
     * 支持spel. spel示例："#{#sys.username}"）
     */
    String insertTenant() default BaseConstant.EMPTY;
    
    /**
     * 可读（select）租户范围
     * <br />
     * 注：优先级高于value
     * <p>
     * 支持spel. spel示例："#{#sys.username}"）
     */
    String[] readableTenants() default {};
    
    /**
     * 可写（update、delete）租户范围
     * <br />
     * 注：优先级高于value
     * <p>
     * 支持spel. spel示例："#{#sys.username}"）
     * </P>
     */
    String[] modifiableTenants() default {};
}
