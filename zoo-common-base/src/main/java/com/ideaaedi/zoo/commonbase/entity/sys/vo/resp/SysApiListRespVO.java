package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统-api资源
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysApiListRespVO {
    
    /**
     * id
     */
    @Schema(description = "资源id")
    private Long id;
    
    /**
     * 资源名
     */
    @Schema(description = "资源名")
    private String name;
    
    /**
     * 资源路径
     */
    @Schema(description = "资源路径")
    private String path;
    
    /**
     * 请求该资源所需要的方法(多个之间使用逗号分割)
     */
    @Schema(description = "请求该资源所需要的方法(多个之间使用逗号分割)")
    private String requestMethod;
    
    /**
     * 所属微服务
     */
    @Schema(description = "所属微服务")
    private String microService;
    
    /**
     * 资源备注说明
     */
    @Schema(description = "资源备注说明")
    private String remark;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedAt;

}
