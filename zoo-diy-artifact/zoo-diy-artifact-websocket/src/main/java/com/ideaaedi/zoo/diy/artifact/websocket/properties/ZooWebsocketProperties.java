package com.ideaaedi.zoo.diy.artifact.websocket.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * websocket相关配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "zoo.websocket")
public class ZooWebsocketProperties {
    
    /**
     * 限制文本消息的大小。单位：字节(B)
     * <p>
     * 如果接收到的消息超过这个大小，服务器可能会拒绝该消息或者抛出异常
     */
    private Long maxSessionIdleTimeout;
    
    /**
     * 限制二进制消息的大小。单位：字节(B)
     * <p>
     * 如果接收到的消息超过这个大小，服务器可能会拒绝该消息或者抛出异常
     */
    private Integer maxTextMessageBufferSize;
    
    /**
     * 设置会话的最大空闲超时时长。单位：毫秒
     * <pre>
     * 注：如果设置为0或负数，则表示没有空闲超时限制，连接将一直保持打开状态直到显式关闭
     * 注：不设置，默认为0
     * </pre>
     */
    private Integer maxBinaryMessageBufferSize;
}
