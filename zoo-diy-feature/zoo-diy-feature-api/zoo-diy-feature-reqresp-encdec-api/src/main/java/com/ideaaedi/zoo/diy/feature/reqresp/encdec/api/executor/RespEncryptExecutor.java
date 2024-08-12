package com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.executor;

import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties.ZooReqrespEncdecProperties;
import org.springframework.core.Ordered;

/**
 * 响应加密执行器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface RespEncryptExecutor<T> extends Ordered {
    
    /**
     * 获取加密配置
     *
     * @return  加密配置
     */
    ZooReqrespEncdecProperties.RespEncryptProperties getEncryptProperties();
    
    /**
     * 加密
     *
     * @param responseData 要加密的响应数据
     *
     * @return 密文
     */
    T encrypt(T responseData);
}