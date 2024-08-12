package com.ideaaedi.zoo.diy.feature.fieldperm.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;

/**
 * 方法参数里的字段信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArgFieldInfoDTO {
    
    /**
     * 字段唯一标识
     */
    @Nonnull
    private String fieldUid;
    
    /**
     * 字段名称
     */
    @Nonnull
    private String fieldName;
    
    /**
     * 字段路径
     */
    @Nonnull
    private String fieldPath;
    
    /**
     * 字段类型
     */
    @Nonnull
    private Class<?> fieldType;
    
    /**
     * 字段上的注解
     */
    @Nullable
    private Annotation[] fieldAnno;
}