package com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.executor;

import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties.ZooReqrespEncdecProperties;

import javax.annotation.Nonnull;

public abstract class AbstractRespEncryptExecutor implements RespEncryptExecutor<String> {
    
    @Nonnull
    private final ZooReqrespEncdecProperties zooReqrespEncdecProperties;
    
    public AbstractRespEncryptExecutor(ZooReqrespEncdecProperties zooReqrespEncdecProperties) {
        this.zooReqrespEncdecProperties = zooReqrespEncdecProperties;
    }
    
    /**
     * 获取加密相关参数
     */
    @Nonnull
    public ZooReqrespEncdecProperties.RespEncryptProperties getEncryptProperties() {
        return zooReqrespEncdecProperties.getRespEncrypt();
    }
    
    @Override
    public int getOrder() {
        return zooReqrespEncdecProperties.getRespEncrypt().getOrder();
    }
}
