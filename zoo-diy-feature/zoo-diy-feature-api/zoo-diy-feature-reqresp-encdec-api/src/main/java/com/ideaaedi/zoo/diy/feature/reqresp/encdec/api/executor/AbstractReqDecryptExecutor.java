package com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.executor;

import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties.ZooReqrespEncdecProperties;

import javax.annotation.Nonnull;

public abstract class AbstractReqDecryptExecutor implements ReqDecryptExecutor<String> {
    
    @Nonnull
    private final ZooReqrespEncdecProperties zooReqrespEncdecProperties;
    
    public AbstractReqDecryptExecutor(ZooReqrespEncdecProperties zooReqrespEncdecProperties) {
        this.zooReqrespEncdecProperties = zooReqrespEncdecProperties;
    }
    
    /**
     * 获取解密相关参数
     */
    @Nonnull
    @Override
    public ZooReqrespEncdecProperties.ReqDecryptProperties getDecryptProperties() {
        return zooReqrespEncdecProperties.getReqDecrypt();
    }
    
    @Override
    public int getOrder() {
        return zooReqrespEncdecProperties.getReqDecrypt().getOrder();
    }
}
