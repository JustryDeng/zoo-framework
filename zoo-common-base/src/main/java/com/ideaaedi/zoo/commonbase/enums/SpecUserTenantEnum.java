package com.ideaaedi.zoo.commonbase.enums;

import com.google.common.collect.Lists;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.DefaultTenantScope;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScope;
import lombok.Getter;

/**
 * 特殊用户id & 租户信息
 * <br />
 * userId <= 100，是系统预留出来的，可以设置在这个范围内
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum SpecUserTenantEnum {
    
    /** 系统定时任务 用户id、租户、可读数据范围、可写数据范围 */
    SYSTEM_SCHEDULE(36, DefaultTenantScope.of("1,", Lists.newArrayList("1,"), Lists.newArrayList("1,"))),
    
    /** 单元测试 用户id、租户、可读数据范围、可写数据范围 */
    SYSTEM_UNIT_TEST(37, DefaultTenantScope.of("1,", Lists.newArrayList("1,"), Lists.newArrayList("1,"))),
    
    /** 刷新api资源信息 */
    SYSTEM_REFRESH_API_RESOURCE(38, DefaultTenantScope.of("1,", Lists.newArrayList("1,"), Lists.newArrayList("1,"))),
    
    /** 系统读取access_control相关数据. 用户可访问的api、用户所属租户、用户可读写的数据范围 */
    SYSTEM_READ_ACCESS_CONTROL_DATA(39, DefaultTenantScope.of("1,", Lists.newArrayList("1,"), Lists.newArrayList("1,"))),
    
    /** 系统监听器 */
    SYSTEM_LISTENER(40, DefaultTenantScope.of("1,", Lists.newArrayList("1,"), Lists.newArrayList("1,"))),
    ;
    
    /** 用户id */
    private final long userId;
    
    /** 数据域租户值 */
    private final TenantScope tenantScope;
    
    SpecUserTenantEnum(long userId, TenantScope tenantScope) {
        this.userId = userId;
        this.tenantScope = tenantScope;
    }
}
