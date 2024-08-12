package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysMenuPO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 删除菜单事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysMenuDeletedEvent extends ApplicationEvent {
    
    private final SysMenuPO deletedMenu;
    
    public SysMenuDeletedEvent(SysMenuPO deletedMenu) {
        super(deletedMenu);
        Objects.requireNonNull(deletedMenu, "deletedMenu cannot be null.");
        this.deletedMenu = deletedMenu;
    }
}


