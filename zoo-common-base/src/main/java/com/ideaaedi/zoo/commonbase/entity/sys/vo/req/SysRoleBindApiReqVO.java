package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * 给角色绑定api资源
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysRoleBindApiReqVO {
    
    /**
     * 角色id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "角色id")
    private Long roleId;
    
    /**
     * api资源id集合
     */
    @NotNull(message = "api资源id不能为null")
    @Size(min = 1, message = "api资源id不能为空")
    @Schema(description = "api资源id集合")
    private Set<Long> apiIds;
}
