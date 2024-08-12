package com.ideaaedi.zoo.diy.artifact.websocket.validator;

import com.ideaaedi.zoo.diy.artifact.websocket.exception.WebSocketAuthException;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * websocket token校验器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2024-07-17 00:03:37
 */
public interface WebSocketTokenValidator {
    
    /**
     * 校验websocket携带的token
     *
     * @param token websocket握手时携带的token（即：websocket子协议Sec-WebSocket-Protocol的内容）
     *
     * @return 放入到{@link Session#getUserProperties()}中的属性，以便后续获取
     *
     * @throws WebSocketAuthException websocket鉴权失败时
     */
    @Nonnull
    Map<String, Object> validate(@Nullable String token) throws WebSocketAuthException;
    
}
