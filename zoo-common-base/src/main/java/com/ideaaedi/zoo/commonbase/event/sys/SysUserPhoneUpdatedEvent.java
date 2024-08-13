package com.ideaaedi.zoo.commonbase.event.sys;

import com.ideaaedi.zoo.commonbase.enums.sys.ThirdLoginTypeEnum;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.context.ApplicationEvent;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * 手机号update事件 </ br> 注：如果新旧手机号一致，也可以发布此事件，此时此事件内的逻辑会自动跳过
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public class SysUserPhoneUpdatedEvent extends ApplicationEvent {
    
    @Nullable
    private final String oldPhone;
    
    @Nullable
    private final String newPhone;
    
    /**
     * 对应的登录场景，为空{@link CollectionUtils#isEmpty} 则匹配所有场景
     */
    @Nullable
    private final EnumSet<ThirdLoginTypeEnum> loginTypeSet;
    
    public SysUserPhoneUpdatedEvent(String oldPhone, String newPhone) {
        this(oldPhone, newPhone, null);
    }
    
    public SysUserPhoneUpdatedEvent(String oldPhone, String newPhone, EnumSet<ThirdLoginTypeEnum> loginTypeSet) {
        super(Triple.of(oldPhone, newPhone, loginTypeSet));
        this.oldPhone = oldPhone;
        this.newPhone = newPhone;
        this.loginTypeSet = loginTypeSet;
    }
}


