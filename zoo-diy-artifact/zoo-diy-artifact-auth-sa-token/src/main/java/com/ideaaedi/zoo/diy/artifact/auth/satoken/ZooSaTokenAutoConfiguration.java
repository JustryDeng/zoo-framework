package com.ideaaedi.zoo.diy.artifact.auth.satoken;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.router.SaRouterStaff;
import cn.dev33.satoken.stp.StpUtil;
import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.commonbase.zoo_component.auth.AuthUrlWhitelist;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.config.TokenStrategyRewriter;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.config.ZooSaTokenExceptionHandler;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.converter.CodeMsgProviderConverter;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.filter.ZooSaServletFilter;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.processor.ZooAuthePostProcessor;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.properties.ZooAuthProperties;
import com.ideaaedi.zoo.diy.artifact.auth.satoken.properties.ZooSaTokenDIYGuideProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Optional;

/**
 * sa-token 自动配置
 */
@Slf4j
@Import(TokenStrategyRewriter.class)
@ImportAutoConfiguration(ZooSaTokenExceptionHandler.class)
@EnableConfigurationProperties({ZooSaTokenDIYGuideProperties.class, ZooAuthProperties.class})
public class ZooSaTokenAutoConfiguration {
    
    @Bean
    @ConditionalOnMissingBean
    @ConfigurationPropertiesBinding
    public CodeMsgProviderConverter codeMsgProviderConverter() {
        return new CodeMsgProviderConverter();
    }
    
    /**
     * 注册Sa-Token全局过滤器 <br />
     * <a href="https://sa-token.cc/doc.html#/up/global-filter">官网</a>
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean<SaServletFilter> saServletFilter(ZooSaTokenExceptionHandler zooSaTokenExceptionHandler,
                                                                   ZooAuthePostProcessor zooAuthePostProcessor,
                                                                   ZooAuthProperties zooAuthProperties) {
        FilterRegistrationBean<SaServletFilter> frBean = new FilterRegistrationBean<>();
        frBean.setFilter(new ZooSaServletFilter()
                // 指定 拦截路由 与 放行路由
                .addInclude("/**")
                // 认证函数: 每次请求执行
                .setAuth(obj -> {
                    SaRouterStaff saRouterStaff = SaRouter.match("/**");
                    ZooAuthProperties.ZooAuthBasic authBasic = zooAuthProperties.getBasic();
                    Optional.ofNullable(authBasic)
                            .map(AuthUrlWhitelist::whitelist)
                            .ifPresent(whitelist -> {
                                if (!CollectionUtils.isEmpty(whitelist)) {
                                    saRouterStaff.notMatch(new ArrayList<>(whitelist));
                                }
                            });
                    SaRequest request = SaHolder.getRequest();
                    String path = request.getRequestPath();
                    // 认证
                    saRouterStaff.check(r -> StpUtil.checkLogin());
                    // 认证后处理. StpUtil.checkLogin()认证失败时，会抛出异常；成功时StpUtil.isLogin()为true
                    if (StpUtil.isLogin()) {
                        zooAuthePostProcessor.doAfterAuthe(StpUtil.getLoginType(), StpUtil.getLoginId(),
                                StpUtil.getTokenValue());
                    }
                    // 鉴权
                    if (authBasic != null && authBasic.validUrlPermit()) {
                        saRouterStaff.check(r -> StpUtil.checkPermission(path));
                    }
                })
                // 异常处理函数：每次认证函数发生异常时执行此函数
                .setError(zooSaTokenExceptionHandler)
                // 前置函数：在每次认证函数之前执行（BeforeAuth 不受 includeList 与 excludeList 的限制，所有请求都会进入）
                .setBeforeAuth(r -> {
                    // ---------- 设置一些安全响应头 ----------
                    SaHolder.getResponse()
                            // 服务器名称
                            .setServer("sa-server")
                            // 是否可以在iframe显示视图： DENY=不可以 | SAMEORIGIN=同域下可以 | ALLOW-FROM uri=指定域名下可以
                            .setHeader("X-Frame-Options", "SAMEORIGIN")
                            // 是否启用浏览器默认XSS防护： 0=禁用 | 1=启用 | 1; mode=block 启用, 并在检查到XSS攻击时，停止渲染页面
                            .setHeader("X-XSS-Protection", "1; mode=block")
                            // 禁用浏览器内容嗅探
                            .setHeader("X-Content-Type-Options", "nosniff")
                    ;
                })
        );
        // 优先级不能设置太高，否则sa-token可能获取不到web上下文
        frBean.setOrder(AutoConfigurationConstant.ZOO_SA_TOKEN_AUTH_FILTER_ORDER);
        return frBean;
    }
}
