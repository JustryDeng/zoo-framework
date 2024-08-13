package com.ideaaedi.zoo.commonbase.provider;

import javax.annotation.Nullable;
import java.util.LinkedHashSet;

/**
 * 用户id、租户信息、数据范围信息 provider
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface UserIdTenantDataScopeProvider {
    
    /**
     *  获取用户id
     *
     * @return 用户id
     */
    @Nullable
    Long obtainUserId();
    
    /**
     *  获取租户
     *
     * @return 租户
     */
    @Nullable
    String obtainTenant();
    
    /**
     * 数据范围去重叠后的可读数据范围的deptId对应的deptPath
     *
     * @return 数据范围去重叠后的可读数据范围的deptId对应的deptPath
     */
    @Nullable
    LinkedHashSet<String> distinctReadDataScopePaths();
    
    /**
     * 数据范围去重叠后的可写(修改/删除)数据范围的deptId对应的deptPath
     *
     * @return 数据范围去重叠后的可写(修改/删除)数据范围的deptId对应的deptPath
     */
    @Nullable
    LinkedHashSet<String> distinctUpdateDataScopePaths();
}
