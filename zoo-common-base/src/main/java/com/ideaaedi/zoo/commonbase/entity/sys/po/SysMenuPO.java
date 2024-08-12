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
 * 系统-菜单表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("sys_menu")
@Schema(description = "系统-菜单表")
public class SysMenuPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "父菜单id（无则填0）")
    @TableField("pid")
    private Long pid;

    @Schema(description = "类型（1-普通菜单）")
    @TableField("type")
    private Integer type;

    @Schema(description = "菜单编码")
    @TableField("code")
    private String code;

    @Schema(description = "菜单名")
    @TableField("name")
    private String name;

    @Schema(description = "菜单路径（从最顶级菜单到当前菜单的路径）")
    @TableField("path")
    private String path;

    @Schema(description = "同一级菜单的排序优先级（越小越靠前）")
    @TableField("sort")
    private Integer sort;

    @Schema(description = "前端相关配置 - 菜单图标")
    @TableField("icon")
    private String icon;

    @Schema(description = "前端相关配置 - 组件路径")
    @TableField("component")
    private String component;

    @Schema(description = "前端相关配置 - 组件名")
    @TableField("component_name")
    private String componentName;

    @Schema(description = "是否可见")
    @TableField("visible")
    private Integer visible;

    @Schema(description = "菜单备注说明")
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