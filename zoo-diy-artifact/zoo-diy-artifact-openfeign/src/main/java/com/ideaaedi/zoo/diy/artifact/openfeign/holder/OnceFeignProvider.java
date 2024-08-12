package com.ideaaedi.zoo.diy.artifact.openfeign.holder;

import com.ideaaedi.commonds.function.NoArgConsumer;
import com.ideaaedi.commonds.function.NoArgFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 临时 feign http请求 url header
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public final class OnceFeignProvider {
    
    /**
     * 执行并返回结果
     *
     * @see OnceFeignProvider#exec(NoArgFunction, OnceFeignInfo)
     */
    public static <R> R exec(@Nonnull NoArgFunction<R> function, @Nonnull String urlPrefix) {
        return OnceFeignProvider.exec(function, OnceFeignInfo.of(urlPrefix));
    }
    
    /**
     * 执行
     *
     * @see OnceFeignProvider#voidExec(NoArgConsumer, OnceFeignInfo)
     */
    public static void voidExec(@Nonnull NoArgConsumer consumer, @Nonnull String urlPrefix) {
        OnceFeignProvider.voidExec(consumer, OnceFeignInfo.of(urlPrefix));
    }
    
    
    /**
     * 执行并返回结果
     *
     * @see OnceFeignProvider#exec(NoArgFunction, OnceFeignInfo)
     */
    public static <R> R exec(@Nonnull NoArgFunction<R> function, @Nonnull String urlPrefix,
                             @Nullable Map<String, Object> headers) {
        return OnceFeignProvider.exec(function, OnceFeignInfo.of(urlPrefix, headers));
    }
    
    /**
     * 执行
     *
     * @see OnceFeignProvider#voidExec(NoArgConsumer, OnceFeignInfo)
     */
    public static void voidExec(@Nonnull NoArgConsumer consumer, @Nonnull String urlPrefix,
                                @Nullable Map<String, Object> headers) {
        OnceFeignProvider.voidExec(consumer, OnceFeignInfo.of(urlPrefix, headers));
    }
    
    /**
     * 执行并返回结果
     *
     * @param function 代码逻辑
     * @param onceFeignInfo feign信息
     */
    public static <R> R exec(@Nonnull NoArgFunction<R> function, @Nonnull OnceFeignInfo onceFeignInfo) {
        try {
            OnceFeignInfoHolder.TEMP_FEIGN_HOLDER.get().addFirst(onceFeignInfo);
            return function.apply();
        } finally {
            ConcurrentLinkedDeque<OnceFeignInfo> deque = OnceFeignInfoHolder.TEMP_FEIGN_HOLDER.get();
            if (deque.size() > 0) {
                deque.pollFirst();
                if (deque.size() == 0) {
                    OnceFeignInfoHolder.TEMP_FEIGN_HOLDER.remove();
                }
            }
        }
    }
    
    /**
     * 执行
     *
     * @param consumer 代码逻辑
     * @param onceFeignInfo feign信息
     */
    public static void voidExec(@Nonnull NoArgConsumer consumer, @Nonnull OnceFeignInfo onceFeignInfo) {
        try {
            OnceFeignInfoHolder.TEMP_FEIGN_HOLDER.get().addFirst(onceFeignInfo);
            consumer.accept();
        } finally {
            ConcurrentLinkedDeque<OnceFeignInfo> deque = OnceFeignInfoHolder.TEMP_FEIGN_HOLDER.get();
            if (deque.size() > 0) {
                deque.pollFirst();
                if (deque.size() == 0) {
                    OnceFeignInfoHolder.TEMP_FEIGN_HOLDER.remove();
                }
            }
        }
    }
    
}
