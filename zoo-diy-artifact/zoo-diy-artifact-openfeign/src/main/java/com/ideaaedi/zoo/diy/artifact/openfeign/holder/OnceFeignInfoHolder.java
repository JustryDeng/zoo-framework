package com.ideaaedi.zoo.diy.artifact.openfeign.holder;

import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 本次feign调用 请求信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class OnceFeignInfoHolder {
    
    /**
     * 会话级临时数据域值
     */
    @Getter
    public static final ThreadLocal<ConcurrentLinkedDeque<OnceFeignInfo>> TEMP_FEIGN_HOLDER =
            new TransmittableThreadLocal<>() {
                @Override
                public ConcurrentLinkedDeque<OnceFeignInfo> copy(ConcurrentLinkedDeque<OnceFeignInfo> parentValue) {
                    return new ConcurrentLinkedDeque<>(
                            parentValue.stream().map(item -> {
                                if (item == null) {
                                    return null;
                                }
                                return OnceFeignInfo.of(item.getUrlPrefix(), item.getHeaders());
                            }).toList()
                    );
                }
                
                @Override
                protected ConcurrentLinkedDeque<OnceFeignInfo> initialValue() {
                    return new ConcurrentLinkedDeque<>();
                }
            };

}