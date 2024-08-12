package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysRolePO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 删除角色事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysRoleDeletedEvent extends ApplicationEvent {
    
    private final SysRolePO deletedRole;
    
    public SysRoleDeletedEvent(SysRolePO deletedRole) {
        super(deletedRole);
        Objects.requireNonNull(deletedRole, "deletedRole cannot be null.");
        this.deletedRole = deletedRole;
    }
}


