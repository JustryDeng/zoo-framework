package com.ideaaedi.zoo.diy.feature.fieldperm.api.advice;

import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.MethodArgFieldInfo;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.exception.FieldPermException;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.spi.MethodArgFieldRepositoryService;
import com.ideaaedi.zoo.diy.feature.fieldperm.api.util.FieldPermUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 校验字段权限
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class FieldPermValidator  implements ApplicationListener<ContextRefreshedEvent>, MethodInterceptor, Ordered {
    
    private final MethodArgFieldRepositoryService repositoryService;
    
    private final FieldPermOrderProvider fieldPermOrderProvider;
    
    private final List<MethodArgFieldInfo> methodArgFieldInfoList = new ArrayList<>(128);
    
    private final Map<String, Field> fieldUidAndFieldCache = new ConcurrentHashMap<>(1024);
    
    public FieldPermValidator(MethodArgFieldRepositoryService repositoryService, FieldPermOrderProvider fieldPermOrderProvider) {
        this.repositoryService = repositoryService;
        this.fieldPermOrderProvider = fieldPermOrderProvider;
    }
    
    @Nullable
    @Override
    public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return invocation.proceed();
        }
        String methodUid = FieldPermUtil.buildMethodUid(method);
        Pair<Boolean, List<MethodArgFieldInfo>> pair = repositoryService.ifPermAndNotAllowedList(methodUid);
        Boolean ifPerm = pair.getLeft();
        if (!BooleanUtils.isTrue(ifPerm)) {
            return invocation.proceed();
        }
        List<MethodArgFieldInfo> notAllowedList = pair.getRight();
        if (CollectionUtils.isEmpty(notAllowedList)) {
            return invocation.proceed();
        }
        validate(invocation, notAllowedList);
        return invocation.proceed();
    }
    
    @Override
    public int getOrder() {
        if (fieldPermOrderProvider != null) {
            return fieldPermOrderProvider.getOrder();
        }
        return AutoConfigurationConstant.ZOO_FIELD_PERM_VALIDATOR_ORDER;
    }

    
    /**
     * 添加方法参数字段信息
     *
     * @param methodArgFieldInfoList 方法参数字段信息
     */
    public void addArgFieldInfos(@Nonnull List<MethodArgFieldInfo> methodArgFieldInfoList) {
        this.methodArgFieldInfoList.addAll(methodArgFieldInfoList);
    }
    
    
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        repositoryService.initSavaOrUpdate(methodArgFieldInfoList);
    }
    
    /**
     * 字段权限校验
     *
     * @param invocation 调用信息
     * @param notAllowedList 不允许有值的方法参数（或方法参数的字段）信息
     */
    protected void validate(MethodInvocation invocation, List<MethodArgFieldInfo> notAllowedList) throws IllegalAccessException {
        List<MethodArgFieldInfo> argList = notAllowedList.stream()
                .filter(x -> Objects.equals(1, x.getLevel())).toList();
        Object[] argValues = invocation.getArguments();
        
        // 校验方法参数必须为null
        Set<String> notAllowedArgUidSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(argList)) {
            for (MethodArgFieldInfo methodArgFieldInfo : argList) {
                Integer argIndex = methodArgFieldInfo.getArgIndex();
                Object argVal = argValues[argIndex];
                if (argVal != null) {
                    throw new FieldPermException(
                            String.format("arg permission check not passed. '%s' should be null.", methodArgFieldInfo.getFieldName()),
                            methodArgFieldInfo
                    );
                }
                notAllowedArgUidSet.add(methodArgFieldInfo.getArgOrFieldUid());
            }
        }
        
        // 校验方法参数中的字段必须为null
        List<MethodArgFieldInfo> fieldList = notAllowedList.stream()
                // 这里只校验2级（即：这里只校验arg里面的field，不校验field下面的field）
                .filter(x -> Objects.equals(2, x.getLevel()))
                // 如果arg已经校验了（即：arg==null了），那么arg里面的field就不需要校验了
                .filter(x -> {
                    if (CollectionUtils.isEmpty(notAllowedArgUidSet)) {
                        return true;
                    }
                    boolean parentHasCheck = notAllowedArgUidSet.stream().anyMatch(argUid -> x.getArgOrFieldUid().startsWith(argUid));
                    return !parentHasCheck;
                })
                .toList();
        if (!CollectionUtils.isEmpty(fieldList)) {
            for (MethodArgFieldInfo methodArgFieldInfo : fieldList) {
                Integer argIndex = methodArgFieldInfo.getArgIndex();
                Object argVal = argValues[argIndex];
                if (argVal == null) {
                    continue;
                }
                String argOrFieldUid = methodArgFieldInfo.getArgOrFieldUid();
                
                Field field = fieldUidAndFieldCache.get(argOrFieldUid);
                if (field == null) {
                    field = FieldUtils.getDeclaredField(argVal.getClass(), methodArgFieldInfo.getFieldName(), true);
                    fieldUidAndFieldCache.putIfAbsent(argOrFieldUid,field);
                }
                
                Object fieldVal = field.get(argVal);
                if (fieldVal != null) {
                    throw new FieldPermException(
                            String.format("field permission check not passed. '%s' should be null.", methodArgFieldInfo.getFieldName()),
                            methodArgFieldInfo
                    );
                }
                notAllowedArgUidSet.add(methodArgFieldInfo.getArgOrFieldUid());
            }
        }
    }
    
    public interface FieldPermOrderProvider extends Ordered {
    
    }
}
