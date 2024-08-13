package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.customizer;

import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.SimpleType;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.ParameterCustomizer;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 枚举说明处理器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class EnumDescCustomizer implements PropertyCustomizer, ParameterCustomizer {
    
    @Override
    public Schema customize(Schema property, AnnotatedType atType) {
        Class<Enum<?>> enumClass = null;
        try {
            enumClass = determineEnumClass(atType);
        } catch (Exception e) {
            log.warn("determineEnumClass fail. e.getMessage() -> {}.", e.getMessage());
        }
        if (enumClass == null) {
            return property;
        }
        
        // 获取格式化后的枚举说明
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        List<String> enumDescList =
                Arrays.stream(enumConstants).map(EnumDescCustomizer::obtainEnumDescription).toList();
        String enumItemDesc = buildHtmlUnOrderList(enumDescList);
        // 赋值
        String description = property.getDescription();
        property.setDescription(StringUtils.defaultIfBlank(description, BaseConstant.EMPTY) + enumItemDesc);
        property.setEnum(null); // 通过这里去掉枚举后面的 ",可用值:xxx"
        return property;
    }
    
    
    @Override
    public Parameter customize(Parameter parameter, MethodParameter methodParameter) {
        Class<Enum<?>> enumClass = null;
        try {
            enumClass = determineEnumClass(methodParameter);
        } catch (Exception e) {
            log.warn("determineEnumClass fail. e.getMessage() -> {}.", e.getMessage());
        }
        if (enumClass == null) {
            return parameter;
        }
        // 获取格式化后的枚举说明
        Enum<?>[] enumConstants = enumClass.getEnumConstants();
        List<String> enumDescList =
                Arrays.stream(enumConstants).map(EnumDescCustomizer::obtainEnumDescription).toList();
        String enumItemDesc = buildHtmlUnOrderList(enumDescList);
        
        // 赋值
        String description = parameter.getDescription();
        parameter.setDescription(StringUtils.defaultIfBlank(description, BaseConstant.EMPTY) + enumItemDesc);
        Schema schema = parameter.getSchema();
        if (schema != null) {
            schema.setEnum(null); // 通过这里去掉枚举后面的 ",可用值:xxx"
        }
        return parameter;
    }
    
    /**
     * 获取对应的枚举类
     *
     * @return 枚举类
     */
    @Nullable
    private static Class<Enum<?>> determineEnumClass(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        Class<Enum<?>> enumClass = null;
        if (parameterType.isEnum()) {
            //noinspection unchecked
            enumClass = (Class<Enum<?>>) parameterType;
        } else if (parameterType.isArray()) {
            Class<?> componentType = parameterType.componentType();
            if (componentType != null && componentType.isEnum()) {
                //noinspection unchecked
                enumClass = (Class<Enum<?>>) componentType;
            }
        } else if (Collection.class.isAssignableFrom(parameterType)) {
            if (methodParameter.getGenericParameterType() instanceof ParameterizedType  parameterizedType) {
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                    Type actualTypeArgument = actualTypeArguments[0];
                    try {
                        Class<?> targetClazz = Class.forName(actualTypeArgument.getTypeName());
                        if (targetClazz.isEnum()) {
                            //noinspection unchecked
                            enumClass = (Class<Enum<?>>) targetClazz;
                        }
                    } catch (ClassNotFoundException ignore) {
                        //
                    }
                }
            }
            Class<?> componentType = parameterType.componentType();
            if (componentType != null && componentType.isEnum()) {
                //noinspection unchecked
                enumClass = (Class<Enum<?>>) componentType;
            }
        }
        return enumClass;
    }
    
    /**
     * 获取对应的枚举类
     *
     * @return 枚举类
     */
    @Nullable
    private static Class<Enum<?>> determineEnumClass(AnnotatedType atType) {
        Type type = atType.getType();
        Class<?> rawClass = null;
        if (type instanceof SimpleType e) {
            rawClass = e.getRawClass();
        } else if (type instanceof CollectionType e) {
            rawClass = e.getContentType().getRawClass();
        } else if (type instanceof ArrayType e) {
            rawClass = e.getContentType().getRawClass();
        }
        if (rawClass == null) {
            return null;
        }
        if (Enum.class.isAssignableFrom(rawClass)) {
            //noinspection unchecked
            return (Class<Enum<?>>) rawClass;
        }
        return null;
    }
    
    /**
     * 获取枚举描述
     *
     * @param enumObj 枚举对象
     *
     * @return 枚举描述
     */
    private static String obtainEnumDescription(@Nonnull Enum<?> enumObj) {
        String name = enumObj.name();
        /*
         * 枚举说明器示例:
         *
         * public interface EnumDescriptor {
         *     // 获取枚举项说明
         *     String obtainDescription();
         * }
         */
        if (enumObj instanceof EnumDescriptor) {
            return name + "：" + ((EnumDescriptor) enumObj).obtainDescription();
        }
        return name;
    }
    
    /**
     * 构建无序列表html
     *
     * @param itemList 列表元素
     *
     * @return 无序列表html
     */
    private static String buildHtmlUnOrderList(@Nullable List<String> itemList) {
        if (CollectionUtils.isEmpty(itemList)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for (String item : itemList) {
            sb.append("<li>");
            sb.append(item);
            sb.append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }
}
