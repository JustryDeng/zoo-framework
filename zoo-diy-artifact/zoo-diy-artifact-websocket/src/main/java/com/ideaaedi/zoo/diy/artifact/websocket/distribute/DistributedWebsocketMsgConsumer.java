package com.ideaaedi.zoo.diy.artifact.websocket.distribute;

import javax.annotation.Nonnull;

/**
 * 分布式websocket消息消费者
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface DistributedWebsocketMsgConsumer {
    
    /**
     * 消费消息
     *
     * @param sender 发送方
     * @param sessionUuid 对应session的唯一key
     * @param message message消息内容
     */
    void consume(String sender,  @Nonnull String sessionUuid, @Nonnull String message);
}
