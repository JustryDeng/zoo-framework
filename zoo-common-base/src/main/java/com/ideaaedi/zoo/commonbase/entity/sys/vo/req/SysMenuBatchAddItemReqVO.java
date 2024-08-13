package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * 菜单增项内容
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysMenuBatchAddItemReqVO {
    
    /**
     * 类型（1-普通菜单）
     */
    @Schema(description = "类型（1-普通菜单）")
    private Integer type;
    
    /**
     * 菜单编码
     */
    @NotBlank(message = "菜单编码不能为空")
    @Schema(description = "菜单编码")
    private String code;
    
    /**
     * 菜单名
     */
    @NotBlank(message = "菜单名不能为空")
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
     * （1-可见;0-不可见）
     */
    @Min(value = 0)
    @Max(value = 1)
    @Schema(description = "是否可见（1-可见;0-不可见）")
    private Integer visible;
    
    /**
     * 菜单备注说明
     */
    @Schema(description = "菜单备注说明")
    private String remark;
    
    /**
     * 子菜单
     */
    @Valid
    @Schema(description = "子菜单")
    private List<SysMenuBatchAddItemReqVO> children;
}
