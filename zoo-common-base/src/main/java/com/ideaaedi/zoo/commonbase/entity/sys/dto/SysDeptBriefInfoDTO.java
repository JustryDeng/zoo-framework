package com.ideaaedi.zoo.commonbase.entity.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统-组织架构 brief INFO
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysDeptBriefInfoDTO {
    
    /**
     * 父部门id（无父部们则填0）
     */
    @Schema(description = "父部门id（无父部们则填0）")
    private Integer pid;
    
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Integer id;
    
    /**
     * 部门编码
     */
    @Schema(description = "编码")
    private String code;
    
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String name;
    
    /**
     * 部门路径
     */
    @Schema(description = "部门路径")
    private String path;
    
    /**
     * 部门路径名称
     */
    @Schema(description = "部门路径名称")
    private String pathName;
}