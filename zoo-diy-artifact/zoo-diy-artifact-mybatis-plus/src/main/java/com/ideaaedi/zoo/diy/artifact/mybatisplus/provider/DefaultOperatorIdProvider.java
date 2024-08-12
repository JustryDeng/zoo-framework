package com.ideaaedi.zoo.diy.artifact.mybatisplus.provider;

import com.ideaaedi.zoo.commonbase.zoo_component.auth.AuthUrlWhitelist;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.handler.MybatisPlusMetaObjectHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import java.util.Optional;

/**
 * 内置默认的操作人id提供器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class DefaultOperatorIdProvider implements MybatisPlusMetaObjectHandler.OperatorIdProvider {
    
    @Autowired(required = false)
    private HttpServletRequest request;
    
    private final AuthUrlWhitelist authUrlWhitelist;
    
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    public DefaultOperatorIdProvider(AuthUrlWhitelist authUrlWhitelist) {
        this.authUrlWhitelist = authUrlWhitelist;
    }
    
    @Override
    public Long operatorId(MetaObject metaObject) {
        if (request == null) {
            return null;
        }
        Long currUserId = ZooContext.Auth.currUserId();
        if (currUserId != null) {
            return currUserId;
        }
        if (authUrlWhitelist == null) {
            return null;
        }
        // 如果是不需要权限的请求，则用户id返回0
        String servletPath = request.getServletPath();
        boolean hasPermission = Optional.ofNullable(authUrlWhitelist.whitelist())
                .map(coll -> coll.stream().anyMatch(url -> antPathMatcher.match(url, servletPath)))
                .orElse(false);
        if (hasPermission) {
            return 0L;
        }
        return null;
    }
}
