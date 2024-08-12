package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统-角色表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-角色表 list resp")
public class SysRoleListRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    @ExcelProperty("id")
    private Long id;
    
    /**
     * 租户编码
     */
    @Schema(description = "租户编码")
    @ExcelProperty("租户编码")
    private String tenant;
    
    /**
     * 所属业务租户的名称
     */
    @Schema(description = "所属业务租户的名称")
    @ExcelProperty("所属业务租户的名称")
    private String tenantName;
    
    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    @ExcelProperty("角色编码")
    private String code;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    @ExcelProperty("角色名称")
    private String name;

    /**
     * 角色类型
     */
    @Schema(description = "角色类型")
    @ExcelProperty("角色类型")
    private Integer type;
    
    /**
     * 排序
     */
    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    /**
     * 状态(1-正常；2-禁用)
     */
    @Schema(description = "状态(1-正常；2-禁用)")
    @ExcelProperty("状态(1-正常；2-禁用)")
    private Integer state;
    
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createdBy;
    
    /**
     * 创建人姓名
     */
    @Schema(description = "创建人姓名")
    private String createdByName;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
}