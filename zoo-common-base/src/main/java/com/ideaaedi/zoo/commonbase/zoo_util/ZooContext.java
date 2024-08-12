package com.ideaaedi.zoo.commonbase.zoo_util;


import com.alibaba.ttl.TransmittableThreadLocal;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.commonbase.entity.BaseCodeMsgEnum;
import com.ideaaedi.zoo.commonbase.enums.SpecUserTenantEnum;
import com.ideaaedi.zoo.commonbase.exception.BaseException;
import com.ideaaedi.zoo.commonbase.util.IpUtil;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScope;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 上下文工具
 *
 * <p>
 * 提示：这里之所以内部再通过接口归档，是为了给后续组件完整化减少复杂度
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public final class ZooContext {
    
    private static final ThreadLocal<Map<String, Object>> contextThreadLocal = TransmittableThreadLocal.withInitial(HashMap::new);
    
    /*
     * 获取线程的上下文
     *
     * @return 线程的上下文
     */
    @Nonnull
    public static Map<String, Object> unmodifiableContext() {
        return Collections.unmodifiableMap(contextThreadLocal.get());
    }
    
    /**
     * 设置数据域租户值到请求attributes
     *
     * @see ZooContext#setUserIdAndTenantScope(Long, TenantScope)
     */
    public static void setUserIdAndTenantScope(@Nonnull SpecUserTenantEnum specUserTenant) {
        ZooContext.setUserIdAndTenantScope(specUserTenant.getUserId(), specUserTenant.getTenantScope());
    }
    
    /**
     * 设置用户id、用户数据域租户值到请求attributes
     *
     * @param userId 用户id
     * @param tenantScope 数据域租户值
     */
    public static void setUserIdAndTenantScope(@Nonnull Long userId, @Nullable TenantScope tenantScope) {
        Objects.requireNonNull(userId);
        Inner.put(BaseConstant.CURR_USER_ID, userId);
        if (tenantScope != null) {
            Inner.put(BaseConstant.CURR_TENANT_SCOPE, tenantScope);
        }
    }
    
    /**
     * 清除用户id、数据域租户值，并返回清空的值
     *
     * @return <ul>
     * <li>左：用户id</li>
     * <li>右-数据域租户值</li>
     * </ul>
     */
    @Nonnull
    public static Pair<Long, TenantScope> clearAndGetUserInfo() {
        // 获取
        Long userId = Inner.get(BaseConstant.CURR_USER_ID);
        TenantScope tenantScope = Inner.get(BaseConstant.CURR_TENANT_SCOPE);
        // 移除
        Inner.remove(BaseConstant.CURR_USER_ID, BaseConstant.CURR_TENANT_SCOPE);
        return Pair.of(userId, tenantScope);
    }
    
    /**
     * 清除用户id、数据域信息
     *
     * @param specUserTenant 指定清除用户与租户(注：当用户id匹配时才清除)
     */
    public static void clear(@Nullable SpecUserTenantEnum specUserTenant) {
        if (specUserTenant == null) {
            return;
        }
        Long existUserId = Auth.currUserId();
        long userId = specUserTenant.getUserId();
        if ((Long.valueOf(userId)).equals(existUserId)) {
            Inner.remove(BaseConstant.CURR_USER_ID, BaseConstant.CURR_TENANT_SCOPE);
        } else {
            log.warn("ignore clear, userId not match. existUserId -> {}, userId -> {}.", existUserId, userId);
        }
    }
    
    /**
     * 清除所有
     */
    @Nullable
    public static void clear() {
        Inner.context().clear();
    }
    
    /**
     * 内部基础能力，不和任何组件相关
     */
    interface Inner {
        
        /*
         * 添加一个键值对到当前线程的上下文中
         */
        static void put(String key, Object value) {
            context().put(key, value);
        }
        
        /*
         * 上下文中获取指定key的值
         */
        @Nullable
        static <T> T get(String key) {
            //noinspection unchecked
            return (T)context().get(key);
        }
        
        /**
         * 清除
         */
        @Nullable
        static void remove(String key, String... moreKeys) {
            Map<String, Object> context = context();
            if (key != null) {
                context.remove(key);
            }
            if (moreKeys != null) {
                for (String moreKey : moreKeys) {
                    context.remove(moreKey);
                }
            }
        }
    
        /*
         * 获取线程的上下文
         *
         * @return 线程的上下文
         */
        @Nonnull
        static Map<String, Object> context() {
            return contextThreadLocal.get();
        }
    }
    
    /**
     * 租户相关
     */
    public interface Tenant {
    
        /**
         * 获取用户数据域
         *
         * @return 当前登录用户数据域
         */
        @Nullable
        static TenantScope currTenantScope() {
            return (TenantScope) Inner.context().get(BaseConstant.CURR_TENANT_SCOPE);
        }
    
        /**
         * 获取用户数据域
         *
         * @return 当前登录用户数据域
         */
        @Nonnull
        static TenantScope currTenantScopeNonNull() {
            final TenantScope tenantScope = currTenantScope();
            if (tenantScope == null) {
                throw new BaseException(BaseCodeMsgEnum.OBTAIN_USER_TENANT_SCOPE_FAIL);
            }
            return tenantScope;
        }
    
        /**
         * 获取用户数据insert域
         *
         * @return 用户数据insert域
         */
        @Nullable
        static String currInsertTenant() {
            TenantScope tenantScope = currTenantScopeNonNull();
            return tenantScope.insertTenant();
        }
    
        /**
         * 获取用户数据写入域
         *
         * @return 用户数据写入域
         */
        @Nonnull
        static String currInsertTenantNonNull() {
            TenantScope tenantScope = currTenantScopeNonNull();
            String insertTenant = tenantScope.insertTenant();
            if (StringUtils.isBlank(insertTenant)) {
                throw new BaseException(BaseCodeMsgEnum.OBTAIN_USER_TENANT_INSERT_SCOPE_FAIL);
            }
            return insertTenant;
        }
    
        /**
         * 获取用户数据查询域
         *
         * @return 用户数据查询域
         */
        @Nullable
        static Collection<String> currReadableTenants() {
            TenantScope tenantScope = currTenantScopeNonNull();
            return tenantScope.readableTenants();
        }
    
        /**
         * 获取用户数据修改/删除域
         *
         * @return 用户数据修改/删除域
         */
        @Nullable
        static Collection<String> currModifiableTenants() {
            TenantScope tenantScope = currTenantScopeNonNull();
            return tenantScope.modifiableTenants();
        }
    }
    
    /**
     * 认证鉴权相关
     */
    public interface Auth {
    
        /**
         * 获取用户id
         *
         * @return 用户id
         */
        @Nullable
        static Long currUserId() {
            return (Long) Inner.context().get(BaseConstant.CURR_USER_ID);
        }
    
        /**
         * 获取用户id
         *
         * @return 用户id
         */
        @Nullable
        static Long currUserIdDefault(Long defaultValue) {
            final Long currUserId = currUserId();
            if (currUserId == null) {
                return defaultValue;
            }
            return currUserId;
        }
    
        /**
         * 获取用户id
         *
         * @return 用户id
         */
        @Nonnull
        static Long currUserIdNonNull() {
            final Long currUserId = currUserId();
            if (currUserId == null) {
                throw new BaseException(BaseCodeMsgEnum.OBTAIN_USER_ID_FAIL);
            }
            return currUserId;
        }
    }
    
    /**
     * 审计日志相关
     */
    public interface AUDIT_LOG {
    
        /**
         * 设置当前最新的审计日志id
         */
        static void setLatestAuditLogId(Long id) {
            Inner.put(BaseConstant.LATEST_AUDIT_LOG_ID, id);
        }
    
        /**
         * 获取http请求
         */
        @Nullable
        static Long latestAuditLogId() {
            return (Long) Inner.context().get(BaseConstant.LATEST_AUDIT_LOG_ID);
        }
    }
    
    /**
     * http相关
     */
    public interface Http {
        
        /**
         * 获取http请求
         */
        @Nullable
        static HttpServletRequest httpServletRequest() {
            final RequestAttributes requestAttributes;
            try {
                requestAttributes = RequestContextHolder.currentRequestAttributes();
            } catch (IllegalStateException e) {
                log.debug(e.getMessage());
                return null;
            }
            if (!(requestAttributes instanceof ServletRequestAttributes)) {
                return null;
            }
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        }
    
        /**
         * 获取http响应
         */
        public static HttpServletResponse httpServletResponse() {
            final RequestAttributes requestAttributes;
            try {
                requestAttributes = RequestContextHolder.currentRequestAttributes();
            } catch (IllegalStateException e) {
                return null;
            }
            if (!(requestAttributes instanceof ServletRequestAttributes)) {
                return null;
            }
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        }
        
        /**
         * 获取客户端ip
         */
        @Nullable
        static String clientIpAddress() {
            return clientIpAddress(httpServletRequest());
        }
    
        /**
         * 获取客户端ip
         */
        @Nullable
        static String clientIpAddress(@Nullable HttpServletRequest httpServletRequest) {
            if (httpServletRequest == null) {
                return null;
            }
            return IpUtil.determineClientIpAddress(httpServletRequest);
        }
    }
}