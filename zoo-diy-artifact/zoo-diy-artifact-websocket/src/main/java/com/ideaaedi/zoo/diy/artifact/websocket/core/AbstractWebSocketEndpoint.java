package com.ideaaedi.zoo.diy.artifact.websocket.core;

import com.alibaba.fastjson2.JSON;
import com.ideaaedi.zoo.commonbase.zoo_injector.ExtAutowiredInjector;
import com.ideaaedi.zoo.commonbase.zoo_support.AppInstanceIdSupport;
import com.ideaaedi.zoo.diy.artifact.websocket.distribute.DistributedWebsocketMsgConsumer;
import com.ideaaedi.zoo.diy.artifact.websocket.distribute.DistributedWebsocketMsgDTO;
import com.ideaaedi.zoo.diy.artifact.websocket.distribute.DistributedWebsocketMsgProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nonnull;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public abstract class AbstractWebSocketEndpoint extends ExtAutowiredInjector implements DistributedWebsocketMsgConsumer {
    
    /**
     * <pre>
     * key - session唯一key， {@link AbstractWebSocketEndpoint#sessionUuid(Session)}
     * value - session
     * </pre>
     */
    protected static final Map<String, Session> CLIENT_SESSION_MAP = new ConcurrentHashMap<>(64);
    
    @Autowired(required = false)
    private DistributedWebsocketMsgProvider distributedWebsocketMsgProvider;
    
    /**
     * 获取与session对应的唯一id
     */
    @Nonnull
    protected abstract String sessionUuid(@Nonnull Session session);
    
    /**
     * 服务端收到消息
     */
    @OnMessage
    public abstract void onMessage(String message, @Nonnull Session session);
    
    /**
     * 获取当前应用实例id
     */
    public String appInstanceId() {
        return AppInstanceIdSupport.APP_INSTANCE_ID;
    }
    
    /**
     * 连接建立
     */
    @OnOpen
    public void onOpen(@Nonnull Session session) {
        Map<String, Object> userProperties = session.getUserProperties();
        log.info("session created. path -> {}, requestParameterMap -> {}, userProperties -> {}",
                session.getRequestURI().getPath(),
                session.getRequestParameterMap(),  // 获取到url中的参数：ws://www.idea-aedi.com/ws/{k0}?k1=v1&k2=v2
                JSON.toJSONString(userProperties));
        String sessionUuid = sessionUuid(session);
        // 当前连接已存在，关闭
        if (CLIENT_SESSION_MAP.containsKey(sessionUuid)) {
            try {
                CLIENT_SESSION_MAP.get(sessionUuid).close();
            } catch (Exception e) {
                log.warn("close session exception. e.getMessage() -> {}.", e.getMessage());
            }
        }
        CLIENT_SESSION_MAP.put(sessionUuid, session);
    }
    
    /**
     * 连接关闭
     */
    @OnClose
    public void onClose(Session session) {
        String sessionUuid = sessionUuid(session);
        log.info("session removed. sessionUuid -> {}, path -> {}", sessionUuid, session.getRequestURI().getPath());
        CLIENT_SESSION_MAP.remove(sessionUuid);
    }
    
    /**
     * 发生错误时
     */
    @OnError
    public void onError(Session session, Throwable e) {
        String sessionUuid = null;
        try {
            sessionUuid = sessionUuid(session);
        } catch (Exception ignore) {
        }
        log.error("websocket occur exception. sessionUuid -> {}, path -> {}, requestParameterMap -> {}",
                sessionUuid, session.getRequestURI().getPath(),
                session.getRequestParameterMap(), e);
    }
    
    @Override
    public void consume(String sender, @Nonnull String sessionUuid, @Nonnull String message) {
        // 如果这条消息就是自己找不到对应session，才广播出去的，那这里就不需要再消费消息了
        if (StringUtils.equals(sender, appInstanceId())) {
            return;
        }
        sendMessageLocal(sessionUuid, message);
    }
    
    /**
     * 向指定session推送文本消息
     */
    public void sendMessage(Session session, String message) {
        String sessionUuid = null;
        try {
            sessionUuid = sessionUuid(session);
        } catch (Exception ignore) {
        }
        try {
            session.getAsyncRemote().sendText(message);
            log.info("send message '{}' to '{}' success.", message, sessionUuid);
        } catch (Exception e) {
            log.error("send message '{}' to '{}' fail. ", message, sessionUuid, e);
        }
    }
    
    /**
     * 向指定session推送二进制消息
     */
    public void sendMessage(Session session, ByteBuffer messageByteBuffer) {
        String sessionUuid = null;
        try {
            sessionUuid = sessionUuid(session);
        } catch (Exception ignore) {
        }
        try {
            session.getAsyncRemote().sendBinary(messageByteBuffer);
            log.info("send binary-message to '{}' success.", sessionUuid);
        } catch (Exception e) {
            log.info("send binary-message to '{}' fail.", sessionUuid, e);
        }
    }
    
    /**
     * 向指定session推送消息
     *
     * @param sessionUuid session唯一key
     * @param message 消息内容
     */
    public void sendMessage(String sessionUuid, String message) {
        Session session = CLIENT_SESSION_MAP.get(sessionUuid);
        if (session != null) {
            sendMessage(session, message);
            return;
        }
        if (distributedWebsocketMsgProvider != null) {
            distributedWebsocketMsgProvider.provide(
                    DistributedWebsocketMsgDTO.builder()
                            .sender(appInstanceId())
                            .sessionUuid(sessionUuid)
                            .msg(message)
                            .build()
            );
        }
    }
    
    /**
     * 向本地的指定session推送消息
     *
     * @param sessionUuid session唯一key
     * @param message 消息内容
     */
    public void sendMessageLocal(String sessionUuid, @Nonnull String message) {
        Session session = CLIENT_SESSION_MAP.get(sessionUuid);
        if (session == null) {
            return;
        }
        sendMessage(session, message);
    }
    
}
