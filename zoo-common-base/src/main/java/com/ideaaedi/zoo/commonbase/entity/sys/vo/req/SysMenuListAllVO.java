package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-菜单表（资源组表）
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuListAllVO extends BasePageDTO {

    /**
     * 父菜单id
     */
    @Schema(description = "父菜单id")
    private Long pid;
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
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

}
