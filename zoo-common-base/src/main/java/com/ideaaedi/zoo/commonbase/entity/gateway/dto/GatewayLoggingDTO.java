package com.ideaaedi.zoo.commonbase.entity.gateway.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 网关进出日志DTO
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class GatewayLoggingDTO {
    
    /* ------------------------------------------------- 请求信息 ------------------------------------------------- */
    
    /**
     * 请求协议
     */
    private String requestSchema;
    
    /**
     * 请求方法
     */
    private String requestMethod;
    
    /**
     * 请求路径
     */
    private String requestPath;
    
    /**
     * 请求数据类型
     */
    private String requestContentType;
    
    /**
     * 请求头
     */
    private Map<String, String> requestHeaders;
    
    /**
     * url参数
     */
    private Map<String, Object> urlParams;
    
    /**
     * 请求体
     */
    private Object requestBody;
    
    /**
     * 请求时间
     */
    private LocalDateTime requestAt;
    
    /* ------------------------------------------------- 响应信息 ------------------------------------------------- */
    
    /**
     * 响应时间
     */
    private LocalDateTime responseAt;
    
    /**
     * 响应状态码
     */
    private Integer httpStatusCode;
    
    /**
     * 响应体
     */
    private Object responseBody;
    
    /**
     * 响应数据类型
     */
    private String responseContentType;
    
    /* ------------------------------------------------- 其它信息 ------------------------------------------------- */
    
    /**
     * 请求ip
     */
    private String clientIp;
    
    /**
     * 目标服务
     */
    private String targetMicroservices;
    
    /**
     * 网关向业务服务转发请求的时间
     * <br />
     * 即：经过过滤器GatewayLoggingFilter的处理后，真实向业务服务发送请求的时间
     */
    private LocalDateTime bizRequestAt;
    
    /**
     * 微服务处理业务耗时（毫秒）
     */
    private Long bizConsumeTime;
    
    /**
     * 业务服务响应至网关的时间
     */
    private LocalDateTime bizResponseAt;
    
    /**
     * 总耗时（毫秒）
     * <br />
     * 总耗时 = 微服务处理业务耗时 + 网关读取并重新放回响应体的耗时
     */
    private Long consumeTime;
    
    /**
     * 日志id
     */
    private String traceId;
    
    /**
     * 异常标识
     * <ul>
     *     <li>0 - 请求异常</li>
     *     <li>1 - 请求成功 & 业务状态未知</li>
     *     <li>2 - 请求成功 & 业务成功</li>
     *     <li>3 - 请求成功 & 业务异常</li>
     * </ul>
     */
    private Integer exceptionFlag = 1;
    
    /**
     * 异常信息
     */
    private String exceptionInfo;
}
