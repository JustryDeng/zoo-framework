package com.ideaaedi.zoo.diy.artifact.sse.distribute;

/**
 * 分布式sse消息提供者
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface DistributedSseMsgProvider {
    
    /**
     * 用于广播待发送SSE消息的redis主题
     */
    String REDIS_TOPIC_SSE_MSG_TO_SEND_NOTIFY = "REDIS_TOPIC_SSE_MSG_TO_SEND_NOTIFY";
    
    /**
     * 提供消息
     *
     * @param distributedSseMsg 消息
     */
    void provide(DistributedSseMsgDTO distributedSseMsg);
}
