package com.ideaaedi.zoo.diy.artifact.auth.satoken.helper;


import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt token负载工具
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public final class ZooJwtTokenPayloadAppender {
    
    private static final ThreadLocal<Map<String, Object>> contextThreadLocal = TransmittableThreadLocal.withInitial(HashMap::new);
    
    /*
     * 获取当前线程的上下文
     *
     * @return 当前线程的上下文
     */
    @Nonnull
    public static Map<String, Object> context() {
        return contextThreadLocal.get();
    }
    
    /**
     * 清除所有
     */
    @Nullable
    public static void clear() {
        context().clear();
    }
    
    /*
     * 添加一个键值对到当前线程的上下文中
     */
    public static void append(String key, Object value) {
        context().put(key, value);
    }
}