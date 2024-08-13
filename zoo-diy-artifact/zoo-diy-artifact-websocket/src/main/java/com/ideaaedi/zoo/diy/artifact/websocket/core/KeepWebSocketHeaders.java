package com.ideaaedi.zoo.diy.artifact.websocket.core;

import com.ideaaedi.zoo.commonbase.zoo_injector.ExtAutowiredInjector;
import com.ideaaedi.zoo.diy.artifact.websocket.validator.WebSocketTokenValidator;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.Session;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class KeepWebSocketHeaders extends ServerEndpointConfig.Configurator {
    
    /**
     * ws自定义协议头
     */
    private static final String HEADER_NAME = "Sec-WebSocket-Protocol";
    
    @Autowired(required = false)
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private WebSocketTokenValidator webSocketTokenValidator;
    
    public KeepWebSocketHeaders() {
        ExtAutowiredInjector.inject(this);
    }
    
    /**
     * 握手时，将请求头里面的信息存入{@link ServerEndpointConfig#getUserProperties()}， 然后在使用时，从{@link Session#getUserProperties()}中取即可
     */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
        // 获取token
        String secWebSocketProtocol = null;
        Map<String, List<String>> headers = request.getHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            List<String> secWebSocketProtocolList = headers.get(HEADER_NAME);
            if (!CollectionUtils.isEmpty(secWebSocketProtocolList)) {
                secWebSocketProtocol = secWebSocketProtocolList.get(0);
                // 如果前端设置了自定义协议头Sec-WebSocket-Protocol，那么这里需要原样返回，否则会协议对接会失败
                response.getHeaders().putIfAbsent(HEADER_NAME, secWebSocketProtocolList);
            }
        }
        
        if (webSocketTokenValidator == null) {
            return;
        }
        // 校验token
        Map<String, Object> params = webSocketTokenValidator.validate(secWebSocketProtocol);
        // 将信息存入{@link ServerEndpointConfig#getUserProperties()}， 在使用时通过{@link Session#getUserProperties()}中获取
        sec.getUserProperties().putAll(params);
    }
}
