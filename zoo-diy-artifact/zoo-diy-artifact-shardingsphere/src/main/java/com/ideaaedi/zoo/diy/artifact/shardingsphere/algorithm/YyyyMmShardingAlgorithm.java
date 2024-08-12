package com.ideaaedi.zoo.diy.artifact.shardingsphere.algorithm;

import lombok.extern.slf4j.Slf4j;

/**
 * 按yyyyMM分表
 *
 * @author JustryDeng
 * @since 1.0.0
 */
@Slf4j
public class YyyyMmShardingAlgorithm extends DateShardingAlgorithm {
    
    /**
     * spi注册
     */
    @Override
    public String getType() {
        // 同时，需要在resource下的/META-INF/services/下创建org.apache.shardingsphere.sharding.apiinfo.sharding.standard.StandardShardingAlgorithm文件，并指定当前类为其SPI实现
        return "yyyyMM_SHARDING";
    }
    
    @Override
    String suffix(String shardingValue) {
        final String[] arr = shardingValue.split("-");
        return "_" + arr[0] + arr[1];
    }
}
