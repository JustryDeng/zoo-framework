package com.ideaaedi.zoo.diy.feature.litefeature.api;

import com.ideaaedi.zoo.diy.feature.litefeature.api.face.LockFaceUtilInitializer;
import com.ideaaedi.zoo.diy.feature.litefeature.api.lock.DefaultLockSupplierManager;
import com.ideaaedi.zoo.diy.feature.litefeature.api.lock.LockSupplier;
import com.ideaaedi.zoo.diy.feature.litefeature.api.lock.LockSupplierManager;
import com.ideaaedi.zoo.diy.feature.litefeature.api.properties.ZooLiteFeatureApiDIYGuideProperties;
import com.ideaaedi.zoo.diy.feature.litefeature.api.properties.ZooLiteFeatureProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 小功能 自动配置
 */
@EnableConfigurationProperties({ZooLiteFeatureApiDIYGuideProperties.class, ZooLiteFeatureProperties.class})
public class ZooLiteFeatureApiAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean(LockSupplierManager.class)
    public LockSupplierManager lockSupplierManager(List<LockSupplier> lockSupplierList) {
        return new DefaultLockSupplierManager(lockSupplierList);
    }
    
    @Bean
    @ConditionalOnMissingBean
    public LockFaceUtilInitializer smsFaceUtilInitializer(LockSupplierManager lockSupplierManager, ZooLiteFeatureProperties liteFeatureProperties) {
        return new LockFaceUtilInitializer(lockSupplierManager, liteFeatureProperties);
    }
}