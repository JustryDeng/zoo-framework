package com.ideaaedi.zoo.commonbase.util;

import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.commonbase.constant.JdSymbolConstant;
import com.ideaaedi.zoo.commonbase.entity.BaseCodeMsgEnum;
import com.ideaaedi.zoo.commonbase.entity.Result;
import com.ideaaedi.zoo.commonbase.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public final class CommonUtil {
    
    /**
     * 断言result为成功
     *
     * @param result 结果对象
     */
    public static void assertSuccess(@Nonnull Result<?> result) {
        Objects.requireNonNull(result, "result cannot be null.");
        boolean ifSuccess = BaseCodeMsgEnum.SUCCESS.getCode().equals(result.getCode());
        if (ifSuccess) {
            return;
        }
        throw new BaseException(result);
    }
    
    /**
     * 生成traceId
     *
     * @param requestUri 请求url
     * @param headerTraceId 请求头中的traceId
     * @param clientIp 客户端ip
     *
     * @return traceId
     */
    @Deprecated
    public static String getTraceId(String requestUri, String headerTraceId, @Nullable String clientIp) {
        String traceId =  UUID.randomUUID().toString().split("-")[0];
        if (StringUtils.isNotBlank(clientIp)) {
            int absHashCode = Math.abs(clientIp.hashCode());
            traceId = traceId + "IP" + absHashCode;
        }
        traceId = traceId + "_%s";
        try {
            if (StringUtils.isNotBlank(headerTraceId)) {
                traceId = headerTraceId;
            } else if (StringUtils.isNotBlank(requestUri)) {
                if (requestUri.endsWith(JdSymbolConstant.SLASH)) {
                    requestUri = requestUri.substring(0, requestUri.length() - 1);
                }
                traceId = String.join("_", traceId, requestUri);
            }
        } catch (Exception e) {
            // ignore
        }
        return traceId;
    }
    
    /**
     * 生成traceId
     *
     * @param requestUri 请求url
     * @param headerTraceId 请求头中的traceId
     *
     * @return traceId
     */
    @Deprecated
    public static String getTraceIdMdcFirst(String requestUri, String headerTraceId) {
        String traceId = MDC.get(BaseConstant.TRACE_ID);
        if (StringUtils.isNotBlank(traceId)) {
            return traceId;
        }
        return CommonUtil.getTraceId(requestUri, headerTraceId, null);
    }
    
    /**
     * 如果tenant属于业务租户那么返回对应的业务租户值; 否则（即为业务租户之上的系统租户，）直接返回tenant
     *
     * @param tenant 租户值
     * @param bizTenantLevel 作为bizTenant的tenant的层级
     *
     * @return 业务租户或系统租户
     */
    public static String determineBizTenantOrSysTenant(@Nonnull String tenant, int bizTenantLevel) {
        Objects.requireNonNull(tenant, "tenant cannot be null.");
        if (!tenant.endsWith(JdSymbolConstant.COMMA)) {
            log.warn("curr tenant -> {}, bizTenantLevel -> {}", tenant, bizTenantLevel);
            throw new IllegalArgumentException("tenant should end with '" + JdSymbolConstant.COMMA + "'.");
        }
        tenant = tenant.substring(0, tenant.length() - 1);
        String[] itemArr = tenant.split(JdSymbolConstant.COMMA);
        if (itemArr.length <= bizTenantLevel) {
            return tenant + JdSymbolConstant.COMMA;
        }
        return String.join(JdSymbolConstant.COMMA, Arrays.copyOf(itemArr, bizTenantLevel)) + JdSymbolConstant.COMMA;
    }
   
}
