package com.ideaaedi.zoo.commonbase.entity.sys.bo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 系统-菜单表（资源组表）
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class BriefMenuBO  {

    /**
     * id
     */
    @TableId("id")
    private Long id;

    /**
     * 父菜单id
     */
    @TableField("pid")
    private Integer pid;
    
    /**
     * 菜单编码
     */
    @TableField("code")
    private String code;
    
    /**
     * 菜单名
     */
    @TableField("name")
    private String name;

    /**
     * 菜单路径（从最顶级菜单到当前菜单的路径）
     */
    @TableField("path")
    private String path;

    /**
     * 同一级菜单的排序优先级（越小越靠前）
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 菜单展示模式（1-展示； 2-不展示）
     */
    @TableField("display_mode")
    private Integer displayMode;

}
