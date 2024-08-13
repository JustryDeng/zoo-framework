package com.ideaaedi.zoo.diy.feature.auditlog.api.advice;

import com.ideaaedi.zoo.diy.feature.auditlog.api.entity.AuditDTO;
import com.ideaaedi.zoo.diy.feature.auditlog.api.properties.ZooAuditLogApiProperties;
import com.ideaaedi.zoo.diy.feature.auditlog.api.spi.AuditLogCollector;
import com.ideaaedi.zoo.diy.feature.auditlog.api.spi.AuditLogRecorder;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;

/**
 * 审计日志切面
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@EnableAspectJAutoProxy(exposeProxy = true)
public class AuditLogPointcutAdvisor extends StaticMethodMatcherPointcutAdvisor {
    
    @SuppressWarnings("rawtypes")
    private final AuditLogCollector auditLogCollector;
    
    public AuditLogPointcutAdvisor(@SuppressWarnings("rawtypes") @Nonnull AuditLogCollector auditLogCollector,
                                   @Nonnull AuditLogRecorder auditLogRecorder,
                                   @Nonnull ZooAuditLogApiProperties zooAuditLogApiProperties) {
        // 设置切面
        setAdvice(new MethodInterceptor() {
            @Nullable
            @Override
            public Object invoke(@Nonnull MethodInvocation invocation) throws Throwable {
                Object targetPointResult = null;
                Object execResult = null;
                Throwable throwable = null;
                AuditDTO auditInfo = null;
                // 采集审计日志
                try {
                    execResult = auditLogCollector.doBefore(invocation, zooAuditLogApiProperties);
                    targetPointResult = invocation.proceed();
                } catch (Throwable th) {
                    throwable = th;
                    throw th;
                } finally {
                    try {
                        //noinspection unchecked
                        auditInfo = auditLogCollector.doAfter(invocation, throwable, execResult, targetPointResult, zooAuditLogApiProperties);
                    } catch (Throwable e) {
                        log.error("Collect audit-log occur exception", e);
                    }
                }
                // 记录审计日志
                if (auditInfo != null) {
                    auditLogRecorder.record(auditInfo);
                }
                return targetPointResult;
            }
        });
        // 设置切面顺序
        setOrder(auditLogCollector.getOrder());
        
        this.auditLogCollector = auditLogCollector;
    }
    
    /**
     * 定义切面触发逻辑
     */
    @Override
    public boolean matches(@Nonnull Method method, @Nonnull Class<?> targetClass) {
        //noinspection unchecked
        return auditLogCollector.staticMatches(method, targetClass);
    }
}
