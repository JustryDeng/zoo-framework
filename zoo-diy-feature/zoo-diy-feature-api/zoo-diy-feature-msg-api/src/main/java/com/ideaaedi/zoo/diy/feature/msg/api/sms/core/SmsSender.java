package com.ideaaedi.zoo.diy.feature.msg.api.sms.core;

import com.ideaaedi.zoo.diy.feature.msg.api.exception.SmsSendWayUnsupportException;
import com.ideaaedi.zoo.diy.feature.msg.api.msg.MsgSender;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.entity.SmsSendResult;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

/**
 * 短信发送器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface SmsSender extends MsgSender {
    
    /**
     * 发送短信
     *
     * @param sms 短信
     * @param phones 手机号
     *
     * @return 发送结果
     */
    @Nonnull
    default SmsSendResult send(@Nonnull String sms, @Nonnull String... phones)
            throws SmsSendWayUnsupportException {
        throw new SmsSendWayUnsupportException(this.getClass().getName()
                + " not support send(java.lang.String, java.lang.String...)");
    }
    
    /**
     * 发送短信
     *
     * @param smsTemplateId 短信模板id
     * @param params 短信模板参数
     * @param phones 手机号
     *
     * @return 发送结果
     */
    @Nonnull
    default SmsSendResult send(@Nonnull Serializable smsTemplateId, @Nullable Map<String, Object> params, @Nonnull String... phones)
            throws SmsSendWayUnsupportException {
        throw new SmsSendWayUnsupportException(this.getClass().getName()
                + " not support send(java.lang.Object, java.util.Map<java.lang.String,java.lang.Object>, java.lang.String...)");
    }
}
