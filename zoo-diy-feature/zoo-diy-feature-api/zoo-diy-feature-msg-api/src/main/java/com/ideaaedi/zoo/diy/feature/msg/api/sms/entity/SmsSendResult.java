package com.ideaaedi.zoo.diy.feature.msg.api.sms.entity;

import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.List;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SmsSendResult implements RawSupport {
    
    /**
     * 是否成功
     * <br />
     * 对于多手机号的发送，全部都成功才为true，否则为false
     */
    private boolean success;
    
    /**
     * 发送成功的手机号
     */
    @Nullable
    private List<String> successPhoneList;
    
    /**
     * 发送失败的手机号
     */
    @Nullable
    private List<String> failPhoneList;
    
    /**
     * 发送失败原因
     */
    @Nullable
    private Object failReason;
    
    /**
     * 短信发送器id
     */
    private String senderId;
    
    /**
     * 原始结果
     */
    private Object raw;
}
