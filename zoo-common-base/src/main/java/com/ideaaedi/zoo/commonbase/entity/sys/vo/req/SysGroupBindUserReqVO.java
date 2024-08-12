package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

/**
 * 给分组绑定用户
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysGroupBindUserReqVO {
    
    /**
     * 分组id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "分组id")
    private Long groupId;
    
    /**
     * 用户id集合
     */
    @NotNull(message = "用户id不能为null")
    @Size(min = 1, message = "用户id不能为空")
    @Schema(description = "用户id集合")
    private Set<Long> userIds;
}
