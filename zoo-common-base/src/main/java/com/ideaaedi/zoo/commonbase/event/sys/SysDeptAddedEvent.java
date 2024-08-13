package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysDeptPO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 新增部门事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysDeptAddedEvent extends ApplicationEvent {
    
    private final SysDeptPO addedDept;
    
    public SysDeptAddedEvent(SysDeptPO addedDept) {
        super(addedDept);
        Objects.requireNonNull(addedDept, "addedDept cannot be null.");
        this.addedDept = addedDept;
    }
}


