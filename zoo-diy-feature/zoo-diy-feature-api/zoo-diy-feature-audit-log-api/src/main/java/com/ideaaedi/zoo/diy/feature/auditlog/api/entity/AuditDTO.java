package com.ideaaedi.zoo.diy.feature.auditlog.api.entity;

import com.ideaaedi.zoo.commonbase.zoo_entity.MoreDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 审计日志
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class AuditDTO extends MoreDTO {
    
    /**
     * 审计日志id
     */
    private Long id;
    
    /**
     * 审计日志父id
     */
    private Long pid = 0L;
    
    /**
     * 日志追踪标识
     * <p>
     * e.g. 108d5ac2c946d78e542704538014675a
     * </p>
     */
    private String traceId;
    
    /**
     * 带业务信息的日志追踪标识
     * <p>
     * e.g. 108d5ac2c946d78e542704538014675a_1_api.user.list
     * </p>
     */
    private String traceXd;
    
    /**
     * 用户id
     */
    private Long userId;
    
    /**
     * 用户名
     */
    private String userName;
    
    /**
     * 操作发生时间
     */
    private LocalDateTime operationTime;
    
    /**
     * 操作类型
     * <p>
     * e.g. UPDATE
     * </p>
     */
    private String operationType;
    
    /**
     * 操作描述
     * <p>
     * e.g. 张三修改了李四的性别
     * </p>
     */
    private String operationDesc;
    
    /**
     * 客户端ip地址
     * <p>
     * e.g. 192.168.1.1
     * </p>
     */
    private String clientIpAddress;
    
    /**
     * 客户端代理信息（请求头中的用户代理信息）
     * <p>
     * e.g. Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36
     * </p>
     */
    private String clientUserAgent;
    
    /**
     * 系统上下文信息
     */
    private Map<String, Object> contextInfo;
    
    /**
     * 请求的HTTP方法
     */
    private String requestMethod;
    
    /**
     * 请求参数
     */
    private Map<String, Object> requestParamMap;
    
    /**
     * 请求的URI
     * <p>
     * e.g. /api/users/123
     * </p>
     */
    private String requestUri;
    
    /**
     * 响应结果
     */
    private Object responseResult;
    
    /**
     * 请求是否成功
     */
    private Boolean ifSuccess;
    
    /**
     * 错误信息
     */
    private String exceptionInfo;
    
}
