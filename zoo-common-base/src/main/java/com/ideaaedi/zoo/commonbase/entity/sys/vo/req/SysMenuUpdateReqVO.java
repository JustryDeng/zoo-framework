package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-菜单表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-菜单表 update req")
public class SysMenuUpdateReqVO extends BaseDTO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    private Long id;
    
    /**
     * 父菜单id（无则填0. 不修改则传原值或null）
     */
    @Schema(description = "父菜单id（无则填0. 不修改则传原值或null）")
    private Long pid;
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;

    /**
     * 类型（1-普通菜单）
     */
    @Schema(description = "类型（1-普通菜单）")
    private Integer type;

    /**
     * 菜单编码
     */
    @Schema(description = "菜单编码")
    private String code;

    /**
     * 菜单名
     */
    @Schema(description = "菜单名")
    private String name;

    /**
     * 同一级菜单的排序优先级（越小越靠前）
     */
    @Schema(description = "同一级菜单的排序优先级（越小越靠前）")
    private Integer sort;

    /**
     * 前端相关配置 - 菜单图标
     */
    @Schema(description = "前端相关配置 - 菜单图标")
    private String icon;

    /**
     * 前端相关配置 - 组件路径
     */
    @Schema(description = "前端相关配置 - 组件路径")
    private String component;

    /**
     * 前端相关配置 - 组件名
     */
    @Schema(description = "前端相关配置 - 组件名")
    private String componentName;

    /**
     * 是否可见
     */
    @Schema(description = "是否可见")
    private Integer visible;

    /**
     * 菜单备注说明
     */
    @Schema(description = "菜单备注说明")
    private String remark;
}