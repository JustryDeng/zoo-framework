package com.ideaaedi.zoo.commonbase.util;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSON;
import com.ideaaedi.zoo.commonbase.entity.BaseCodeMsgEnum;
import com.ideaaedi.zoo.commonbase.exception.RpcException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * http-client配置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@UtilityClass
public final class HttpUtil {
    
    /**
     * get请求
     *
     * @see HttpUtil#get(String, Map, Class, Map)
     */
    public static String get(String url, @Nullable Map<String, String> params) {
        return get(url, params, String.class, null);
    }
    
    
    /**
     * get请求
     *
     * @param url 请求url
     * @param params 请求数据
     * @param returnType 返回数据类
     *
     * @return 返回数据
     */
    public static <T> T get(String url, @Nullable Map<String, String> params, Class<T> returnType, Map<String, List<String>> headers) {
        Objects.requireNonNull(url, "url cannot be null.");
        String responseBody;
        cn.hutool.http.HttpResponse response = null;
        try {
            // param
            if (!CollectionUtils.isEmpty(params)) {
                StringBuilder sb = new StringBuilder(16);
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    String value = entry.getValue();
                    if (value == null) {
                        continue;
                    }
                    sb.append(
                            URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.name()))
                            .append("=")
                            .append(URLEncoder.encode(value, StandardCharsets.UTF_8.name()))
                            .append("&");
                }
                sb.replace(sb.length() - 1, sb.length(), "");
                url = url + "?" + sb;
            }
            log.info("url -> {}", url);
    
