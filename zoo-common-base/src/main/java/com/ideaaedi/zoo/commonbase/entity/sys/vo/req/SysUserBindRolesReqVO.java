package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2022/9/8 16:10:40
 */
@Data
public class SysUserBindRolesReqVO {
    
    /**
     * 用户id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "用户id")
    private Long userId;
    
    /**
     * 角色id集合
     */
    @Schema(description = "角色id集合")
    private Set<Long> roleIds;
}
