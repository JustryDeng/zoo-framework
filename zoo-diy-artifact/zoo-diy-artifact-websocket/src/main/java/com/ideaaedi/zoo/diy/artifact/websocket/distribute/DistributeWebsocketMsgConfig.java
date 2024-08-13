package com.ideaaedi.zoo.diy.artifact.websocket.distribute;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.List;
import java.util.Optional;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnProperty(value = "spring.data.redis.host")
@ConditionalOnMissingBean(name = DistributeWebsocketMsgConfig.BEAN_NAME)
public class DistributeWebsocketMsgConfig {
    
    static final String BEAN_NAME = "com.ideaaedi.zoo.diy.artifact.websocket.distribute.DistributeWebsocketMsgConfig";
    
    @Bean
    @ConditionalOnMissingBean
    public RedisMessageListenerContainer distributedWebsocketMsgListenerContainer(RedisConnectionFactory connectionFactory,
                                                                                  Optional<List<DistributedWebsocketMsgConsumer>> distributedMsgDeliveryNotifyOptional) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        distributedMsgDeliveryNotifyOptional.ifPresent(list -> {
            container.addMessageListener(
                    new MessageListenerAdapter(new DistributeWebsocketMsgListener(list), "onMessage"),
                    new PatternTopic(DistributedWebsocketMsgProvider.REDIS_TOPIC_WEBSOCKET_MSG_TO_SEND_NOTIFY)
            );
        });
        return container;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public DistributedWebsocketMsgProvider distributedWebsocketMsgProvider(StringRedisTemplate stringRedisTemplate) {
        return distributedWebsocketMsg -> stringRedisTemplate.convertAndSend(
                DistributedWebsocketMsgProvider.REDIS_TOPIC_WEBSOCKET_MSG_TO_SEND_NOTIFY,
                JSON.toJSONString(distributedWebsocketMsg, JSONWriter.Feature.NotWriteDefaultValue)
        );
    }
}
