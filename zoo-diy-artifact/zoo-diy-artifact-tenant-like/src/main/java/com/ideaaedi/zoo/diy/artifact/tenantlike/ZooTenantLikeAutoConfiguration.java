package com.ideaaedi.zoo.diy.artifact.tenantlike;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.ideaaedi.zoo.commonbase.constant.AutoConfigurationConstant;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScope;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScopeHolder;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import com.ideaaedi.zoo.diy.artifact.tenantlike.holder.MybatisPlusInfoHolder;
import com.ideaaedi.zoo.diy.artifact.tenantlike.properties.ZooTenantLikeDIYGuideProperties;
import com.ideaaedi.zoo.diy.artifact.tenantlike.properties.ZooTenantLikeProperties;
import com.ideaaedi.zoo.diy.artifact.tenantlike.tenant.LikeTenantLineInnerInterceptor;
import com.ideaaedi.zoo.diy.artifact.tenantlike.tenant.TenantScopeAspect;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.util.CollectionUtils;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 多租户 自动配置
 */
@Slf4j
@Import(TenantScopeAspect.class)
@AutoConfigureOrder(AutoConfigurationConstant.ZOO_TENANT_LIKE_AUTO_CONFIGURATION_ORDER)
@EnableConfigurationProperties({ZooTenantLikeDIYGuideProperties.class, ZooTenantLikeProperties.class})
public class ZooTenantLikeAutoConfiguration {
    
    @Bean
    public MybatisPlusInfoHolder mybatisPlusInfoHolder(ApplicationContext applicationContext) {
        return new MybatisPlusInfoHolder(applicationContext);
    }
    
    /**
     * 插件配置
     */
    @Bean(name = AutoConfigurationConstant.MYBATIS_PLUS_INTERCEPTOR_BEAN_NAME)
    public MybatisPlusInterceptor mybatisPlusInterceptor(MybatisPlusInfoHolder mybatisPlusInfoHolder) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 租户插件
        interceptor.addInnerInterceptor(new LikeTenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                // 先从会话级临时数据域中查
                TenantScope tenantScope = TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.get().peekFirst();
                // 在从用户登录态中查
                if (tenantScope == null) {
                    tenantScope = ZooContext.Tenant.currTenantScope();
                }
                Objects.requireNonNull(tenantScope, "getTenantId fail. tenantScope cannot be null.");
                return new StringValue(JSON.toJSONString(tenantScope, JSONWriter.Feature.NotWriteDefaultValue));
            }
            
            @Override
            public String getTenantIdColumn() {
                return BaseConstant.TENANT_COLUMN_NAME;
            }
            
            @Override
            public boolean ignoreTable(String tableName) {
                Set<String> tenantTables = mybatisPlusInfoHolder.getTenantTables();
                if (CollectionUtils.isEmpty(tenantTables)) {
                    return true;
                }
                if (tableName.contains(BaseConstant.MYSQL_ANTI_ESCAPE)) {
                    tableName = tableName.replace(BaseConstant.MYSQL_ANTI_ESCAPE, BaseConstant.EMPTY);
                }
                return !tenantTables.contains(tableName);
            }
        }));
        
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        log.info("enable mybatis-plus interceptors -> {}.",
                interceptor.getInterceptors().stream().map(x -> x.getClass().getSimpleName()).collect(Collectors.toList()));
        return interceptor;
    }
}
