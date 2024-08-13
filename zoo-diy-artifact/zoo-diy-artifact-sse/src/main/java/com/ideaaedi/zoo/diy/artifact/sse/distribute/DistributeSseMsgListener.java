package com.ideaaedi.zoo.diy.artifact.sse.distribute;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

/**
 * redis中待发送的sse消息 监听器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class DistributeSseMsgListener implements MessageListener {
    
    private final DistributedSseMsgConsumer distributedSseMsgConsumer;
    
    public DistributeSseMsgListener(DistributedSseMsgConsumer distributedSseMsgConsumer) {
        this.distributedSseMsgConsumer = distributedSseMsgConsumer;
    }
    
    @Override
    public void onMessage(@Nonnull Message message, byte[] pattern) {
        if (distributedSseMsgConsumer == null) {
            return;
        }
        byte[] body = message.getBody();
        if (body.length == 0) {
            return;
        }
        String topic = new String(message.getChannel());
        String listenMsg = new String(body, StandardCharsets.UTF_8);
        DistributedSseMsgDTO redisToSendMsg = JSON.parseObject(listenMsg, DistributedSseMsgDTO.class);
        if (redisToSendMsg == null) {
            return;
        }
        log.info("listen redis sse msg {} from topic {}.", listenMsg, topic);
        String emitterUuid = redisToSendMsg.getEmitterUuid();
        String msg = redisToSendMsg.getMsg();
        String sender = redisToSendMsg.getSender();
        if (StringUtils.isAnyBlank(emitterUuid, msg)) {
            return;
        }
        distributedSseMsgConsumer.consume(sender, emitterUuid, msg);
    }
}

