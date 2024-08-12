package com.ideaaedi.zoo.diy.feature.litefeature.impl.cs;

import com.ideaaedi.zoo.diy.feature.litefeature.impl.cs.lock.CsLockSupplier;
import com.ideaaedi.zoo.diy.feature.litefeature.impl.cs.properties.ZooLiteFeatureCsDIYGuideProperties;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * 小功能 自动配置
 */
@EnableConfigurationProperties(ZooLiteFeatureCsDIYGuideProperties.class)
public class ZooLiteFeatureCsAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    public CsLockSupplier csLockSupplier(@Autowired(required = false) RedissonClient redissonClient) {
        return new CsLockSupplier(redissonClient);
    }
}