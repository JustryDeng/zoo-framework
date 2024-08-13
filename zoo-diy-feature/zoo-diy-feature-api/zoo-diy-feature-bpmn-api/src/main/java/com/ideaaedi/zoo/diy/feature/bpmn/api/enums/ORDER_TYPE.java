package com.ideaaedi.zoo.diy.feature.bpmn.api.enums;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.Getter;

/**
 * 排序方式
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum ORDER_TYPE implements EnumDescriptor {
    
    ASC(true, "升序（缺省默认值）"),
    DESC(false, "降序");
    
    private final boolean defaultValue;
    
    private final String description;
    
    ORDER_TYPE(boolean defaultValue, String description) {
        this.defaultValue = defaultValue;
        this.description = description;
    }
    
    @Override
    public String obtainDescription() {
        return this.description;
    }
}
