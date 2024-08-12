package com.ideaaedi.zoo.diy.feature.auditlog.api.spi;

import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.diy.feature.auditlog.api.entity.AuditDTO;
import com.ideaaedi.zoo.diy.feature.auditlog.api.properties.ZooAuditLogApiProperties;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.Ordered;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * 审计日志 采集器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2100.7.3
 */
public interface AuditLogCollector<R> extends Ordered {
    
    /**
     * 是否触发切面增强
     * <p>
     * 注：在编译时或加载时就会根据此方法确定匹配规则。在运行时不会再调用此方法
     * </p>
     *
     * @return true-触发，false-不触发
     */
    boolean staticMatches(@Nonnull Method method, @Nonnull Class<?> targetClass);
    
    /**
     * 前处理逻辑
     * <p>
     * 注：在业逻辑之前执行
     * </p>
     *
     * @param invocation 方法调用对象
     *
     * @return 返回结果
     */
    R doBefore(@Nonnull MethodInvocation invocation, @Nonnull ZooAuditLogApiProperties zooAuditLogApiProperties);
    
    /**
     * 后处理逻辑
     * <p>
     * 注：在业逻辑之后执行，无论业务逻辑是否发生异常都会执行
     * </p>
     *
     * @param invocation 方法调用对象
     * @param throwable 异常
     * <br />{@link AuditLogCollector#doBefore(MethodInvocation, ZooAuditLogApiProperties)} 或{@link MethodInvocation#proceed()}所发生的异常
     * @param beforeResult 方法调用对象
     * <br />{@link AuditLogCollector#doBefore(MethodInvocation, ZooAuditLogApiProperties)}的返回结果
     * @param targetPointResult 方法调用对象
     * <br />{@link MethodInvocation#proceed()}的返回结果
     * @param zooAuditLogApiProperties 基础配置
     *
     * @return 审计日志对象
     */
    AuditDTO doAfter(@Nonnull MethodInvocation invocation, @Nullable Throwable throwable,
                     @Nullable R beforeResult, @Nullable Object targetPointResult,
                     @Nonnull ZooAuditLogApiProperties zooAuditLogApiProperties);
    
    @Override
    default int getOrder() {
        return AutoConfigurationConstant.ZOO_AUDIT_LOG_COLLECTOR_ORDER;
    }
}
