package com.ideaaedi.zoo.diy.feature.msg.api.sms;

import com.ideaaedi.commonds.env.Env;
import com.ideaaedi.commonds.env.RequiredEnv;
import com.ideaaedi.commonds.env.Unit;
import com.ideaaedi.zoo.commonbase.zoo_face.Face;
import com.ideaaedi.zoo.diy.feature.msg.api.exception.SmsNoSuchSenderException;
import com.ideaaedi.zoo.diy.feature.msg.api.exception.SmsNotPointDefaultSenderException;
import com.ideaaedi.zoo.diy.feature.msg.api.exception.SmsSendWayUnsupportException;
import com.ideaaedi.zoo.diy.feature.msg.api.properties.ZooMsgProperties;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.core.SmsSender;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.core.SmsSenderManager;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.entity.SmsSendResult;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Map;

/**
 * 短信门面工具类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@UtilityClass
public final class SmsFaceUtil implements Face {
    
    static SmsSenderManager smsSenderManager;
    
    static ZooMsgProperties zooMsgProperties;
    
    /**
     * 获取默认的短信发送器
     * <pre>
     * 你可以通过以下配置来指定默认的短信发送器
     * {@code
     *  zoo:
     *    msg:
     *       sms:
     *         # 指定短信的默认发送器
     *         default-sender-id: xxx
     * }
     * </pre>
     *
     * @return 默认的短信发送器
     */
    @Nullable
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static SmsSender getDefaultSender() {
        return smsSenderManager.find(zooMsgProperties.getSms().getDefaultSenderId());
    }
    
    /**
     * 获取短信发送器管理器
     *
     * @return 短信发送器管理器
     */
    @Nonnull
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static SmsSenderManager getSenderManager() {
        return smsSenderManager;
    }
    
    /**
     * 发送短信
     *
     * @see #send(String, String, String...)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static SmsSendResult send(@Nonnull String sms, @Nonnull String... phones)
            throws SmsSendWayUnsupportException {
        String defaultSenderId = zooMsgProperties.getSms().getDefaultSenderId();
        if (StringUtils.isBlank(defaultSenderId)) {
            throw new SmsNotPointDefaultSenderException("you could point default sms-sender by config 'zoo.msg.sms.default-sender-id=xxx'");
        }
        return send(defaultSenderId, sms, phones);
    }
    
    /**
     * 发送短信
     *
     * @param sms 短信
     * @param phones 手机号
     *
     * @return 发送结果
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static SmsSendResult send(@Nonnull String senderId, @Nonnull String sms, @Nonnull String... phones)
            throws SmsSendWayUnsupportException {
        SmsSender smsSender = smsSenderManager.find(senderId);
        if (smsSender == null) {
            throw new SmsNoSuchSenderException("not found sms-sender whose id is '" + senderId + "'.");
        }
        return smsSender.send(sms, phones);
    }
    
    /**
     * 发送短信
     *
     * @see #send(String, Serializable, Map, String...)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static SmsSendResult send(@Nonnull Serializable smsTemplateId, @Nullable Map<String, Object> params,
                                     @Nonnull String... phones)
            throws SmsSendWayUnsupportException {
        String defaultSenderId = zooMsgProperties.getSms().getDefaultSenderId();
        if (StringUtils.isBlank(defaultSenderId)) {
            throw new SmsNotPointDefaultSenderException("you could point default sms-sender by config 'zoo.msg.sms.default-sender-id=xxx'");
        }
        return send(defaultSenderId, smsTemplateId, params, phones);
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
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static SmsSendResult send(@Nonnull String senderId, @Nonnull Serializable smsTemplateId, @Nullable Map<String, Object> params,
                                     @Nonnull String... phones)
            throws SmsSendWayUnsupportException {
        SmsSender smsSender = smsSenderManager.find(senderId);
        if (smsSender == null) {
            throw new SmsNoSuchSenderException("not found sms-sender whose id is '" + senderId + "'.");
        }
        return smsSender.send(smsTemplateId, params, phones);
    }
}
