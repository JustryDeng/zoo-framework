package com.ideaaedi.zoo.diy.feature.msg.sms.impl.sms4j.core;

import com.ideaaedi.zoo.diy.feature.msg.api.exception.SmsSendWayUnsupportException;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.core.SmsSender;
import com.ideaaedi.zoo.diy.feature.msg.api.sms.entity.SmsSendResult;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.callback.CallBack;
import org.dromara.sms4j.api.entity.SmsResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class SmsBlendWrapper implements SmsBlend, SmsSender {
    
    private final SmsBlend proxy;
    
    public SmsBlendWrapper(SmsBlend smsBlend) {
        this.proxy = smsBlend;
    }
    
    @Override
    public String getConfigId() {
        return proxy.getConfigId();
    }
    
    @Override
    public String getSupplier() {
        return proxy.getSupplier();
    }
    
    @Override
    public SmsResponse sendMessage(String phone, String message) {
        return proxy.sendMessage(phone, message);
    }
    
    @Override
    public SmsResponse sendMessage(String phone, LinkedHashMap<String, String> messages) {
        return proxy.sendMessage(phone, messages);
    }
    
    @Override
    public SmsResponse sendMessage(String phone, String templateId, LinkedHashMap<String, String> messages) {
        return proxy.sendMessage(phone, templateId, messages);
    }
    
    @Override
    public SmsResponse massTexting(List<String> phones, String message) {
        return proxy.massTexting(phones, message);
    }
    
    @Override
    public SmsResponse massTexting(List<String> phones, String templateId, LinkedHashMap<String, String> messages) {
        return proxy.massTexting(phones, templateId, messages);
    }
    
    @Override
    public void sendMessageAsync(String phone, String message, CallBack callBack) {
        proxy.sendMessageAsync(phone, message, callBack);
    }
    
    @Override
    public void sendMessageAsync(String phone, String message) {
        proxy.sendMessageAsync(phone, message);
    }
    
    @Override
    public void sendMessageAsync(String phone, String templateId, LinkedHashMap<String, String> messages,
                                 CallBack callBack) {
        proxy.sendMessageAsync(phone, templateId, messages, callBack);
    }
    
    @Override
    public void sendMessageAsync(String phone, String templateId, LinkedHashMap<String, String> messages) {
        proxy.sendMessageAsync(phone, templateId, messages);
    }
    
    @Override
    public void delayedMessage(String phone, String message, Long delayedTime) {
        proxy.delayedMessage(phone, message, delayedTime);
    }
    
    @Override
    public void delayedMessage(String phone, String templateId, LinkedHashMap<String, String> messages,
                               Long delayedTime) {
        proxy.delayedMessage(phone, templateId, messages, delayedTime);
    }
    
    @Override
    public void delayMassTexting(List<String> phones, String message, Long delayedTime) {
        proxy.delayMassTexting(phones, message, delayedTime);
    }
    
    @Override
    public void delayMassTexting(List<String> phones, String templateId, LinkedHashMap<String, String> messages,
                                 Long delayedTime) {
        proxy.delayMassTexting(phones, templateId, messages, delayedTime);
    }
    
    @Override
    public void joinInBlacklist(String phone) {
        proxy.joinInBlacklist(phone);
    }
    
    @Override
    public void removeFromBlacklist(String phone) {
        proxy.removeFromBlacklist(phone);
    }
    
    @Override
    public void batchJoinBlacklist(List<String> phones) {
        proxy.batchJoinBlacklist(phones);
    }
    
    @Override
    public void batchRemovalFromBlacklist(List<String> phones) {
        proxy.batchRemovalFromBlacklist(phones);
    }
    
    @Override
    public String id() {
        return proxy.getConfigId();
    }
    
    @Override
    public String supplier() {
        return proxy.getSupplier();
    }
    
    @Override
    public List<String> tags() {
        return Collections.emptyList();
    }
    
    @Override
    public SmsSendResult send(@Nonnull Serializable smsTemplateId, @Nullable Map<String, Object> params, @Nonnull String... phones) throws SmsSendWayUnsupportException {
        LinkedHashMap<String, String> messages = new LinkedHashMap<>(16);
        if (params != null) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                Object value = entry.getValue();
                if (value == null) {
                    continue;
                }
                messages.put(entry.getKey(), value.toString());
            }
        }
        SmsResponse smsResponse;
        if (phones.length == 1) {
            smsResponse = proxy.sendMessage(phones[0], smsTemplateId.toString(), messages);
        } else {
            smsResponse = proxy.massTexting(Arrays.stream(phones).toList(), smsTemplateId.toString(), messages);
        }
        boolean isSuccess = smsResponse.isSuccess();
        Object data = smsResponse.getData();
        String configId = smsResponse.getConfigId();
        SmsSendResult smsSendResult = new SmsSendResult();
        smsSendResult.setSuccess(isSuccess);
        if (!isSuccess) {
            smsSendResult.setFailReason(data);
        }
        smsSendResult.setSenderId(configId);
        smsSendResult.setRaw(data);
        return smsSendResult;
    }
}
