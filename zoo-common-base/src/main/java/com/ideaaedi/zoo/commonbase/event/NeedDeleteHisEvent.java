package com.ideaaedi.zoo.commonbase.event;

import com.ideaaedi.zoo.commonbase.his.HisIdentify;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 删除历史记录事件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class NeedDeleteHisEvent extends ApplicationEvent {
    
    /** 要删除的历史记录里业务id集合 */
    private final List<? extends Number> idList;
    
    /** 业务po对应的历史po类型 */
    private final Class<? extends HisIdentify> hisClazz;
    
    public NeedDeleteHisEvent(List<? extends Number> idList, Class<? extends HisIdentify> hisClazz) {
        super(idList);
        Assert.isTrue(!CollectionUtils.isEmpty(idList), "idList cannot be empty.");
        Objects.requireNonNull(hisClazz, "hisClazz cannot be null");
        this.idList = idList;
        this.hisClazz = hisClazz;
    }
}