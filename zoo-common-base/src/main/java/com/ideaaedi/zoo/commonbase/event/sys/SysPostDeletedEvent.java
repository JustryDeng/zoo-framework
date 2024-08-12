package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysPostPO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.Objects;

/**
 * 删除职位事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysPostDeletedEvent extends ApplicationEvent {
    
    private final SysPostPO deletedPost;
    
    public SysPostDeletedEvent(SysPostPO deletedPost) {
        super(deletedPost);
        Objects.requireNonNull(deletedPost, "deletedPost cannot be null.");
        this.deletedPost = deletedPost;
    }
}


