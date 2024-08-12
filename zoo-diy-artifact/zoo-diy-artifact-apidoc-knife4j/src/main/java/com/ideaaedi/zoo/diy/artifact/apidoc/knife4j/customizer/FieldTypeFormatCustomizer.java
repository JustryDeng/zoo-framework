package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.customizer;

import com.fasterxml.jackson.databind.type.SimpleType;
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.properties.ZooKnife4jProperties;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.oas.models.media.Schema;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.PropertyCustomizer;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 字段类型、字段格式的自定义
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class FieldTypeFormatCustomizer implements PropertyCustomizer {
    
    @Resource
    private ZooKnife4jProperties zooKnife4jProperties;
    
    @Override
    public Schema customize(Schema property, AnnotatedType atType) {
        Type type = atType.getType();
        if (!(type instanceof SimpleType simpleType)) {
            return property;
        }
        List<ZooKnife4jProperties.FileTypeMapping> fileTypeMappingList = Optional
                .ofNullable(zooKnife4jProperties.getFileTypeMappings())
                .orElse(Collections.emptyList());
        if (CollectionUtils.isEmpty(fileTypeMappingList)) {
            return property;
        }
        Map<String, ZooKnife4jProperties.FileTypeMapping> fileTypeMap = fileTypeMappingList.stream().collect(
                Collectors.toMap(
                        ZooKnife4jProperties.FileTypeMapping::getType,
                        Function.identity(),
                        (pre, next) -> next
                )
        );
        String className = Optional.ofNullable(simpleType.getRawClass())
                .map(Class::getName)
                .orElse(null);

        ZooKnife4jProperties.FileTypeMapping fileTypeMapping = fileTypeMap.get(className);
        if (fileTypeMapping == null) {
            return property;
        }
        property.setType(fileTypeMapping.getTargetType());
        String format = fileTypeMapping.getFormat();
        format = StringUtils.defaultIfBlank(format, null);
        property.setFormat(format);
        property.set$ref(null); // 去掉可能存在的字段类型对象的引用
        return property;
    }
    
}
