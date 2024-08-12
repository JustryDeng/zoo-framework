package com.ideaaedi.zoo.diy.artifact.sse.distribute;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.ideaaedi.zoo.diy.artifact.sse.core.SsePusher;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@AutoConfiguration
@ConditionalOnProperty(value = "spring.data.redis.host")
@ConditionalOnMissingBean(name = DistributeSseMsgConfig.BEAN_NAME)
public class DistributeSseMsgConfig {
    
    static final String BEAN_NAME = "com.ideaaedi.zoo.diy.artifact.sse.distribute.DistributeSseMsgConfig";
    
    @Bean
    @ConditionalOnMissingBean
    public RedisMessageListenerContainer distributedSseMsgListenerContainer(RedisConnectionFactory connectionFactory,
                                                                            SsePusher ssePusher) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(
                new MessageListenerAdapter(new DistributeSseMsgListener(ssePusher), "onMessage"),
                new PatternTopic(DistributedSseMsgProvider.REDIS_TOPIC_SSE_MSG_TO_SEND_NOTIFY)
        );
        return container;
    }
    
    @Bean
    @ConditionalOnMissingBean
    public DistributedSseMsgProvider distributedSseMsgProvider(StringRedisTemplate stringRedisTemplate) {
        return distributedSseMsg -> stringRedisTemplate.convertAndSend(
                DistributedSseMsgProvider.REDIS_TOPIC_SSE_MSG_TO_SEND_NOTIFY,
                JSON.toJSONString(distributedSseMsg, JSONWriter.Feature.NotWriteDefaultValue)
        );
    }
}
