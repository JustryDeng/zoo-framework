package com.ideaaedi.zoo.diy.artifact.grayscale.aspect.advice;

import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.diy.artifact.grayscale.aspect.annotation.Grayscale;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 灰度切面
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@Aspect
@EnableAspectJAutoProxy(exposeProxy = true)
public class GrayscaleAspect implements Ordered {
    
    /**
     * key-方法唯一key
     * pair-left -  方法是否存在
     * pair-right - 灰度方法
     */
    private static final Map<String, Pair<Boolean, Method>> GRAY_METHOD_CACHE = new ConcurrentHashMap<>(64);
    
    /**
     * key-方法唯一key
     * pair-left -  方法是否存在
     * pair-right - 条件方法
     */
    private static final Map<String, Pair<Boolean, Method>> CONDITION_METHOD_CACHE = new ConcurrentHashMap<>(64);
    
    private final GrayscaleAspectOrderProvider grayscaleAspectOrderProvider;
    
    public GrayscaleAspect(GrayscaleAspectOrderProvider grayscaleAspectOrderProvider) {
        this.grayscaleAspectOrderProvider = grayscaleAspectOrderProvider;
    }
    
    @Around("@annotation(grayscale)")
    public Object grayAround(ProceedingJoinPoint pjp, Grayscale grayscale) throws Throwable {
        String condition = grayscale.condition();
        String gray = grayscale.gray();
        Method grayMethod = extractGrayMethod(pjp, gray);
        Method conditonMethod = extractConditionMethod(pjp, condition);
        if (grayMethod == null || conditonMethod == null) {
            return pjp.proceed();
        }
        Object conditionResult = invoke(pjp, conditonMethod);
        if (conditionResult instanceof Boolean && (Boolean)conditionResult) {
            log.info("Hit gray method -> {}", grayMethod.toGenericString());
            return invoke(pjp, grayMethod);
        }
        return pjp.proceed();
    }
    
    @Override
    public int getOrder() {
        if (grayscaleAspectOrderProvider != null) {
            return grayscaleAspectOrderProvider.getOrder();
        }
        return AutoConfigurationConstant.ZOO_GRAYSCALE_ASPECT_ORDER;
    }
    
    /**
     * 调用指定方法
     */
    private Object invoke(ProceedingJoinPoint pjp, Method method) throws Throwable {
        Object[] args = pjp.getArgs();
        //noinspection deprecation
        if (!method.isAccessible()) {
            makeAccessible(method);
        }
        if (isStatic(method)) {
            return method.invoke(null, args);
        }
        return method.invoke(pjp.getTarget(), args);
    }
    
    /**
     * 抽取灰度方法
     */
    @Nullable
    private Method extractGrayMethod(ProceedingJoinPoint pjp, String grayMethod) {
        if (StringUtils.isBlank(grayMethod)) {
            return null;
        }
        Class<?> clazz = pjp.getTarget().getClass();
        String key = methodUk(clazz, grayMethod);
        Pair<Boolean, Method> pair = GRAY_METHOD_CACHE.get(key);
        if (pair == null) {
            Method method = resolveSameParamTypeAndReturnTypeMethod(pjp, grayMethod, clazz);
            pair = Pair.of(method != null, method);
            GRAY_METHOD_CACHE.put(key, pair);
        }
        Boolean exist = pair.getLeft();
        if (!exist) {
            return null;
        }
        return pair.getRight();
    }
    
    /**
     * 抽取条件方法
     */
    @Nullable
    private Method extractConditionMethod(ProceedingJoinPoint pjp, String grayMethod) {
        if (StringUtils.isBlank(grayMethod)) {
            return null;
        }
        Class<?> clazz = pjp.getTarget().getClass();
        String key = methodUk(clazz, grayMethod);
        Pair<Boolean, Method> pair = CONDITION_METHOD_CACHE.get(key);
        if (pair == null) {
            Method method = resolveSameParamTypeAndReturnBooleanMethod(pjp, grayMethod, clazz);
            pair = Pair.of(method != null, method);
            CONDITION_METHOD_CACHE.put(key, pair);
        }
        Boolean exist = pair.getLeft();
        if (!exist) {
            return null;
        }
        return pair.getRight();
    }
    
