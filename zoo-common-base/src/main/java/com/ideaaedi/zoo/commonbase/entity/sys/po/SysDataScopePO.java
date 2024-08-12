package com.ideaaedi.zoo.commonbase.entity.sys.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ideaaedi.zoo.commonbase.enums.sys.DataScopeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统-数据操作范围 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-数据操作范围")
@TableName(value = "sys_data_scope", autoResultMap = true)
public class SysDataScopePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "操作类型（READ-查询数据；UPDATE-修改/删除数据）")
    @TableField("type")
    private DataScopeTypeEnum type;

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "部门id")
    @TableField("dept_id")
    private Integer deptId;

    @Schema(description = "部门路径变更前的历史数据是否可见（1-可见；0-不可见）")
    @TableField("dept_his_visible")
    private Integer deptHisVisible;
    
    @Schema(description = "指定应用的api集合（为空则表示应用于所有api）")
//    @TableField(value = "api_ids", typeHandler = JacksonTypeHandler.class) todo
    @TableField(value = "api_ids")
    private List<Long> apiIds;

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;

    @Schema(description = "是否已删除(0-未删除；1-已删除)")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @Schema(description = "创建人")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Schema(description = "修改人")
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    @Schema(description = "修改时间")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}