package com.ideaaedi.zoo.commonbase.message.fromto;

import com.ideaaedi.zoo.commonbase.message.FromTo;

/**
 * 系统往用户手机号发送短信
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class SmsSys2UserPhone implements FromTo<String, Long> {
    
    /**
     * 要发送的手机号对应的用户id
     */
    private Long userId;
    
    public String from() {
        return null;
    }
    
    public ToType toType() {
        return ToType.PHONE;
    }
    
    public Long to() {
        return this.userId;
    }
    
}
