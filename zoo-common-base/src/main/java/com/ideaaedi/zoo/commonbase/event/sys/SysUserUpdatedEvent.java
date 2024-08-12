package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysUserPO;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 修改用户事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysUserUpdatedEvent extends ApplicationEvent {
    
    private final SysUserPO oldUser;
    
    private final SysUserPO newUser;
    
    public SysUserUpdatedEvent(SysUserPO oldUser, SysUserPO newUser) {
        super(Pair.of(oldUser, newUser));
        Objects.requireNonNull(oldUser, "oldUser cannot be null.");
        Objects.requireNonNull(newUser, "newUser cannot be null.");
        this.oldUser = oldUser;
        this.newUser = newUser;
    }
}


