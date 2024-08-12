package com.ideaaedi.zoo.diy.artifact.sse.core;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;

/**
 * UTF8字符支持
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class SseEmitterUTF8 extends SseEmitter {
    
    public SseEmitterUTF8() {
        super();
    }
    
    public SseEmitterUTF8(Long timeout) {
        super(timeout);
    }
    
    @Override
    protected void extendResponse(@Nonnull ServerHttpResponse outputMessage) {
        super.extendResponse(outputMessage);
        HttpHeaders headers = outputMessage.getHeaders();
        headers.setContentType(new MediaType(MediaType.TEXT_EVENT_STREAM, StandardCharsets.UTF_8));
    }
}



