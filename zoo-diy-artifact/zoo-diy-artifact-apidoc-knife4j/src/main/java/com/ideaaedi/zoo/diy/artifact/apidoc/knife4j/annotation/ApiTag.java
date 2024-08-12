package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.annotation;

import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ApiTag是{@link Tag}的简化
 *
 * <pre>{@code
 * @ApiTag(name = "demo", order = 100) 的效果类似于
 *
 * @Tag(
 *   name = "demo",
 *   description = "demo",
 *   extensions = @Extension(
 *     properties = @ExtensionProperty(name = "x-order", value = "100", parseValue = true)
 *   )
 * )
 * }
 * </pre>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Documented
@Target({ElementType.TYPE})
@Tag(name = BaseConstant.EMPTY)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiTag {
    
    @AliasFor(annotation = Tag.class)
    String name();
    
    @AliasFor(annotation = Tag.class)
    String description() default "";
    
    @AliasFor(annotation = Tag.class)
    ExternalDocumentation externalDocs() default @ExternalDocumentation;
    
    @AliasFor(annotation = Tag.class)
    Extension[] extensions() default {};
    
    /**
     * 排序
     */
    int order() default 0;
}