            HttpRequest httpRequest = HttpRequest.get(url);
            if (!CollectionUtils.isEmpty(headers)) {
                httpRequest = httpRequest.header(headers);
            }
            response = httpRequest
                    .execute();
            responseBody = response.body();
            log.info("responseBody -> {}", responseBody);
            if (response.getStatus() != HttpStatus.OK.value()) {
                String tips = String.format("response.statusCode is not 200. url -> %s, response -> %s", url, response);
                throw new RpcException(tips, BaseCodeMsgEnum.HTTP_STATUS_CODE_ERROR, responseBody);
            }
        } catch (RpcException e) {
            throw e;
        } catch (Exception e) {
            String tips = String.format("url -> %s, response -> %s", url, response);
            throw new RpcException(tips, BaseCodeMsgEnum.RPC_ERROR, e);
        }
        T returnObj;
        if (returnType.isAssignableFrom(String.class)) {
            //noinspection unchecked
            returnObj = (T) responseBody;
        } else {
            returnObj = JSON.parseObject(responseBody, returnType);
        }
        log.info("returnObj -> {}", returnObj);
        return returnObj;
    }
    
    /**
     * post application/json请求
     *
     * @see HttpUtil#postJson(String, Object, Map)
     */
    public static <R> String postJson(String url, R requestData) {
        return postJson(url, requestData, null);
    }
    
    /**
     * post application/json请求
     *
     * @see HttpUtil#postJson(String, Object, Map, Class)
     */
    public static <R> String postJson(String url, R requestData, @Nullable Map<String, String> headers) {
        return postJson(url, requestData, headers, String.class);
    }
    
    /**
     * post application/json请求
     *
     * @see HttpUtil#postJson(String, Object, Map, Class)
     */
    public static <R> String postJson(String url, R requestData, @Nullable Map<String, String> headers,
                                      boolean recordLog) {
        return postJson(url, requestData, headers, String.class, recordLog);
    }
    
    /**
     * post application/json请求
     *
     * @see HttpUtil#postJson(String, Object, Map, Class, boolean)
     */
    public static <R, T> T postJson(String url, @Nullable R requestData, @Nullable Map<String, String> headers,
                                    Class<T> returnType) {
        if (CollectionUtils.isEmpty(headers)) {
            headers = new HashMap<>(8);
        }
        headers.put("Content-Type", "application/json;charset=utf-8");
        String json = null;
        if (requestData != null) {
            json = (requestData instanceof String) ? requestData.toString() : JSON.toJSONString(requestData);
        }
        return postJson(url, json, headers, returnType, true);
    }
    
    /**
     * post application/json请求
     *
     * @see HttpUtil#postJson(String, Object, Map, Class, boolean)
     */
    public static <R, T> T postJson(String url, @Nullable R requestData, @Nullable Map<String, String> headers,
                                    Class<T> returnType, boolean recordLog) {
        Objects.requireNonNull(url, "url cannot be null.");
        Objects.requireNonNull(returnType, "returnType cannot be null.");
        if (CollectionUtils.isEmpty(headers)) {
            headers = new HashMap<>(8);
        }
        headers.put("Content-Type", "application/json;charset=utf-8");
        String json = null;
        if (requestData != null) {
            json = (requestData instanceof String) ? requestData.toString() : JSON.toJSONString(requestData);
        }
        String responseBody;
        cn.hutool.http.HttpResponse response = null;
        try {
            if (recordLog) {
                log.info("url -> {}, requestData -> {}", url, requestData);
            }
           response = HttpRequest.post(url)
                    .body(json)
                    .headerMap(headers, true)
                    .execute();
            responseBody = response.body();
            log.info("responseBody -> {}", responseBody);
            if (response.getStatus() != HttpStatus.OK.value()) {
                String tips = String.format("response.statusCode is not 200. url -> %s, requestData -> %s, response "
                                            + "-> %s", url, requestData, response);
                throw new RpcException(tips, BaseCodeMsgEnum.HTTP_STATUS_CODE_ERROR, responseBody);
            }
        } catch (RpcException e) {
            throw e;
        } catch (Exception e) {
            String tips = String.format("url -> %s, requestData -> %s, response -> %s", url, requestData, response);
            throw new RpcException(tips, BaseCodeMsgEnum.RPC_ERROR, e);
        }
        T returnObj;
        if (returnType.isAssignableFrom(String.class)) {
            //noinspection unchecked
            returnObj = (T) responseBody;
        } else {
            returnObj = JSON.parseObject(responseBody, returnType);
        }
        if (recordLog) {
            log.info("returnObj -> {}", returnObj);
        }
        return returnObj;
    }
    
    /**
     * post application/json请求
     *
     * @see HttpUtil#postForm(String, Map, Map)
     */
    public static String postForm(String url, Map<String, Object> params) {
        return postForm(url, params, null);
    }
    
    /**
     * post application/json请求
     *
     * @see HttpUtil#postForm(String, Map, Map, Class)
     */
    public static String postForm(String url, @Nullable Map<String, Object> params,
                                      @Nullable Map<String, String> headers) {
        return postForm(url, params, headers, String.class);
    }
    
    /**
     * post application/x-www-form-urlencoded请求
     *
     * @see HttpUtil#postJson(String, Object, Map, Class, boolean)
     */
    public static <T> T postForm(String url, @Nullable Map<String, Object> params,
                                 @Nullable Map<String, String> headers, Class<T> returnType) {
        return postForm(url, params, headers, returnType, true);
    }
    
    /**
     * post application/x-www-form-urlencoded请求
     *
     * @see HttpUtil#postJson(String, Object, Map, Class, boolean)
     */
    public static <T> T postForm(String url, @Nullable Map<String, Object> params, @Nullable Map<String, String> headers,
                                 Class<T> returnType, boolean recordLog) {
        // 请求头
        if (CollectionUtils.isEmpty(headers)) {
            headers = new HashMap<>(4);
        }
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        String requestData = JSON.toJSONString(params);
        cn.hutool.http.HttpResponse response = null;
        String responseBody;
        try {
            if (recordLog) {
                log.info("url -> {}, requestData -> {}", url, requestData);
            }
            response = cn.hutool.http.HttpRequest.post(url)
                    .form(params)
                    .headerMap(headers, true)
                    .execute();
            responseBody = response.body();
            log.info("responseBody -> {}", responseBody);
            if (response.getStatus() != HttpStatus.OK.value()) {
                String tips = String.format("response.statusCode is not 200. url -> %s, requestData -> %s, response "
                                            + "-> %s", url, requestData, response);
                throw new RpcException(tips, BaseCodeMsgEnum.HTTP_STATUS_CODE_ERROR, responseBody);
            }
        } catch (RpcException e) {
            throw e;
        } catch (Exception e) {
            String tips = String.format("url -> %s, requestData -> %s, response -> %s", url, requestData, response);
            throw new RpcException(tips, BaseCodeMsgEnum.RPC_ERROR, e);
        }
        T returnObj;
        if (returnType.isAssignableFrom(String.class)) {
            //noinspection unchecked
            returnObj = (T) responseBody;
        } else {
            returnObj = JSON.parseObject(responseBody, returnType);
        }
        if (recordLog) {
            log.info("returnObj -> {}", returnObj);
        }
        return returnObj;
    }
}
