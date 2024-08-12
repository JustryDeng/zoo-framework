package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysDeptPO;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 修改部门事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysDeptUpdatedEvent extends ApplicationEvent {
    
    private final SysDeptPO oldDept;
    
    private final SysDeptPO newDept;
    
    public SysDeptUpdatedEvent(SysDeptPO oldDept, SysDeptPO newDept) {
        super(Pair.of(oldDept, newDept));
        Objects.requireNonNull(oldDept, "oldDept cannot be null.");
        Objects.requireNonNull(newDept, "newDept cannot be null.");
        this.oldDept = oldDept;
        this.newDept = newDept;
    }
}


