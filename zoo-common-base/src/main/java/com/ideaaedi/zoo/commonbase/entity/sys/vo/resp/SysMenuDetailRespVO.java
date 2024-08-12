package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统-菜单表 detail resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-菜单表 detail resp")
public class SysMenuDetailRespVO {
    
    /**
     * id
     */
    @Schema(description = "id")
    private Long id;
    
    /**
     * 租户编码
     */
    @Schema(description = "租户编码")
    private String tenant;
    
    /**
     * 所属业务租户的名称
     */
    @Schema(description = "所属业务租户的名称")
    private String tenantName;
    
    /**
     * 父菜单id（无则填0）
     */
    @Schema(description = "父菜单id（无则填0）")
    private Long pid;
    
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
     * 菜单路径（从最顶级菜单到当前菜单的路径）
     */
    @Schema(description = "菜单路径（从最顶级菜单到当前菜单的路径）")
    private String path;
    
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
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedAt;
    
}