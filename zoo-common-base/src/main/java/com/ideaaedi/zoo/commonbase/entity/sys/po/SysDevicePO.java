package com.ideaaedi.zoo.commonbase.entity.sys.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统-设备总表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("sys_device")
@Schema(description = "系统-设备总表")
public class SysDevicePO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "所属部门id")
    @TableField("dept_id")
    private Integer deptId;

    @Schema(description = "所属用户id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "类别")
    @TableField("type")
    private Integer type;

    @Schema(description = "型号")
    @TableField("model")
    private String model;

    @Schema(description = "序列号")
    @TableField("serial_no")
    private String serialNo;

    @Schema(description = "设备厂商")
    @TableField("manufacturer")
    private String manufacturer;

    @Schema(description = "设备厂商code")
    @TableField("manufacturer_code")
    private String manufacturerCode;

    @Schema(description = "状态")
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