package com.ideaaedi.zoo.diy.feature.reqresp.encdec.impl.springmvc.core;

import com.ideaaedi.commonds.security.AES;
import com.ideaaedi.commonds.security.Base64;
import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.executor.AbstractReqDecryptExecutor;
import com.ideaaedi.zoo.diy.feature.reqresp.encdec.api.properties.ZooReqrespEncdecProperties;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RequestBodyDecryptFilter extends AbstractReqDecryptExecutor implements Filter {
    
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    
    public RequestBodyDecryptFilter(ZooReqrespEncdecProperties zooReqrespEncdecProperties) {
        super(zooReqrespEncdecProperties);
    }
    
    @Override
    public String decrypt(String requestData) {
        if (StringUtils.isBlank(requestData)) {
            return requestData;
        }
        ZooReqrespEncdecProperties.ReqDecryptProperties decryptProperties = getDecryptProperties();
        return AES.decryptSilently(requestData,
                Base64.encode(decryptProperties.getAesKey().getBytes(StandardCharsets.UTF_8)));
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest httpServletRequest)) {
            log.debug("curr request is not HttpServletRequest. skip.");
            filterChain.doFilter(request, response);
            return;
        }
        if (!(response instanceof HttpServletResponse httpServletResponse)) {
            log.debug("curr response is not HttpServletResponse. skip.");
            filterChain.doFilter(request, response);
            return;
        }
        String method = httpServletRequest.getMethod();
        boolean hisHttpMethod =
                HttpMethod.POST.name().equalsIgnoreCase(method) || HttpMethod.PUT.name().equalsIgnoreCase(method);
        if (!hisHttpMethod) {
            log.debug("curr http-method is not 'POST' or 'PUT'. skip.");
            filterChain.doFilter(request, response);
            return;
        }
        String rawRequestBody = readRequestBody(httpServletRequest);
        String decryptedRequestBody;
        if (needDecrypt(httpServletRequest)) {
            log.debug("before decrypt request body, {}", rawRequestBody);
            decryptedRequestBody = decrypt(rawRequestBody);
            log.debug("after  decrypt request body, {}", decryptedRequestBody);
        } else {
            decryptedRequestBody = rawRequestBody;
        }
        filterChain.doFilter(new DecryptingHttpServletRequestWrapper(httpServletRequest, decryptedRequestBody), response);
    }
    
    /**
     * 是否需要解密
     */
    private boolean needDecrypt(HttpServletRequest httpServletRequest) {
        // 未设置解密项的话，跳过
        ZooReqrespEncdecProperties.ReqDecryptProperties decryptProperties = getDecryptProperties();
        Set<String> includeExistReqHeaders = decryptProperties.getIncludeExistReqHeaders().stream()
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        Set<String> includeHosts = decryptProperties.getIncludeHosts().stream()
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        Set<String> includePaths = decryptProperties.getIncludePaths().stream()
                .filter(StringUtils::isNotBlank).collect(Collectors.toSet());
        boolean includeExistReqHeadersIsEmpty = CollectionUtils.isEmpty(includeExistReqHeaders);
        boolean includeHostsIsEmpty = CollectionUtils.isEmpty(includeHosts);
        boolean includePathsIsEmpty = CollectionUtils.isEmpty(includePaths);
        if (includeExistReqHeadersIsEmpty && includeHostsIsEmpty && includePathsIsEmpty) {
            log.debug("not config decrypt item. ignore decrypt request body.");
            return false;
        }
        // 命中排除项（存在指定的请求头）的话，跳过
        Set<String> excludeExistReqHeaders = decryptProperties.getExcludeExistReqHeaders();
        if (!CollectionUtils.isEmpty(excludeExistReqHeaders)) {
            boolean hitExcludeHeader = excludeExistReqHeaders.stream().anyMatch(excludeHeader -> httpServletRequest.getHeader(excludeHeader) != null);
            if (hitExcludeHeader) {
                log.debug("hitExcludeHeader. ignore decrypt request body.");
                return false;
            }
        }
        // 命中排除项（存在指定的uri）的话，跳过
        String requestUri = httpServletRequest.getRequestURI();
        Set<String> excludePaths = decryptProperties.getExcludePaths();
        if (!CollectionUtils.isEmpty(excludePaths)) {
            boolean hitExcludePaths = excludePaths.stream().anyMatch(excludePath -> antPathMatcher.match(excludePath, requestUri));
            if (hitExcludePaths) {
                log.debug("hitExcludePaths. ignore decrypt request body.");
                return false;
            }
        }
        // 命中排除项（存在指定的host）的话，跳过
        String remoteHost = httpServletRequest.getRemoteHost();
        Set<String> excludeHosts = decryptProperties.getExcludeHosts();
        if (!CollectionUtils.isEmpty(excludeHosts)) {
            boolean hitExcludeHosts = excludeHosts.stream().anyMatch(host -> antPathMatcher.match(host, remoteHost));
            if (hitExcludeHosts) {
                log.debug("hitExcludeHosts. ignore decrypt request body.");
                return false;
            }
        }
        
        // 命中包含项（存在指定的请求头）的话，需要解密
        if (!includeExistReqHeadersIsEmpty) {
            boolean hitIncludeHeader = includeExistReqHeaders.stream().anyMatch(includeHeader -> httpServletRequest.getHeader(includeHeader) != null);
            if (hitIncludeHeader) {
                log.debug("hitIncludeHeader. do decrypt.");
                return true;
            }
        }
        // 命中包含项（存在指定的uri）的话，需要解密
        if (!includePathsIsEmpty) {
            boolean hitIncludePaths = includePaths.stream().anyMatch(includePath -> antPathMatcher.match(includePath, requestUri));
            if (hitIncludePaths) {
                log.debug("hitIncludePaths. do decrypt.");
                return true;
            }
        }
        // 命中包含（存在指定的host）的话，需要解密
        if (!includeHostsIsEmpty) {
            boolean hitIncludeHosts = includeHosts.stream().anyMatch(host -> antPathMatcher.match(host, remoteHost));
            if (hitIncludeHosts) {
                log.debug("hitIncludeHosts. do decrypt.");
                return true;
            }
        }
        log.debug("don't hit include item. ignore decrypt request body.");
        return false;
    }
    
    /**
     * 获取请求体数据
     */
    private String readRequestBody(HttpServletRequest request) throws IOException {
        // 确保不乱码
        request.setCharacterEncoding("UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }
        return stringBuilder.toString();
    }
    
    /**
     * 自定义的请求包装器
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    static class DecryptingHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private final String requestBody;
        
        public DecryptingHttpServletRequestWrapper(HttpServletRequest request, String requestBody) {
            super(request);
            this.requestBody = requestBody;
        }
        
        @Override
        public ServletInputStream getInputStream() {
            final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody.getBytes(StandardCharsets.UTF_8));
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return false;
                }
                
                @Override
                public boolean isReady() {
                    return false;
                }
                
                @Override
                public void setReadListener(ReadListener listener) {
                
                }
                
                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }
        
        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }
    }
}
