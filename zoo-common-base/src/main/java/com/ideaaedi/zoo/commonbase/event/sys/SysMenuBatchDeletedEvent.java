package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysMenuPO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 批量删除菜单事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysMenuBatchDeletedEvent extends ApplicationEvent {
    
    private final List<SysMenuPO> deletedMenuList;
    
    public SysMenuBatchDeletedEvent(List<SysMenuPO> deletedMenuList) {
        super(deletedMenuList);
        Assert.isTrue(!CollectionUtils.isEmpty(deletedMenuList), "deletedMenuList cannot be empty.");
        this.deletedMenuList = deletedMenuList;
    }
}


