package com.ideaaedi.zoo.diy.feature.reqresp.encdec.impl.springmvc;

import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties.ZooReqrespEncdecProperties;
import com.ideaaedi.zoo.diy.feature.reqresp.encdec.impl.springmvc.core.RequestBodyDecryptFilter;
import com.ideaaedi.zoo.diy.feature.reqresp.encdec.impl.springmvc.core.ResponseBodyEncryptExecutor;
import com.ideaaedi.zoo.diy.feature.reqresp.encdec.impl.springmvc.properties.ZooReByBodyAdviceDIYGuideProperties;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * 请求解密，响应加密 自动配置
 */
@EnableConfigurationProperties({ZooReByBodyAdviceDIYGuideProperties.class})
public class ZooReByBodyAdviceAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "zoo.req-resp-enc-dec.req-decrypt.enable", havingValue = "true")
    public FilterRegistrationBean<Filter> requestBodyDecryptFilter(ZooReqrespEncdecProperties encdecProperties) {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        RequestBodyDecryptFilter filter = new RequestBodyDecryptFilter(encdecProperties);
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(filter.getOrder());
        return registrationBean;
    }
    
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(value = "zoo.req-resp-enc-dec.resp-encrypt.enable", havingValue = "true")
    public ResponseBodyEncryptExecutor responseBodyEncryptExecutor(ZooReqrespEncdecProperties encdecProperties) {
        return new ResponseBodyEncryptExecutor(encdecProperties);
    }
    
}