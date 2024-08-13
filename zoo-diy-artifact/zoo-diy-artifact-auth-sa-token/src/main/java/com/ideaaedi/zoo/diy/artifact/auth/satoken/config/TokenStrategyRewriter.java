package com.ideaaedi.zoo.diy.artifact.auth.satoken.config;

import cn.dev33.satoken.strategy.SaStrategy;
import cn.hutool.jwt.JWTUtil;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.helper.ZooJwtTokenPayloadAppender;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.properties.ZooAuthProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义token生成策略
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class TokenStrategyRewriter {
    
    @Resource
    private ZooAuthProperties zooAuthProperties;
    
    @PostConstruct
    public void rewrite() {
        ZooAuthProperties.ZooAuthToken token = zooAuthProperties.getToken();
        SaStrategy.instance.createToken = (loginId, loginType) -> {
            Map<String, Object> payloadMap = new HashMap<>(ZooJwtTokenPayloadAppender.context());
            payloadMap.put("loginId", loginId);
            payloadMap.put("loginType", loginType);
            return JWTUtil.createToken(payloadMap, token.getJwtSecret().getBytes(StandardCharsets.UTF_8)
            );
        };
    }

}
