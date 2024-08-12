package com.ideaaedi.zoo.commonbase.enums.sys;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.Getter;

/**
 * 组织架构类型枚举
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum DeptTypeEnum implements EnumDescriptor {
    
    DEPT("部门"),
    
    FACTORY("工厂"),
    ;
    
    private final String desc;
    
    DeptTypeEnum(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String obtainDescription() {
        return this.desc;
    }
}
