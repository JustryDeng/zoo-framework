package com.ideaaedi.zoo.diy.artifact.sse;

import com.ideaaedi.zoo.diy.artifact.sse.core.SsePusher;
import com.ideaaedi.zoo.diy.artifact.sse.distribute.DistributeSseMsgConfig;
import com.ideaaedi.zoo.diy.artifact.sse.distribute.DistributedSseMsgProvider;
import com.ideaaedi.zoo.diy.artifact.sse.properties.ZooSseDIYGuideProperties;
import com.ideaaedi.zoo.diy.artifact.sse.properties.ZooSseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * sse 自动配置
 */
@ImportAutoConfiguration(DistributeSseMsgConfig.class)
@EnableConfigurationProperties({ZooSseProperties.class, ZooSseDIYGuideProperties.class})
public class ZooSseAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SsePusher ssePusher(@Autowired(required = false) DistributedSseMsgProvider distributedSseMsgProvider) {
        return new SsePusher(distributedSseMsgProvider);
    }
}