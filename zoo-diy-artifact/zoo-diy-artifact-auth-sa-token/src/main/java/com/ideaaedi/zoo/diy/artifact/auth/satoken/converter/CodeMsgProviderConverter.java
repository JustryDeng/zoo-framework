package com.ideaaedi.zoo.diy.artifact.auth.satoken.converter;

import com.ideaaedi.commonds.constants.SymbolConstant;
import com.ideaaedi.zoo.commonbase.entity.CodeMsgProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * CodeMsgProvider转换器
 *
 * @author JustryDeng
 * @since 1.0.0
 */
public class CodeMsgProviderConverter implements GenericConverter {
    
    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        // String 转换至 CodeMsgProvider
        ConvertiblePair convertiblePair = new ConvertiblePair(String.class, CodeMsgProvider.class);
        Set<ConvertiblePair> convertiblePairSet = new HashSet<>(1);
        convertiblePairSet.add(convertiblePair);
        return convertiblePairSet;
    }
    
    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        String enumClassLongNameAndItem = String.valueOf(source);
        if (StringUtils.isBlank(enumClassLongNameAndItem)) {
            throw new IllegalArgumentException("source cannot be blank. source -> " + source);
        }
        int index = enumClassLongNameAndItem.lastIndexOf(SymbolConstant.POINT);
        if (index <= 0) {
            throw new IllegalArgumentException("source is illegal. is should be '{enum-class-long-name}.{enum-item}'");
        }
        try {
            // enumClassLongName形如：com.ideaaedi.zoo.commonbase.entity.BaseCodeMsgEnum
            String enumClassLongName = enumClassLongNameAndItem.substring(0, index);
            // enumClassLongName形如：SUCCESS
            String enumItem = enumClassLongNameAndItem.substring(index + 1);
            return checkAndGetEnumInstance(enumClassLongName, enumItem);
        } catch (Exception e) {
            throw new IllegalArgumentException("convert source[" + enumClassLongNameAndItem + "] to "
                    + "DesensitizationStrategy Exception!", e);
        }
    }
    
    /**
     * 获取枚举实例
     *
     * @param fullClassName 枚举类 全类名。 如:
     * DefaultDesensitizationStrategyEnum.NAME中，DefaultDesensitizationStrategyEnum的全类名。
     * @param item 具体枚举项。 如: DefaultDesensitizationStrategyEnum.NAME中， NAME就对应item
     *
     * @return 枚举实例
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public CodeMsgProvider checkAndGetEnumInstance(String fullClassName, String item) throws ClassNotFoundException {
        if (StringUtils.isAnyBlank(fullClassName, item)) {
            throw new IllegalArgumentException("enum-class-long-name [" + fullClassName + "] and item[" + item + "] "
                    + "must not be blank.");
        }
        Class<?> clazz = this.getClass().getClassLoader().loadClass(fullClassName);
        Assert.isTrue(clazz.isEnum(), "clazz[" + clazz + "] is not Enum.");
        Class<Enum> enumClazz = (Class<Enum>) clazz;
        Enum enumObj = Enum.valueOf(enumClazz, item);
        if (enumObj instanceof CodeMsgProvider) {
            return (CodeMsgProvider) enumObj;
        }
        throw new IllegalArgumentException("enum[" + enumClazz + "] must be implement CodeMsgProvider.");
    }
}
