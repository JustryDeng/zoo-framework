package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-菜单表 batch add req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-菜单表 batch main add req")
public class SysMenuBatchAddMainReqVO extends SysMenuBatchAddItemReqVO {
    
    /**
     * 父菜单id（无则填0）
     */
    @NotNull(message = "父菜单id不能为空")
    @Schema(description = "父菜单id（无则填0）")
    private Long pid;
}