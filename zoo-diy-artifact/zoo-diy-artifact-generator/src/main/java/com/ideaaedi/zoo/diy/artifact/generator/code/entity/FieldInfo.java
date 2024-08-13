package com.ideaaedi.zoo.diy.artifact.generator.code.entity;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class FieldInfo {
    
    /**
     * 注释
     */
    private String comment;
    
    /**
     * 名称
     */
    private String name;
    
    /**
     * 字段的类型
     * <pre>
     * {@code
     *  如：
     *    String
     *    List<String>
     * }
     * </pre>
     */
    private String type;
    
    /**
     * 要import的全类名
     */
    private List<String> importClasses;
    
    /**
     * (non-javadoc)
     *
     * @param propertyName 字段名
     * @param propertyComment 字段注释
     * @param propertyType 字段类型
     */
    public static FieldInfo of(String propertyName, String propertyComment, Class<?> propertyType) {
        FieldInfo info = new FieldInfo();
        info.setComment(propertyComment);
        info.setName(propertyName);
        info.setType(propertyType.getSimpleName());
        info.setImportClasses(
                Lists.newArrayList(propertyType.getName())
        );
        return info;
    }
    
    /**
     * (non-javadoc)
     *
     * @param propertyName 字段名
     * @param propertyComment 字段注释
     * @param propertyType 字段类型
     * @param importClasses 要导的包
     */
    public static FieldInfo of(String propertyName, String propertyComment, String propertyType,
                               String... importClasses) {
        FieldInfo info = new FieldInfo();
        info.setComment(propertyComment);
        info.setName(propertyName);
        info.setType(propertyType);
        if (importClasses != null) {
            info.setImportClasses(
                    Arrays.stream(importClasses).toList()
            );
        }
        return info;
    }
}
