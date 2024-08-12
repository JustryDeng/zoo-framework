package com.ideaaedi.zoo.diy.artifact.sse.core;

import com.ideaaedi.zoo.commonbase.zoo_support.AppInstanceIdSupport;
import com.ideaaedi.zoo.diy.artifact.sse.distribute.DistributedSseMsgConsumer;
import com.ideaaedi.zoo.diy.artifact.sse.distribute.DistributedSseMsgDTO;
import com.ideaaedi.zoo.diy.artifact.sse.distribute.DistributedSseMsgProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Sse消息推送
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class SsePusher implements DistributedSseMsgConsumer, SmartInitializingSingleton, Runnable {
    
    private static final long DEFAULT_TIMEOUT_DAY1 = 24 * 60 * 60 * 1000L;
    
    private static final long DEFAULT_RECONNECT_TIME_SECONDS15 = 15 * 1000L;
    
    /**
     * <pre>
     * key - SseEmitter唯一key
     * value - SseEmitter
     * </pre>
     */
    protected static final Map<String, SseEmitter> CLIENT_EMITTER_MAP = new ConcurrentHashMap<>(64);
    
    private final DistributedSseMsgProvider distributedSseMsgProvider;
    
    public SsePusher(DistributedSseMsgProvider distributedSseMsgProvider) {
        this.distributedSseMsgProvider = distributedSseMsgProvider;
    }
    
    /**
     * 创建sse连接
     *
     * @see SsePusher#connect(String, Long, long)
     */
    public SseEmitter connect(String emitterUuid) {
        return connect(emitterUuid, DEFAULT_TIMEOUT_DAY1, DEFAULT_RECONNECT_TIME_SECONDS15);
    }
    
    /**
     * 创建sse连接
     *
     * @see SsePusher#connect(String, Long, long)
     */
    public SseEmitter connect(String emitterUuid, Long timeout) {
        return connect(emitterUuid, timeout, DEFAULT_RECONNECT_TIME_SECONDS15);
    }
    
    /**
     * 创建sse连接
     *
     * @param emitterUuid sseEmitter的唯一key<p>
     * @param timeout sse超时时间（单位毫秒）
     * <p>注：0表示永不超时；不设置则自动取spring.mvc.async.request-timeout值(默认是 30000ms)，代码详见{@link AsyncSupportConfigurer#setDefaultTimeout(long)}</p>
     * <p>注：从创建时间开始算，如果超过指定的时间sse还未结束，那么会进入onTimeout回调</p>
     * @param reconnectTimeMillis 客户端重连时间（单位毫秒）
     * <p>注：客户端收到reconnectTime后，如果遇到网络或其他问题导致连接断开，客户端会根据这个时间延迟其；
     * 下一次连接尝试。这样可以帮助控制客户端重新连接的频率，避免因频繁重试而对服务器造成不必要的压力
     * </p>
     */
    public SseEmitter connect(String emitterUuid, Long timeout, long reconnectTimeMillis) {
        SseEmitter sseEmitter = timeout == null ? new SseEmitterUTF8() : new SseEmitterUTF8(timeout);
        // 完成后回调
        sseEmitter.onCompletion(() -> {
            log.info("sse completed. emitter-uuid -> {}.", emitterUuid);
            CLIENT_EMITTER_MAP.remove(emitterUuid);
        });
        // 超时回调
        sseEmitter.onTimeout(() -> {
            log.info("sse timeout. emitter-uuid -> {}.", emitterUuid);
            CLIENT_EMITTER_MAP.remove(emitterUuid);
        });
        // 异常回调
        sseEmitter.onError(e -> {
            log.warn("sse error. emitter-uuid -> {}. e.getMessage() -> {}", emitterUuid, e.getMessage());
            CLIENT_EMITTER_MAP.remove(emitterUuid);
        });
        /*
         * 设置客户端重连时间
         *
         * 客户端收到reconnectTime后，如果遇到网络或其他问题导致连接断开，客户端会根据这个时间延迟其
         * 下一次连接尝试。这样可以帮助控制客户端重新连接的频率，避免因频繁重试而对服务器造成不必要的压力
         */
        try {
            sseEmitter.send(SseEmitter.event().reconnectTime(reconnectTimeMillis));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CLIENT_EMITTER_MAP.put(emitterUuid, sseEmitter);
        return sseEmitter;
    }
    
    /**
     * 关闭sse
     *
     * @param emitterUuid sseEmitter的唯一key
     * @param throwable 异常原因
     */
    public void disconnect(@Nonnull String emitterUuid, @Nullable Throwable throwable) {
        SseEmitter sseEmitter = CLIENT_EMITTER_MAP.get(emitterUuid);
        if (sseEmitter == null) {
            return;
        }
        if (throwable != null) {
            sseEmitter.completeWithError(throwable);
        } else {
            sseEmitter.complete();
        }
        CLIENT_EMITTER_MAP.remove(emitterUuid);
    }
    
    /**
     * 发送消息
     *
     * @param emitterUuid sseEmitter的唯一key
     * @param msg 消息内容
     */
    public void sendMessage(@Nonnull String emitterUuid, @Nonnull String msg) {
        SseEmitter sseEmitter = CLIENT_EMITTER_MAP.get(emitterUuid);
        if (sseEmitter != null) {
            try {
                sseEmitter.send(SseEmitter.event().data(msg));
                log.info("send sse msg '{}' to '{}'.", msg, emitterUuid);
            } catch (Throwable e) {
                log.warn("send sse msg occur exception. msg '{}' to '{}', e.getMessage() -> {}", msg, emitterUuid, e.getMessage());
                disconnect(emitterUuid, e);
            }
            return;
        }
        if (distributedSseMsgProvider != null) {
            distributedSseMsgProvider.provide(
                    DistributedSseMsgDTO.builder()
                            .sender(appInstanceId())
                            .emitterUuid(emitterUuid)
                            .msg(msg)
                            .build()
            );
        }
    }
    
    /**
     * 向本地的指定sseEmitter推送消息
     *
     * @param emitterUuid sseEmitter的唯一key
     * @param msg 消息内容
     */
    public void sendMessageLocal(@Nonnull String emitterUuid, @Nonnull String msg) {
        SseEmitter sseEmitter = CLIENT_EMITTER_MAP.get(emitterUuid);
        if (sseEmitter == null) {
            return;
        }
        try {
            sseEmitter.send(SseEmitter.event().data(msg));
            log.info("send sse msg '{}' to '{}'.", msg, emitterUuid);
        } catch (Throwable e) {
            log.warn("send sse msg occur exception. msg '{}' to '{}', e.getMessage() -> {}", msg, emitterUuid, e.getMessage());
            disconnect(emitterUuid, e);
        }
    }
    
    @Override
    public void consume(String sender, @Nonnull String emitterUuid, @Nonnull String message) {
        // 如果这条消息就是自己找不到对应emitter，才广播出去的，那这里就不需要再消费消息了
        if (StringUtils.equals(sender, appInstanceId())) {
            return;
        }
        sendMessageLocal(emitterUuid, message);
    }
    
    /**
     * 检测客户端是否还"活着"
     */
    @Override
    public void run() {
        while (true) {
            CLIENT_EMITTER_MAP.forEach((emitterUuid, sseEmitter) -> {
                try {
                    sseEmitter.send(SseEmitter.event().comment("PING"));
                } catch (Throwable e) {
                    disconnect(emitterUuid, e);
                }
            });
            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @Override
    public void afterSingletonsInstantiated() {
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }
    
    /**
     * 获取当前应用实例id
     */
    public String appInstanceId() {
        return AppInstanceIdSupport.APP_INSTANCE_ID;
    }
}
