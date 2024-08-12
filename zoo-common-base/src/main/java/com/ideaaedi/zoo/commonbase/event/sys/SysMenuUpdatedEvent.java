package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysMenuPO;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 修改菜单事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysMenuUpdatedEvent extends ApplicationEvent {
    
    private final SysMenuPO oldMenu;
    
    private final SysMenuPO newMenu;
    
    public SysMenuUpdatedEvent(SysMenuPO oldMenu, SysMenuPO newMenu) {
        super(Pair.of(oldMenu, newMenu));
        Objects.requireNonNull(oldMenu, "oldMenu cannot be null.");
        Objects.requireNonNull(newMenu, "newMenu cannot be null.");
        this.oldMenu = oldMenu;
        this.newMenu = newMenu;
    }
}


