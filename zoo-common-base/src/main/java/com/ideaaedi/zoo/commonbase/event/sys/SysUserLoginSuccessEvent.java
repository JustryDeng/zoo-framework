package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysUserPO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 用户登录成功事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysUserLoginSuccessEvent extends ApplicationEvent {
    
    private final SysUserPO sysUser;
    
    public SysUserLoginSuccessEvent(SysUserPO sysUser) {
        super(sysUser);
        Objects.requireNonNull(sysUser, "sysUser cannot be null.");
        this.sysUser = sysUser;
    }
}


