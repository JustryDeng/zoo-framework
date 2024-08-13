package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ideaaedi.zoo.commonbase.enums.sys.DataScopeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统-数据操作范围 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-数据操作范围 list resp")
public class SysDataScopeListRespVO {
    
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
     * 操作类型
     */
    @Schema(description = "操作类型")
    @ExcelProperty("操作类型")
    private DataScopeTypeEnum type;
    
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @ExcelProperty("用户id")
    private Long userId;
    
    /**
     * 部门id
     */
    @Schema(description = "部门id")
    @ExcelProperty("部门id")
    private Integer deptId;
    
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @ExcelProperty("用户名")
    private String username;
    
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @ExcelProperty("部门名称")
    private String deptName;
    
    /**
     * 部门id路径
     */
    @Schema(description = "部门id路径")
    @ExcelProperty("部门id路径")
    private String deptPath;
    
    /**
     * 部门路径变更前的历史数据是否可见（1-可见；0-不可见）
     */
    @Schema(description = "部门路径变更前的历史数据是否可见（1-可见；0-不可见）")
    @TableField("dept_his_visible")
    private Integer deptHisVisible;
    
    /**
     * 指定应用的api集合（为null或空则表示应用于所有api）
     */
    @ExcelIgnore
    @Schema(description = "指定应用的api集合（为null或空则表示应用于所有api）", hidden = true)
    private List<Long> apiIds;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createdAt;
    
}