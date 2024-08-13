package com.ideaaedi.zoo.diy.feature.fieldperm.api.spi;

import com.ideaaedi.zoo.diy.feature.fieldperm.api.annotation.FieldPermission;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.MethodArgFieldInfo;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Nonnull;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 方法参数字段解析器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2100.7.3
 */
public interface MethodArgFieldParser {
    
    /**
     * 是否支持解析
     *
     * @param method 方法
     * @param clazz 方法所属类
     *
     * @return 是否支持解析
     */
    default boolean support(@Nonnull Method method, @Nonnull Class<?> clazz) {
        FieldPermission fieldPermission = AnnotatedElementUtils.findMergedAnnotation(method, FieldPermission.class);
        if (fieldPermission == null) {
            return false;
        }
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
        if (requestMapping == null) {
            return false;
        }
        Controller controller = AnnotatedElementUtils.findMergedAnnotation(clazz, Controller.class);
        return controller != null;
    }
    
    /**
     * 解析方法的参数字段信息
     *
     * @param method 方法
     * @param clazz 方法所属类
     *
     * @return 方法的参数字段信息
     */
    @Nonnull
    List<MethodArgFieldInfo> parse(@Nonnull Method method, @Nonnull Class<?> clazz);
}
