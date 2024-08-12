package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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
@Schema(description = "系统-菜单表 batch add req")
public class SysMenuBatchAddReqVO extends BaseDTO {
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
    /**
     * 菜单集合
     */
    @Valid
    @NotNull(message = "菜单不能为null")
    @Size(min = 1, message = "菜单不能为空")
    @Schema(description = "菜单集合")
    private List<SysMenuBatchAddMainReqVO> meneList;
}