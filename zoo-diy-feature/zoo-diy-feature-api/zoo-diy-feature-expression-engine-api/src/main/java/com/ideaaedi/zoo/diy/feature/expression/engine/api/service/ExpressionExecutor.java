package com.ideaaedi.zoo.diy.feature.expression.engine.api.service;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.exception.ExpressionExecException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 表达式脚本执行器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface ExpressionExecutor {
    
    /**
     * 执行一段脚本
     *
     * @see ExpressionExecutor#execute(String, Map, List, long)
     */
    @Nullable
    default <T> T execute(@Nonnull String expressScript) throws ExpressionExecException {
        return execute(expressScript, null, null, -1);
    }
    
    /**
     * 执行一段脚本
     *
     * @see ExpressionExecutor#execute(String, Map, List, long)
     */
    @Nullable
    default <T> T execute(@Nonnull String expressScript, @Nullable Map<String, Object> context) throws ExpressionExecException {
        return execute(expressScript, context, null, -1);
    }
    
    /**
     * 执行一段脚本
     *
     * @see ExpressionExecutor#execute(String, Map, List, long)
     */
    @Nullable
    default <T> T execute(@Nonnull String expressScript, @Nullable List<String> errorList) throws ExpressionExecException {
        return execute(expressScript, null, errorList, -1);
    }
    
    /**
     * 执行一段脚本
     *
     * @see ExpressionExecutor#execute(String, Map, List, long)
     */
    @Nullable
    default <T> T execute(@Nonnull String expressScript, @Nullable Map<String, Object> context,
                           @Nullable List<String> errorList) throws ExpressionExecException {
        return execute(expressScript, context, errorList, -1);
    }
    
    /**
     * 执行一段脚本
     *
     * @see ExpressionExecutor#execute(String, Map, List, long)
     */
    @Nullable
    default <T> T execute(@Nonnull String expressScript, long timeoutMillis) throws ExpressionExecException {
        return execute(expressScript, null, null, timeoutMillis);
    }
    
    /**
     * 执行一段脚本
     *
     * @see ExpressionExecutor#execute(String, Map, List, long)
     */
    @Nullable
    default <T> T execute(@Nonnull String expressScript, @Nullable Map<String, Object> context,
                           long timeoutMillis) throws ExpressionExecException {
        return execute(expressScript, context, null, timeoutMillis);
    }
    
    /**
     * 执行一段脚本
     *
     * @return 执行结果
     *
     * @see ExpressionExecutor#execute(String, Map, List, long)
     */
    @Nullable
    default <T> T execute(@Nonnull String expressScript, @Nullable List<String> errorList,
                           long timeoutMillis) throws ExpressionExecException {
        return execute(expressScript, null, errorList, timeoutMillis);
    }
    
    /**
     * 执行一段脚本
     *
     * @param expressScript 程序脚本
     * @param context 执行上下文
     * @param errorList 输出的错误信息List
     * @param timeoutMillis 超时时长（单位毫秒）（-1表示本次执行永不超时）
     * <p>
     * 注：部分表达式引擎实现（如QLExpress），可能还有全局超时控制。虽然没触发本次的超时限制，但是可能触发全局的超时限制
     * </p>
     *
     * @return 执行结果
     */
    @Nullable
    <T> T execute(@Nonnull String expressScript, @Nullable Map<String, Object> context,
                   @Nullable List<String> errorList, long timeoutMillis) throws ExpressionExecException;
    
    /**
     * 简单检查脚本语法是否正确
     * <p>
     * 提示：这只是一个语法的简单检查，实际情况各种各样，最好在简单检查通过后，在测试环境测试没问题后再考虑上线
     * </p>
     *
     * @param expressScript 程序脚本
     *
     * @return true-语法正确；false-语法错误
     */
    boolean checkSyntax(@Nonnull String expressScript) throws ExpressionExecException;
    
    /**
     * 获取脚本需要的外部变量名
     *
     * @param expressScript 程序脚本
     *
     * @return 脚本需要的外部变量名
     */
    @Nonnull
    Set<String> getOutVarNames(@Nonnull String expressScript) throws ExpressionExecException;

    /**
     * 获取脚本需要的外部函数名
     *
     * @param expressScript 程序脚本
     *
     * @return 脚本需要的外部函数名
     */
    @Nonnull
    Set<String> getOutFunctionNames(@Nonnull String expressScript) throws ExpressionExecException;
    
    /**
     * 添加一个全局函数（，由指定类的指定方法实现）
     *
     * @see ExpressionExecutor#addGlobalFunction(String, Class, String, Class[], boolean)
     */
    default void addGlobalFunction(@Nonnull String name, @Nonnull Class<?> clazz, @Nonnull String methodName,
                                   @Nonnull Class<?>[] paramTypes) throws ExpressionExecException {
        addGlobalFunction(name, clazz, methodName, paramTypes, false);
    }
    
    /**
     * 添加一个全局函数（，由指定类的指定方法实现）
     * <p>
     * 注1：一般用于添加静态方法
     * </p>
     *
     * @param name 函数名
     * @param clazz 指定的实现类
     * @param methodName 指定的实现方法
     * @param paramTypes 方法的参数类型
     * @param force 当已存在同名函数时，是否强制添加（true-当已存在同名函数时，覆盖原函数；false-当已存在同名函数时，抛出异常）
     */
    void addGlobalFunction(@Nonnull String name, @Nonnull Class<?> clazz,
                           @Nonnull String methodName, @Nonnull Class<?>[] paramTypes, boolean force)
                            throws ExpressionExecException;
    
    /**
     * 添加一个全局函数（，由指定对象的指定方法实现）
     *
     * @see ExpressionExecutor#addGlobalFunction(String, Object, String, Class[], boolean)
     */
    default void addGlobalFunction(@Nonnull String name, @Nonnull Object instance,
                           @Nonnull String methodName, @Nonnull Class<?>[] paramTypes)
                            throws ExpressionExecException {
        addGlobalFunction(name, instance, methodName, paramTypes, false);
    }
    
    /**
     * 添加一个全局函数（，由指定对象的指定方法实现）
     * <p>
     * 注1：一般用于添加实例方法
     * </p>
     *
     * @param name 函数名
     * @param instance 指定的实现实例（如：spring-bean等）
     * @param methodName 指定的实现方法
     * @param paramTypes 方法的参数类型
     * @param force 当已存在同名函数时，是否强制添加（true-当已存在同名函数时，覆盖原函数；false-当已存在同名函数时，抛出异常）
     */
    void addGlobalFunction(@Nonnull String name, @Nonnull Object instance,
                           @Nonnull String methodName, @Nonnull Class<?>[] paramTypes, boolean force)
                            throws ExpressionExecException;
}
