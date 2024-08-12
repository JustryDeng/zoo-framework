package com.ideaaedi.zoo.commonbase.feign;

import lombok.Data;

import java.util.LinkedHashSet;

/**
 * 跨微服务请求 携带的数据DTO
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class CrossMicroServiceTransDataDTO {
    
    /**
     * 用户id
     */
    private Long userId;
    
    /**
     * 租户
     */
    private String tenant;
    
    /**
     * 可读数据范围的deptId对应的deptPath
     */
    private LinkedHashSet<String> readDataScopePaths;
    
    /**
     * 可写(修改/删除)数据范围的deptId对应的deptPath
     */
    private LinkedHashSet<String> updateDataScopePaths;
    
    /**
     * 当前请求来自哪个微服务
     */
    private String fromMicroService;
}
