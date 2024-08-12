package com.ideaaedi.zoo.diy.artifact.tenantlike.tenant;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ideaaedi.commonds.function.NoArgConsumer;
import com.ideaaedi.commonds.function.NoArgFunction;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.DefaultTenantScope;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScope;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScopeHolder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 临时租户提供器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public final class TenantProvider {
    
    /**
     * （全局）执行并返回结果
     * <p>
     * 注：全局只对查有效
     *
     * @see TenantProvider#exec(NoArgFunction, String, Collection, Collection)
     */
    public static <R> R execGlobalSelect(@Nonnull NoArgFunction<R> function) {
        return TenantProvider.exec(function, null, Lists.newArrayList(TenantScope.IGNORE_URD_SCOPE), null);
    }
    
    /**
     * 执行
     * <p>
     * 注：全局只对查有效
     * <p>
     *
     * @see TenantProvider#voidExec(NoArgConsumer, String, Collection, Collection)
     */
    public static void voidExecGlobalSelect(@Nonnull NoArgConsumer consumer) {
        TenantProvider.voidExec(consumer,  null, Lists.newArrayList(TenantScope.IGNORE_URD_SCOPE), null);
    }
    
    /**
     * 执行并返回结果
     * <p>
     * 注：增删改查数据范围都为tenant
     * <p>
     * @see TenantProvider#exec(NoArgFunction, String, Collection, Collection)
     */
    public static <R> R exec(@Nonnull NoArgFunction<R> function, @Nonnull String curdTenant) {
        Set<String> dataScopeTenants = Sets.newHashSet(curdTenant);
        return TenantProvider.exec(function, curdTenant, dataScopeTenants, dataScopeTenants);
    }
    
    /**
     * 执行
     * <p>
     * 注：数据读写范围与租户值保持一致
     * <p>
     * @see TenantProvider#voidExec(NoArgConsumer, String, Collection, Collection)
     */
    public static void voidExec(@Nonnull NoArgConsumer consumer, @Nonnull String curdTenant) {
        Set<String> dataScopeTenants = Sets.newHashSet(curdTenant);
        TenantProvider.voidExec(consumer, curdTenant, dataScopeTenants, dataScopeTenants);
    }
    
    /**
     * 执行并返回结果
     *
     * @param function 代码逻辑
     * @param insertTenant 插入（insert）时的租户值（一般为：用户所属租户）
     * @param readableTenants 可读（select）租户范围
     * @param modifiableTenants 可写（update、delete）租户范围
     */
    public static <R> R exec(@Nonnull NoArgFunction<R> function, @Nullable String insertTenant,
                             @Nullable Collection<String> readableTenants,
                             @Nullable Collection<String> modifiableTenants) {
        try {
            TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.get().addFirst(
                    DefaultTenantScope.of(insertTenant, readableTenants, modifiableTenants)
            );
            return function.apply();
        } finally {
            ConcurrentLinkedDeque<TenantScope> tenantScopes = TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.get();
            if (tenantScopes.size() > 0) {
                tenantScopes.pollFirst();
                if (tenantScopes.size() == 0) {
                    TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.remove();
                }
            }
        }
    }
    
    /**
     * 执行
     *
     * @param consumer 代码逻辑
     * @param insertTenant 插入（insert）时的租户值（一般为：用户所属租户）
     * @param readableTenants 可读（select）租户范围
     * @param modifiableTenants 可写（update、delete）租户范围
     */
    public static void voidExec(@Nonnull NoArgConsumer consumer, @Nullable String insertTenant,
                                @Nullable Collection<String> readableTenants,
                                @Nullable Collection<String> modifiableTenants) {
        try {
            TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.get().addFirst(
                    DefaultTenantScope.of(insertTenant, readableTenants, modifiableTenants)
            );
            consumer.accept();
        } finally {
            ConcurrentLinkedDeque<TenantScope> tenantScopes = TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.get();
            if (tenantScopes.size() > 0) {
                tenantScopes.pollFirst();
                if (tenantScopes.size() == 0) {
                    TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.remove();
                }
            }
        }
    }
    
}
