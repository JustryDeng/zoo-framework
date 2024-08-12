package com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.executor;

import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties.ZooReqrespEncdecProperties;
import org.springframework.core.Ordered;

/**
 * 请求解密执行器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface ReqDecryptExecutor<T> extends Ordered {
    
    /**
     * 获取解密配置
     *
     * @return  解密配置
     */
    ZooReqrespEncdecProperties.ReqDecryptProperties getDecryptProperties();
    
    /**
     * 解密
     *
     * @param requestData 要解密的请求数据
     *
     * @return 明文
     */
    T decrypt(T requestData);
}