    /**
     * 获取同参数类型、同返回值类型的指定方法
     *
     * @param pjp 要获取的方法需要与 pjp正执行的方法 同参数类型、同返回值类型
     * @param clazz 从哪里获取
     * @param methodName 要获取的方法名
     *
     * @return 获取到的方法
     */
    @Nullable
    private Method resolveSameParamTypeAndReturnTypeMethod(ProceedingJoinPoint pjp, String methodName, Class<?> clazz) {
        Method originMethod = resolveTargetMethod(pjp);
        return findMethod(clazz, methodName, originMethod.getReturnType(), originMethod.getParameterTypes());
    }
    
    /**
     * 获取同参数类型&返回值类型为boolean的指定方法
     *
     * @param pjp 要获取的方法需要与 pjp正执行的方法 同参数类型
     * @param clazz 从哪里获取
     * @param methodName 要获取的方法名
     *
     * @return 获取到的方法
     */
    @Nullable
    private Method resolveSameParamTypeAndReturnBooleanMethod(ProceedingJoinPoint pjp, String methodName, Class<?> clazz) {
        Method originMethod = resolveTargetMethod(pjp);
        return findMethod(clazz, methodName, boolean.class, originMethod.getParameterTypes());
    }
    
    /**
     * 获取原方法
     */
    @Nonnull
    protected Method resolveTargetMethod(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Class<?> targetClass = joinPoint.getTarget().getClass();
        Method method = getDeclaredMethod(targetClass, signature.getName(),
                signature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("Cannot resolve target method: " + signature.getMethod().getName());
        }
        return method;
    }
    
    /**
     * Get declared method with provided name and parameterTypes in given class and its super classes.
     * All parameters should be valid.
     *
     * @param clazz          class where the method is located
     * @param name           method name
     * @param parameterTypes method parameter type list
     * @return resolved method, null if not found
     */
    private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethod(superClass, name, parameterTypes);
            }
        }
        return null;
    }
    
    /**
     * 方法唯一名（className#methodName）
     */
    private static String methodUk(Class<?> clazz, String name) {
        return String.format("%s#%s", clazz.getCanonicalName(), name);
    }
    
    /**
     * 确保可访问
     */
    @SuppressWarnings("deprecation")
    private static void makeAccessible(Method method) {
        boolean isNotPublic = !Modifier.isPublic(method.getModifiers()) ||
                !Modifier.isPublic(method.getDeclaringClass().getModifiers());
        if (isNotPublic && !method.isAccessible()) {
            method.setAccessible(true);
        }
    }
    
    /**
     * 是否是静态方法
     */
    private static boolean isStatic(@Nonnull Method method) {
        return Modifier.isStatic(method.getModifiers());
    }
    
    /**
     * 从指定类中查找方法
     *
     * @param clazz 从哪个类查
     * @param name 要查询的方法名
     * @param returnType 方法的返回值类型基类
     * @param parameterTypes 方法的参数类型
     *
     * @return  查找到的方法
     */
    @Nullable
    static Method findMethod(@Nonnull Class<?> clazz, @Nonnull String name, @Nonnull Class<?> returnType, Class<?>... parameterTypes) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (name.equals(method.getName())
                    && returnType.isAssignableFrom(method.getReturnType())
                    && Arrays.equals(parameterTypes, method.getParameterTypes())) {
                return method;
            }
        }
        // Current class not found, find in the super classes recursively.
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && !Object.class.equals(superClass)) {
            return findMethod(superClass, name, returnType, parameterTypes);
        }
        return null;
    }
    
    public interface GrayscaleAspectOrderProvider extends Ordered {
    
    }
}
