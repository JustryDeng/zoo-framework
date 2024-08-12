package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统-业务租户表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-业务租户表 list resp")
public class SysTenantListRespVO {
    
    /**
     * id
     */
    @Schema(description = "id")
    private Long id;
    
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Integer deptId;
    
    /**
     * 编码
     */
    @Schema(description = "编码")
    private String deptCode;
    
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    
}