package com.ideaaedi.zoo.commonbase.entity.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门id & 职位id
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class DeptIdPostIdDTO {
    
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Integer deptId;
    
    /**
     * 职位id
     */
    @Schema(description = "职位id")
    private Long postId;
    
}
