package com.ideaaedi.zoo.commonbase.message;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;

import java.io.Serializable;

/**
 * 消息交互方信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface FromTo<From, To> extends Serializable {
    
    /**
     * 发送方类型
     * <br />
     * {@link FromTo#from}的类型
     *
     * @return 发送方类型
     */
    default FromType fromType() {
        return FromType.SYSTEM;
    }
    
    /**
     * 发送方信息
     * <p>
     * <ul>
     *     说明：
     *     <ol>1. 当{@link FromTo#fromType}值为{@link FromType#SYSTEM}时， 不会用到此字段</ol>
     * </ul>
     *
     * @return 发送方信息
     */
    From from();
    
    /**
     * 接收方类型
     * <br />
     * {@link FromTo#to()}的类型
     *
     * @return 接收方类型
     */
    ToType toType();
    
    /**
     * 接收方信息
     *
     * @return 接收方信息
     */
    To to();
    
    /**
     * 消息发送方类型
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
     * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    enum FromType implements EnumDescriptor {
        
        SYSTEM("系统"),
        ;
        
        /** 描述 */
        final String desc;
    
        FromType(String desc) {
            this.desc = desc;
        }
        
        @Override
        public String obtainDescription() {
            return this.desc;
        }
    }
    
    /**
     * 消息接收方类型
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
     * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    enum ToType implements EnumDescriptor {
    
        USER_PHONE("用户关联的手机号"),
        
        PHONE("手机号"),
        ;
        
        /** 描述 */
        final String desc;
    
        ToType(String desc) {
            this.desc = desc;
        }
        
        @Override
        public String obtainDescription() {
            return this.desc;
        }
    }
}
