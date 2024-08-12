package com.ideaaedi.zoo.diy.artifact.logging.micrometer.util;

import com.ideaaedi.commonds.constants.SymbolConstant;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import io.opentelemetry.api.trace.TraceId;
import io.opentelemetry.sdk.trace.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * trace工具
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public final class TraceUtil {
    
    /**
     * 向mdc中写入TRACE_XD
     *
     * @param userId 用户id
     * @param requestPath 请求uri
     */
    public static void generateAndInjectTraceXd(Long userId, String requestPath) {
        String traceId = MDC.get(BaseConstant.TRACE_ID);
        if (StringUtils.isBlank(traceId)) {
            traceId = IdGenerator.random().generateTraceId();
        }
        String uriInfo = Optional.ofNullable(requestPath)
                .map(uri -> uri.replace(SymbolConstant.SLASH, SymbolConstant.POINT))
                .map(uri -> StringUtils.strip(uri, SymbolConstant.POINT))
                .orElse(BaseConstant.EMPTY);
        String traceXd = String.join("_", traceId, userId == null ? "%s" : userId.toString(), uriInfo);
        injectTraceXd(traceXd);
    }
    
    /**
     * 写入TRACE_XD
     *
     * @param traceXd 带业务信息的日志追踪id
     */
    public static void injectTraceXd(@Nonnull String traceXd) {
        MDC.put(BaseConstant.TRACE_XD, traceXd);
    }
    
    /**
     * 清除mdc中的TRACE_XD
     */
    public static void cleanTraceXd() {
        MDC.remove(BaseConstant.TRACE_XD);
    }
    
    /**
     * 将用户id写入TRACE_XD中
     *
     * @param userId 用户id
     */
    public static void fillUserId2TraceXd(Long userId) {
        if (userId == null) {
            return;
        }
        String traceXd = MDC.get(BaseConstant.TRACE_XD);
        if (StringUtils.isBlank(traceXd)) {
            return;
        }
        if (traceXd.contains("%s")) {
            String newTraceXd = String.format(traceXd, userId);
            MDC.put(BaseConstant.TRACE_XD, newTraceXd);
            log.info("upgrade traceXd from {} to {}", traceXd, newTraceXd);
        }
    }

    /**
     * 从traceXd中抽取traceId
     *
     * @param traceXd 含业务信息的日志追踪id
     */
    @Nonnull
    public static String extractTraceId(@Nonnull String traceXd) {
        return traceXd.split("_")[0];
    }
    
    /**
     * 判断traceXd是否有效
     * <br />
     * 因为traceXd的构成是: {满足micrometer标准的traceId} + “_” + {用户id} + "_" + {requestPath}。所以进行以下校验
     *
     * @return traceXd是否有效
     */
    public static boolean validateTraceXd(String traceXd) {
        if (traceXd == null) {
            return false;
        }
        int splitSymbolCount = 0;
        for (char c : traceXd.toCharArray()) {
            if ('_' == c) {
                splitSymbolCount++;
            }
        }
        if (splitSymbolCount < 2) {
            return false;
        }
        // 再根据micrometer标准校验traceId
        String traceId = traceXd.split("_")[0];
        return TraceId.isValid(traceId);
    }
}
