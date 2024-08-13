package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * 查询角色拥有的api req
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysRoleQueryApiReqVO {

    @NotNull(message = "角色id不能为null")
    @Size(min = 1, message = "角色id不能为空")
    @Schema(description = "角色id集合")
    private Set<Long> roleIds;
    
    @Schema(description = "是否只查询直接与角色绑定的api，默认为false"
            + "<ul> "
            + "<li>true：只查询与角色直接绑定的api </li>"
            + "<li>false：查询与角色直接绑定的api，同时也查询通过菜单与角色间接绑定的api </li>"
            + "<ul>")
    private Boolean onlyQueryDirectBoundApi;
    
}
