package com.ideaaedi.zoo.commonbase.message.fromto;

import com.ideaaedi.zoo.commonbase.message.FromTo;
import lombok.Data;

/**
 * 系统往手机号发送短信
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SmsSys2Phone implements FromTo<String, String> {
    
    /** 手机号 */
    private String phone;
    
    public String from() {
        return null;
    }
    
    public ToType toType() {
        return ToType.PHONE;
    }
    
    public String to() {
        return this.phone;
    }
}
