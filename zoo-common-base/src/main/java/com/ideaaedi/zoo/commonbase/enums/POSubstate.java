package com.ideaaedi.zoo.commonbase.enums;

import com.ideaaedi.commonds.tuple.Locator;
import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * po表相关substate状态汇总
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@SuppressWarnings("SpellCheckingInspection")
public interface POSubstate extends EnumDescriptor, Locator<Field>, Supplier<Integer> {
    
    String FIELD_NAME = "substate";
    
    /**
     * 状态值
     */
    int getSubstate();
    
    /**
     * 状态描述
     */
    String getDesc();
    
    @Override
    default String obtainDescription() {
        return this.getDesc();
    }
    
    /**
     * 子类重写时可返回null
     */
    @Nullable
    @Override
    default Field locate() {
        try {
            return this.getClass().getDeclaredField(FIELD_NAME);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    default Integer get() {
        return this.getSubstate();
    }
    
    /**
     * 共有的state
     */
    @Getter
    @AllArgsConstructor
    enum CommonEnum implements POSubstate {
        
        NORMAL(1, "正常"),
        ;
        
        private final int substate;
        
        private final String desc;
        
        @Override
        public Field locate() {
            return null;
        }
    }
}
