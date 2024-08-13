package com.ideaaedi.zoo.diy.feature.msg.api.sms.core;

import com.ideaaedi.zoo.diy.feature.msg.api.exception.SmsSenderAlreadyExistException;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class DefaultSmsSenderManager implements SmsSenderManager {
    
    private static final Map<String, SmsSender> SENDER_HOLDER = new ConcurrentHashMap<>(16);
    
    public DefaultSmsSenderManager(List<SmsSender> senderList) {
        if (!CollectionUtils.isEmpty(senderList)) {
            add(senderList.toArray(new SmsSender[0]));
        }
    }
    
    @Nullable
    @Override
    public SmsSender find(@Nullable String senderId) {
        if (senderId == null) {
            return null;
        }
        return SENDER_HOLDER.get(senderId);
    }
    
    @Nullable
    @Override
    public SmsSender findFirst(@Nullable String... senderIds) {
        if (senderIds == null) {
            return null;
        }
        for (String senderId : senderIds) {
            SmsSender smsSender = SENDER_HOLDER.get(senderId);
            if (smsSender != null) {
                return smsSender;
            }
        }
        return null;
    }
    
    @Nonnull
    @Override
    public Map<String, SmsSender> findAll() {
        return new HashMap<>(SENDER_HOLDER);
    }
    
    @Override
    public boolean exist(@Nullable String senderId) {
        return SENDER_HOLDER.containsKey(senderId);
    }
    
    @Override
    public void add(@Nullable SmsSender smsSender) throws SmsSenderAlreadyExistException {
        if (smsSender == null) {
            return;
        }
        String id = smsSender.id();
        boolean exist = SENDER_HOLDER.containsKey(id);
        if (exist) {
            throw new SmsSenderAlreadyExistException(String.format("senderId[%s] already exist.", id));
        }
        SENDER_HOLDER.put(id, smsSender);
    }
    
    @Override
    public void add(@Nullable SmsSender... smsSenders) throws SmsSenderAlreadyExistException {
        if (smsSenders == null) {
            return;
        }
        for (SmsSender smsSender : smsSenders) {
            String id = smsSender.id();
            boolean exist = SENDER_HOLDER.containsKey(id);
            if (exist) {
                throw new SmsSenderAlreadyExistException(String.format("senderId[%s] already exist.", id));
            }
            SENDER_HOLDER.put(id, smsSender);
        }
    }
    
    @Override
    public void remove(@Nullable String senderId) {
        if (senderId == null) {
            return;
        }
        SENDER_HOLDER.remove(senderId);
    }
    
    @Override
    public void remove(@Nullable String... senderIds) {
        if (senderIds == null) {
            return;
        }
        for (String senderId : senderIds) {
            SENDER_HOLDER.remove(senderId);
        }
    }
}
