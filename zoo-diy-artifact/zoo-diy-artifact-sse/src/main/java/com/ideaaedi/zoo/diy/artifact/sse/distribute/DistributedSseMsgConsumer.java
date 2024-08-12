package com.ideaaedi.zoo.diy.artifact.sse.distribute;

import javax.annotation.Nonnull;

/**
 * 分布式sse消息消费者
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface DistributedSseMsgConsumer {
    
    /**
     * 消费消息
     *
     * @param sender 发送方
     * @param emitterUuid 对应sse emitter的唯一key
     * @param message message消息内容
     */
    void consume(String sender,  @Nonnull String emitterUuid, @Nonnull String message);
}
