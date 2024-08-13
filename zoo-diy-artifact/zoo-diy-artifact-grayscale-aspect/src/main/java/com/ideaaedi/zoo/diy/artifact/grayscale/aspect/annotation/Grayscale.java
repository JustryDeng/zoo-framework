package com.ideaaedi.zoo.diy.artifact.grayscale.aspect.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 灰度注解
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Inherited
@Documented
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Grayscale {
    
    /**
     * 灰度方法
     *
     * <pre>
     * 注：此方法上如果有相关aop的话，aop不会生效
     * </pre>
     *
     * <pre>
     * {@code
     * // 假设业务方法是：
     * @Grayscale(gray="grayAbc", condition="conditionXyz")
     * public String userInfo(PojoA arg0, PojoB arg1, PojoC arg2) {
     * ...
     * }
     *
     * // 那么灰度方法应该同参数、同返回类型（或是原方法返回类型的子类型），如：
     * public String grayAbc(PojoA arg0, PojoB arg1, PojoC arg2) {
     * ...
     * }
     * </pre>
     */
    String gray();
    
    /**
     * 条件condition方法，应返回boolean
     * <pre>
     * 注：当condition返回true && 灰度方法存在时，才会调用灰度方法
     * 注：此方法上如果有相关aop的话，aop不会生效
     * </pre>
     *
     * <pre>
     * {@code
     * // 假设业务方法是：
     * @Grayscale(gray="grayAbc", condition="conditionXyz")
     * public String userInfo(PojoA arg0, PojoB arg1, PojoC arg2) {
     * ...
     * }
     *
     * // 那么condition方法应该同参数、同返回类型、返回值类型为boolean，如：
     * public boolean conditionXyz(PojoA arg0, PojoB arg1, PojoC arg2) {
     * ...
     * }
     * </pre>
     */
    String condition();
}
