package com.ideaaedi.zoo.diy.feature.fieldperm.impl.openapi.core;

import com.ideaaedi.commonds.tuple.Holder;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.ArgFieldInfoDTO;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.ArgInfoDTO;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.MethodArgFieldInfo;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.enums.MethodArgFieldEnum;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.spi.MethodArgFieldParser;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.util.FieldPermUtil;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class OpenapiMethodArgFieldParser implements MethodArgFieldParser {
    
    private ParameterNameDiscoverer parameterNameDiscoverer;
    
    public OpenapiMethodArgFieldParser(ParameterNameDiscoverer parameterNameDiscoverer) {
        this.parameterNameDiscoverer = parameterNameDiscoverer;
    }
    
    @PostConstruct
    private void init() {
        if (parameterNameDiscoverer == null) {
            parameterNameDiscoverer = new StandardReflectionParameterNameDiscoverer();
        }
    }
    
    @Nonnull
    @Override
    public List<MethodArgFieldInfo> parse(@Nonnull Method method, @Nonnull Class<?> clazz) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return Collections.emptyList();
        }
        List<MethodArgFieldInfo> list = new ArrayList<>(128);
        String methodUid = FieldPermUtil.buildMethodUid(method);
        LinkedHashMap<String, ArgInfoDTO> argUidAndArgInfoMap = FieldPermUtil.buildArgUid(method,
                parameterNameDiscoverer);
        Annotation[] methodAnnotations = method.getAnnotations();
    
        Holder<Integer> argIndexHolder = Holder.of(0);
        argUidAndArgInfoMap.forEach((argUid, argInfo) -> {
            Integer argIndex = argIndexHolder.getValue();
            argIndexHolder.setValue(argIndex + 1);
            Annotation[] argAnnotations = argInfo.getArgAnno();
            Class<?> argType = argInfo.getArgType();
            Annotation[] argClazzAnnotations = argType.getAnnotations();
    
            // 方法上的参数
            Parameter parameter = findParameterFromArg(argAnnotations);
            String argName = argInfo.getArgName();
            if (parameter == null) {
                parameter = findParameterFromMethod(methodAnnotations, argName);
            }
            String fieldDesc = null;
            boolean ifVisible = true;
            if (parameter != null) {
                fieldDesc = parameter.description();
                ifVisible = !parameter.hidden();
            } else {
                Schema schema = findSchemaFromArgType(argClazzAnnotations);
                if (schema != null) {
                    fieldDesc = schema.description();
                }
            }
            list.add(
                    MethodArgFieldInfo.builder()
                            .argOrFieldUid(argUid)
                            .methodUid(methodUid)
                            .type(MethodArgFieldEnum.REQ)
                            .level(1)
                            .path(argName)
                            .fieldName(argName)
                            .fieldType(argType.getName())
                            .fieldDesc(fieldDesc)
                            .ifVisible(ifVisible ? 1 : 0)
                            .argIndex(argIndex)
                            .build()
            );
    
    
            // 参数中的字段
            LinkedHashMap<String, ArgFieldInfoDTO> fieldUidAndFieldInfoMap = FieldPermUtil.buildFieldUid(argUid, argName, argType);
            fieldUidAndFieldInfoMap.forEach((fieldUid, fieldInfo) -> {
                Schema schema = findSchemaFromField(fieldInfo.getFieldAnno());
                String fieldName = fieldInfo.getFieldName();
                list.add(
                        MethodArgFieldInfo.builder()
                                .argOrFieldUid(fieldUid)
                                .methodUid(methodUid)
                                .type(MethodArgFieldEnum.REQ)
                                .level(2)
                                .path(argName + "." + fieldName)
                                .fieldName(fieldName)
                                .fieldType(fieldInfo.getFieldType().getName())
                                .fieldDesc(schema == null ? null : schema.description())
                                .ifVisible(schema == null ? 1 : (schema.hidden() ? 0 : 1))
                                .argIndex(argIndex)
                                .build()
                );
            });
        });
        return list;
    }
    
    
    
    /**
     * 从方法参数前获取Parameter注解
     *
     * @param annotations 参数前的所有注解
     */
    @Nullable
    private Parameter findParameterFromArg(@Nullable Annotation[] annotations) {
        if (annotations == null) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof Parameter parameter) {
                return parameter;
            }
        }
        return null;
    }
    
    /**
     * 从方法上获取Parameter注解
     *
     * @param annotations 方法上的所有注解
     * @param matchName 要查找的参数名
     */
    @Nullable
    private Parameter findParameterFromMethod(@Nullable Annotation[] annotations, @Nullable String matchName) {
        if (StringUtils.isBlank(matchName)) {
            return null;
        }
        if (annotations == null) {
            return null;
        }
        Parameters parameters = null;
        for (Annotation annotation : annotations) {
            if (annotation instanceof Parameter p) {
                if (matchName.equals(p.name())) {
                    return p;
                }
            }
            if (annotation instanceof Parameters p) {
                parameters = p;
                break;
            }
        }
        if (parameters == null) {
            return null;
        }
        Parameter[] parameterArr = parameters.value();
        if (parameterArr == null) {
            return null;
        }
        for (Parameter parameter : parameterArr) {
            if (matchName.equals(parameter.name())) {
                return parameter;
            }
        }
        return null;
    }
    
    /**
     * 从参数类型上获取Schema注解
     *
     * @param annotations 参数类型上的所有注解
     */
    @Nullable
    private Schema findSchemaFromArgType(@Nullable Annotation[] annotations) {
        if (annotations == null) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof Schema schema) {
                return schema;
            }
        }
        return null;
    }
    
    /**
     * 从字段上获取Schema注解
     *
     * @param annotations 字段上的所有注解
     */
    @Nullable
    private Schema findSchemaFromField(@Nullable Annotation[] annotations) {
        if (annotations == null) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation instanceof Schema schema) {
                return schema;
            }
        }
        return null;
    }
}
