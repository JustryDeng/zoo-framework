package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * <p>
 * 系统-角色表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-角色表 update req")
public class SysRoleUpdateReqVO extends BaseDTO {

    /**
     * id
     */
    @NotNull(message = "角色id不能为空")
    @Schema(description = "id")
    private Long id;

    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String code;
    
    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色类型
     */
    @Schema(description = "角色类型")
    private Integer type;
    
    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 状态(1-正常；2-禁用)
     */
    @Schema(description = "状态(1-正常；2-禁用)")
    private Integer state;
    
    /**
     * 可访问的菜单id集合
     */
    @Schema(description = "可访问的菜单id集合", $comment = "为null表示不修改，为空集合表示清空")
    private Set<Long> menusIds;
    
    /**
     * 可访问的api id集合
     */
    @Schema(description = "可访问的api id集合", $comment = "为null表示不修改，为空集合表示清空")
    private Set<Long> apiIds;
}