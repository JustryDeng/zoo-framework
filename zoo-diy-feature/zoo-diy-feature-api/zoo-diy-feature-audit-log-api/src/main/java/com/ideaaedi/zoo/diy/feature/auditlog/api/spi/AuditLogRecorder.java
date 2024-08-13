package com.ideaaedi.zoo.diy.feature.auditlog.api.spi;

import com.ideaaedi.zoo.diy.feature.auditlog.api.entity.AuditDTO;

import javax.annotation.Nonnull;

/**
 * 审计日志 记录器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2100.7.3
 */
public interface AuditLogRecorder {
    
    /**
     * 记录审计
     * <p>
     * 注：为提升性能，建议进行异步逻辑
     * </p>
     */
    void record(@Nonnull AuditDTO auditInfo);
}
