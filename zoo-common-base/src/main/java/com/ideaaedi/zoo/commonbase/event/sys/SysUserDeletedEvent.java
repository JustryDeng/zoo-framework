package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysUserPO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 删除用户事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysUserDeletedEvent extends ApplicationEvent {
    
    private final SysUserPO deletedUser;
    
    public SysUserDeletedEvent(SysUserPO deletedUser) {
        super(deletedUser);
        Objects.requireNonNull(deletedUser, "deletedUser cannot be null.");
        this.deletedUser = deletedUser;
    }
}


