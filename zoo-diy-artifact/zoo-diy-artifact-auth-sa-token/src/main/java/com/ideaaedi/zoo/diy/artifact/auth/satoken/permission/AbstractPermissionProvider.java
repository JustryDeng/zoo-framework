package com.ideaaedi.zoo.diy.artifact.auth.satoken.permission;

import cn.dev33.satoken.stp.StpInterface;
import com.ideaaedi.zoo.commonbase.zoo_component.logging.TraceBaseUtil;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScope;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.processor.ZooAuthePostProcessor;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * 权限数据获取器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public abstract class AbstractPermissionProvider implements ZooAuthePostProcessor, StpInterface {
    
    /**
     * 获取用户数据域信息
     *
     * @param loginType 账号类别
     * @param loginId 账号id
     * @param tokenValue 本次登录产生的 token 值
     *
     * @return 用户的数据域
     */
    @Nullable
    protected abstract TenantScope getTenantScope(String loginType, Object loginId, String tokenValue);
    

    @Override
    public void doAfterAuthe(String loginType, Object loginId, String tokenValue) {
        Objects.requireNonNull(loginId, "loginId should not be null.");
        String userIdStr = loginId.toString();
        // 将用户id填入traceXd
        TraceBaseUtil.fillUserId2TraceXd(userIdStr);
    
        // 将用户域记录进上下文中
        ZooContext.setUserIdAndTenantScope(
                Long.parseLong(userIdStr),
                getTenantScope(loginType, loginId, tokenValue)
        );
    }

}
