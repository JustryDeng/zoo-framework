package com.ideaaedi.zoo.commonbase.zoo_component.tenantlike;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.Getter;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 数据可操作域holder
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public final class TenantScopeHolder {
    
    /**
     * 会话级临时数据域值
     */
    @Getter
    public static final ThreadLocal<ConcurrentLinkedDeque<TenantScope>> SESSION_DATA_SCOPE_HOLDER =
            new TransmittableThreadLocal<>() {
                @Override
                public ConcurrentLinkedDeque<TenantScope> copy(ConcurrentLinkedDeque<TenantScope> parentValue) {
                    return new ConcurrentLinkedDeque<>(
                            parentValue.stream().map(tenantScope -> {
                                if (tenantScope == null) {
                                    return null;
                                }
                                return DefaultTenantScope.of(tenantScope.insertTenant(), tenantScope.readableTenants(), tenantScope.modifiableTenants());
                            }).toList()
                    );
                }
                
                @Override
                protected ConcurrentLinkedDeque<TenantScope> initialValue() {
                    return new ConcurrentLinkedDeque<>();
                }
            };
}
