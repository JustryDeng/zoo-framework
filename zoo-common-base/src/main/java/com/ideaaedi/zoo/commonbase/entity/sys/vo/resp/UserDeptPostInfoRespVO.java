package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 部门信息 - resp
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class UserDeptPostInfoRespVO {
    
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Long userId;
    
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Integer deptId;
    
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;
    
    /**
     * 职位id
     */
    @Schema(description = "职位id")
    private Long postId;
    
    /**
     * 职位名称
     */
    @Schema(description = "职位名称")
    private String postName;
    
    /**
     * 部门id路径
     */
    @Schema(description = "部门id路径")
    private String deptIdPath;
    
    /**
     * 部门名路径
     */
    @Schema(description = "部门名路径")
    private String deptPathName;
}
