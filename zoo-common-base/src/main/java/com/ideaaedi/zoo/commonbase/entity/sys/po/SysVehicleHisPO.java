package com.ideaaedi.zoo.commonbase.entity.sys.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ideaaedi.zoo.commonbase.enums.sys.VehicleTypeEnum;
import com.ideaaedi.zoo.commonbase.his.HisIdentify;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统-车辆历史表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("sys_vehicle_his")
@Schema(description = "系统-车辆历史表")
public class SysVehicleHisPO implements HisIdentify, Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "历史记录id（主键id）")
    @TableId(value = "his_id", type = IdType.AUTO)
    private Integer hisId;

    @Schema(description = "版本（格式为yyyyMMddHHmmss）")
    @TableField("his_version")
    private Long hisVersion;

    @Schema(description = "id")
    @TableField("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "类别")
    @TableField("type")
    private VehicleTypeEnum type;

    @Schema(description = "所属组织")
    @TableField("dept_id")
    private Integer deptId;

    @Schema(description = "所属用户")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "车牌号")
    @TableField("plate_no")
    private String plateNo;

    @Schema(description = "车牌颜色")
    @TableField("plate_color")
    private Integer plateColor;

    @Schema(description = "状态(1-正常)")
    @TableField("state")
    private Integer state;

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