package com.ideaaedi.zoo.commonbase.enums.sys;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.Getter;

/**
 * API资源类型枚举
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum ApiTypeEnum implements EnumDescriptor {
    
    PUBLIC("公开的（访问无需token）"),

    PRIVATE("私有的（访问需要token）"),
    
    ANT_MATCH("ant匹配的api");
    
    private final String desc;
    
    ApiTypeEnum(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String obtainDescription() {
        return this.desc;
    }
}
