package com.ideaaedi.zoo.diy.artifact.tenantlike.tenant;

import com.google.common.collect.Lists;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.DefaultTenantScope;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScope;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScopeHolder;
import com.ideaaedi.zoo.commonbase.zoo_util.SpelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

/**
 * 对{@link com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.annotation.TenantScope}进行支持实现
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 90)
@EnableAspectJAutoProxy(exposeProxy = true)
public class TenantScopeAspect {
    
    @Around("@annotation(tenantScopeAnno)")
    public Object aroundAdvice(ProceedingJoinPoint thisJoinPoint, com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.annotation.TenantScope tenantScopeAnno) throws Throwable {
        TenantScope tenantScopeData = parseTenantScope(thisJoinPoint, tenantScopeAnno);
        log.debug("TenantScopeAspect curr tenantScopeData -> {}", tenantScopeData);
        try {
            TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.get().addFirst(tenantScopeData);
            return thisJoinPoint.proceed();
        } finally {
            ConcurrentLinkedDeque<TenantScope> tenantScopes = TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.get();
            if (tenantScopes.size() > 0) {
                tenantScopes.pollFirst();
                if (tenantScopes.size() == 0) {
                    TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.remove();
                }
            }
        }
    }
    
    /*
     * 获取可操作数据范围信息
     */
    private static TenantScope parseTenantScope(ProceedingJoinPoint thisJoinPoint, com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.annotation.TenantScope tenantScopeAnno) {
        String commonDataScopeTenant = tenantScopeAnno.value();
        String insertTenant = tenantScopeAnno.insertTenant();
        String[] readableTenantArr = tenantScopeAnno.readableTenants();
        String[] modifiableTenantArr = tenantScopeAnno.modifiableTenants();
        Method method = ((MethodSignature) thisJoinPoint.getSignature()).getMethod();
        Object[] arguments = thisJoinPoint.getArgs();
        
        if (StringUtils.isNotBlank(commonDataScopeTenant)) {
            commonDataScopeTenant = SpelUtil.parseSpel(method, arguments, String.class, commonDataScopeTenant);
        }
        if (StringUtils.isNotBlank(insertTenant)) {
            insertTenant = SpelUtil.parseSpel(method, arguments, String.class, insertTenant);
        }
        
        insertTenant = StringUtils.isBlank(insertTenant) ? commonDataScopeTenant : insertTenant;
        List<String> readableTenants = null;
        if (readableTenantArr == null || readableTenantArr.length == 0) {
            if (StringUtils.isNotBlank(commonDataScopeTenant)) {
                readableTenants = Lists.newArrayList(commonDataScopeTenant);
            }
        } else {
            readableTenants = Arrays.stream(readableTenantArr)
                    .filter(Objects::nonNull)
                    .map(tenant -> SpelUtil.parseSpel(method, arguments, String.class, tenant))
                    .collect(Collectors.toList());
        }
        List<String> modifiableTenants = null;
        if (modifiableTenantArr == null || modifiableTenantArr.length == 0) {
            if (StringUtils.isNotBlank(commonDataScopeTenant)) {
                modifiableTenants = Lists.newArrayList(commonDataScopeTenant);
            }
        } else {
            modifiableTenants = Arrays.stream(modifiableTenantArr)
                    .filter(Objects::nonNull)
                    .map(tenant -> SpelUtil.parseSpel(method, arguments, String.class, tenant))
                    .collect(Collectors.toList());
        }
        return DefaultTenantScope.of(insertTenant, readableTenants, modifiableTenants);
    }
}