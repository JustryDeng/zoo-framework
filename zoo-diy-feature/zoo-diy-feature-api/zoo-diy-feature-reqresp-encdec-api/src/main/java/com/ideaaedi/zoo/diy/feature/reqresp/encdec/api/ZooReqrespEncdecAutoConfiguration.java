package com.ideaaedi.zoo.diy.feature.reqresp.encdec.api;

import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties.ZooReqrespEncdecProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 请求响应加解密 自动配置
 */
@Slf4j
@EnableConfigurationProperties(ZooReqrespEncdecProperties.class)
public class ZooReqrespEncdecAutoConfiguration {

}