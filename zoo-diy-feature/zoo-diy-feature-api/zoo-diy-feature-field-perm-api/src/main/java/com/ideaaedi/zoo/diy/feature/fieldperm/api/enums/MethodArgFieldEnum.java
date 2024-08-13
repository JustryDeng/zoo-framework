package com.ideaaedi.zoo.diy.feature.fieldperm.api.enums;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.Getter;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum MethodArgFieldEnum implements EnumDescriptor {
    
    REQ("请求"),
    RESP("响应");
    
    private final String description;
    
    MethodArgFieldEnum(String description) {
        this.description = description;
    }
    
    @Override
    public String obtainDescription() {
        return description;
    }
}
