package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysDeptPO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 删除部门事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysDeptDeletedEvent extends ApplicationEvent {
    
    private final SysDeptPO deletedDept;
    
    public SysDeptDeletedEvent(SysDeptPO deletedDept) {
        super(deletedDept);
        Objects.requireNonNull(deletedDept, "deletedDept cannot be null.");
        this.deletedDept = deletedDept;
    }
}


