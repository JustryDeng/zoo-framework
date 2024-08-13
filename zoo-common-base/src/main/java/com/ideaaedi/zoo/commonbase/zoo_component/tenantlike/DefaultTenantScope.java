package com.ideaaedi.zoo.commonbase.zoo_component.tenantlike;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * 数据域可操作域
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class DefaultTenantScope implements TenantScope {
    
    /**
     * 用户插入（insert）时的租户值（一般为：用户所属租户）
     */
    private String insertTenant;
    
    /**
     * 用户可读（select）租户范围
     */
    private Collection<String> readableTenants;
    
    /**
     * 用户可写（update、delete）租户范围
     */
    private Collection<String> modifiableTenants;
    
    @Override
    public String insertTenant() {
        return this.insertTenant;
    }
    
    @Override
    public Collection<String> readableTenants() {
        return this.readableTenants;
    }
    
    @Override
    public Collection<String> modifiableTenants() {
        return this.modifiableTenants;
    }
    
    /**
     * 快速创建
     *
     * @param insertTenant 用户插入（insert）时的租户值（一般为：用户所属租户）
     * @param readableTenants 用户可读（select）租户范围
     * @param modifiableTenants 用户可写（update、delete）租户范围
     *
     * @return 数据可操作域
     */
    public static DefaultTenantScope of(@Nullable String insertTenant,
                                        @Nullable Collection<String> readableTenants,
                                        @Nullable Collection<String> modifiableTenants) {
        DefaultTenantScope dtdsp = new DefaultTenantScope();
        dtdsp.setInsertTenant(insertTenant);
        dtdsp.setReadableTenants(readableTenants);
        dtdsp.setModifiableTenants(modifiableTenants);
        return dtdsp;
    }
    
    /**
     * 快速创建
     *
     * @param curdTenant 用户增删改查都使用的数据域租户值
     *
     * @return 数据可操作域
     */
    public static DefaultTenantScope of(@Nullable String curdTenant) {
        DefaultTenantScope dtdsp = new DefaultTenantScope();
        if (curdTenant != null) {
            dtdsp.setInsertTenant(curdTenant);
            dtdsp.setReadableTenants(Lists.newArrayList(curdTenant));
            dtdsp.setModifiableTenants(Lists.newArrayList(curdTenant));
        }
        return dtdsp;
    }
}
