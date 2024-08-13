package com.ideaaedi.zoo.diy.artifact.websocket.distribute;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * redis中待发送的ws消息 监听器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class DistributeWebsocketMsgListener implements MessageListener {
    
    private final List<DistributedWebsocketMsgConsumer> distributedWebsocketMsgConsumerList;
    
    public DistributeWebsocketMsgListener(List<DistributedWebsocketMsgConsumer> distributedWebsocketMsgConsumerList) {
        this.distributedWebsocketMsgConsumerList = distributedWebsocketMsgConsumerList;
    }
    
    @Override
    public void onMessage(@Nonnull Message message, byte[] pattern) {
        if (CollectionUtils.isEmpty(distributedWebsocketMsgConsumerList)) {
            return;
        }
        byte[] body = message.getBody();
        if (body.length == 0) {
            return;
        }
        String topic = new String(message.getChannel());
        String listenMsg = new String(body, StandardCharsets.UTF_8);
        DistributedWebsocketMsgDTO redisToSendMsg = JSON.parseObject(listenMsg, DistributedWebsocketMsgDTO.class);
        if (redisToSendMsg == null) {
            return;
        }
        log.info("listen redis websocket msg {} from topic {}.", listenMsg, topic);
        String sessionUuid = redisToSendMsg.getSessionUuid();
        String msg = redisToSendMsg.getMsg();
        String sender = redisToSendMsg.getSender();
        if (StringUtils.isAnyBlank(sessionUuid, msg)) {
            return;
        }
        for (DistributedWebsocketMsgConsumer distributedWebsocketMsgConsumer : distributedWebsocketMsgConsumerList) {
            distributedWebsocketMsgConsumer.consume(sender, sessionUuid, msg);
        }
    }
}

