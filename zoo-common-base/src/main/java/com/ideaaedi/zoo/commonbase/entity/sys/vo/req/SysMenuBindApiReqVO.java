package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.fasterxml.jackson.annotation.JsonAlias;
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
public class SysMenuBindApiReqVO {
    
    /**
     * 菜单id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "菜单id")
    private Long menuId;
    
    /**
     * api资源id集合
     */
    @JsonAlias(value = {"endpointIds", "apiIds"})
    @Schema(description = "api资源id集合")
    private Set<Long> apiIds;
}
