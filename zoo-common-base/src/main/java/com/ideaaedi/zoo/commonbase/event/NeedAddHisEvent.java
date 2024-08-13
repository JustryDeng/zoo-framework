package com.ideaaedi.zoo.commonbase.event;

import com.ideaaedi.zoo.commonbase.his.HisIdentify;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 新增历史记录事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class NeedAddHisEvent extends ApplicationEvent {
    
    /** 要新增的历史记录 业务po集合 */
    private final List<?> poList;
    
    /** 业务po对应的历史po类型 */
    private final Class<? extends HisIdentify> hisClazz;
    
    public NeedAddHisEvent(List<?> poList, Class<? extends HisIdentify> hisClazz) {
        super(poList);
        Assert.isTrue(!CollectionUtils.isEmpty(poList), "poList cannot be empty.");
        Objects.requireNonNull(hisClazz, "hisClazz cannot be null");
        this.poList = poList;
        this.hisClazz = hisClazz;
    }
}