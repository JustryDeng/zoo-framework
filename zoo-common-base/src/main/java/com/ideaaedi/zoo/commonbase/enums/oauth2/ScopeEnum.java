package com.ideaaedi.zoo.commonbase.enums.oauth2;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.Getter;

/**
 * oauth scope
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum ScopeEnum implements EnumDescriptor {
    
    /** item */
    SYSTEM_ALL("system-all", "所有域");
    
    private final String scope;
    
    private final String desc;
    
    ScopeEnum(String scope, String desc) {
        this.scope = scope;
        this.desc = desc;
    }
    
    @Override
    public String obtainDescription() {
        return this.desc;
    }
}