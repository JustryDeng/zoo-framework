package com.ideaaedi.zoo.commonbase.enums.sys;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.Getter;

/**
 * 用户类型枚举
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum UserTypeEnum implements EnumDescriptor {
    
    /** 短期临时用户 */
    TEMP_T("短期临时用户"),
    
    /** 长期临时用户 */
    TEMP_E("长期临时用户"),
    
    /** 客户端ak/sk用户 */
    CLIENT_AK_SK("客户端用户"),
    
    /** 普通用户 */
    NORMAL("普通用户"),

    /** 管理后台用户 */
    BACKEND("管理后台用户"),
    ;
    
    private final String desc;
    
    UserTypeEnum(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String obtainDescription() {
        return this.desc;
    }
}
