package com.ideaaedi.zoo.diy.feature.fieldperm.api.util;

import cn.hutool.core.util.ClassUtil;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.ArgFieldInfoDTO;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.ArgInfoDTO;
import org.springframework.core.ParameterNameDiscoverer;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public final class FieldPermUtil {
    
    /**
     * 获取方法唯一标识
     *
     * @param method 方法
     *
     * @return 方法唯一标识
     */
    @Nonnull
    public static String buildMethodUid(@Nonnull Method method) {
        StringBuilder sb = new StringBuilder(128);
        Class<?>[] parameterTypes = method.getParameterTypes();
        sb.append(method.getDeclaringClass().getName())
                .append("#")
                .append(method.getName())
                .append("(");
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> argClass = parameterTypes[i];
            String argClassName = argClass.getName();
            String argClassSimpleName = argClass.getSimpleName();
            if (i > 0) {
                sb.append(",");
            }
            // @clnhc即：@ class long name hash code
            sb.append(argClassSimpleName).append("@clnhc").append(argClassName.hashCode());
        }
        sb.append(")");
        return sb.toString();
    }
    
    /**
     * 获取方法的参数的唯一标识
     *
     * @param method 方法
     * @param parameterNameDiscoverer 参数名解析器
     *
     * @return key-方法参数的唯一标识；value-方法参数名 & 参数类型
     */
    @Nonnull
    public static LinkedHashMap<String, ArgInfoDTO> buildArgUid(@Nonnull Method method,
                                                                @Nonnull ParameterNameDiscoverer parameterNameDiscoverer) {
        LinkedHashMap<String, ArgInfoDTO> map = new LinkedHashMap<>(4);
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        if (parameterNames == null || parameterNames.length == 0) {
            return map;
        }
        String methodUid = buildMethodUid(method);
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterNames.length != parameterTypes.length) {
            throw new IllegalStateException("parameterNames.length should equals to parameterTypes.length. curr "
                    + "method -> " + method.getName());
        }
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterNames.length != parameterAnnotations.length) {
            throw new IllegalStateException("parameterNames.length should equals to parameterAnnotations.length. curr "
                    + "method -> " + method.getName());
        }
        for (int i = 0; i < parameterTypes.length; i++) {
            String parameterName = parameterNames[i];
            String argUid = methodUid + "    $" + parameterName;
            map.put(argUid, ArgInfoDTO.builder()
                    .argName(parameterName)
                    .argType(parameterTypes[i])
                    .argUid(argUid)
                    .argAnno(parameterAnnotations[i])
                    .build()
            );
        }
        return map;
    }
    
    /**
     * 获取方法参数里的字段唯一标识
     *
     * @param argUid 方法参数的唯一标识
     * @param argName 方法参数的名称
     * @param argClazz 参数类型
     *
     * @return key-（方法参数里的）字段唯一标识；value-字段
     */
    @Nonnull
    public static LinkedHashMap<String, ArgFieldInfoDTO> buildFieldUid(@Nonnull String argUid, @Nonnull String argName, @Nonnull Class<?> argClazz) {
        LinkedHashMap<String, ArgFieldInfoDTO> map = new LinkedHashMap<>(16);
        if (ClassUtil.isPrimitiveWrapper(argClazz)) {
            return map;
        }
        if (ClassUtil.isEnum(argClazz)) {
            return map;
        }
        if (ClassUtil.isSimpleTypeOrArray(argClazz)) {
            return map;
        }
        if (Iterator.class.isAssignableFrom(argClazz)) {
            return map;
        }
        Field[] fields = argClazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String fieldUid = argUid + "." + fieldName;
            map.put(fieldUid,
                    ArgFieldInfoDTO.builder()
                            .fieldUid(fieldUid)
                            .fieldName(fieldName)
                            .fieldPath(argName + "." + fieldName)
                            .fieldType(field.getType())
                            .fieldAnno(field.getAnnotations())
                            .build()
            );
        }
        return map;
    }
}
