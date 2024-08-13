package com.ideaaedi.zoo.commonbase.enums.sys;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;

/**
 * 数据操作类型
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public enum DataScopeTypeEnum implements EnumDescriptor {
    
    /** 查询数据 */
    READ("查询数据"),
    
    /** 护照 */
    UPDATE("修改/删除数据");
    
    private final String desc;
    
    DataScopeTypeEnum(String desc) {
        this.desc = desc;
    }
    
    @Override
    public String obtainDescription() {
        return this.desc;
    }
}