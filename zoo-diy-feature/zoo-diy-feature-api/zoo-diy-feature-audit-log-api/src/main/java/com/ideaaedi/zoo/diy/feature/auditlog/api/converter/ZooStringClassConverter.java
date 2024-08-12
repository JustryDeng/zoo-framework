package com.ideaaedi.zoo.diy.feature.auditlog.api.converter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.HashSet;
import java.util.Set;

/**
 * string 转换为 class
 *
 * @author JustryDeng
 * @since 1.0.0
 */
public class ZooStringClassConverter implements GenericConverter {
    
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        // String 转换至 Class
        ConvertiblePair convertiblePair = new ConvertiblePair(String.class, Class.class);
        Set<ConvertiblePair> convertiblePairSet = new HashSet<>(1);
        convertiblePairSet.add(convertiblePair);
        return convertiblePairSet;
    }
    
    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String classLongName = String.valueOf(source);
        if (StringUtils.isBlank(classLongName)) {
            throw new IllegalArgumentException("source cannot be blank. source -> " + source);
        }
        try {
            return Class.forName(classLongName + ".class");
        } catch (ClassNotFoundException e) {
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    
}
