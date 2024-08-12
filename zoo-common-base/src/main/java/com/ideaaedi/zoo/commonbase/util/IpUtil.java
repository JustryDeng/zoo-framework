package com.ideaaedi.zoo.commonbase.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ideaaedi.zoo.commonbase.constant.JdSymbolConstant;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ip util
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public final class IpUtil {
    
    public static final String UNKNOWN = "unknown";
    
    /**
     * ip寻找优先级
     */
    public static final List<String> IP_HEADER_CHAIN = Lists.newArrayList(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "X-Real-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
    ).stream().map(headerName -> StringUtils.lowerCase(headerName, Locale.ENGLISH)).collect(Collectors.toList());
    
    /**
     * 本机ip
     */
    public static final Set<String> LOCALHOST_IP_SET = Sets.newHashSet("127.0.0.1", "0:0:0:0:0:0:0:1");
    
    /**
     * 获取客户端ip地址
     *
     * @return 客户端ip地址
     */
    @Nullable
    public static String determineClientIpAddress(@Nullable HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        final Map<String, String> headers = new HashMap<>(16);
        Enumeration<String> headerNameEnumeration = request.getHeaderNames();
        if (headerNameEnumeration != null) {
            while (headerNameEnumeration.hasMoreElements()) {
                String headerKey = headerNameEnumeration.nextElement();
                String headerValue = request.getHeader(headerKey);
                if (StringUtils.isBlank(headerValue)) {
                    continue;
                }
                headers.put(StringUtils.lowerCase(headerKey, Locale.ENGLISH), headerValue);
            }
        }
        log.debug("IpUtil.determineClientIpAddress headers -> {}", headers);
        String ipInfo = null;
        // 从指定头中获取
        for (String toFindHeader : IP_HEADER_CHAIN) {
            ipInfo = headers.get(toFindHeader);
            if (StringUtils.isNotBlank(ipInfo) && !UNKNOWN.equalsIgnoreCase(ipInfo)) {
                log.debug("IpUtil.determineClientIpAddress ipInfo -> {}. toFindHeader -> {}", ipInfo, toFindHeader);
                break;
            }
        }
        // 调用getRemoteAddr获取
        if (StringUtils.isBlank(ipInfo)) {
            ipInfo = request.getRemoteAddr();
            log.debug("IpUtil.determineClientIpAddress RemoteAddr ipInfo -> {}", ipInfo);
        }
        // 多个ip地址的话，取第一个
        if (StringUtils.isNotBlank(ipInfo)) {
            log.debug("IpUtil.determineClientIpAddress find multi-ip. curr ipInfo -> {}", ipInfo);
            ipInfo = Arrays.stream(ipInfo.split(JdSymbolConstant.COMMA))
                    .filter(str -> !UNKNOWN.equalsIgnoreCase(str))
                    .findFirst()
                    .orElse(null);
        }
        if (LOCALHOST_IP_SET.contains(ipInfo)) {
            // 获取本机内网地址
            try {
                ipInfo = InetAddress.getLocalHost().getHostAddress();
                log.debug("IpUtil.determineClientIpAddress obtain host address -> {}", ipInfo);
            } catch (UnknownHostException e) {
                // ignore
            }
        }
        return ipInfo;
    }

}


