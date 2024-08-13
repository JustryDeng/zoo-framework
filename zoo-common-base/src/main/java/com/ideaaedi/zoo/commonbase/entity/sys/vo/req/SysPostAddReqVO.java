package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-职位表 add req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-职位表 add req")
public class SysPostAddReqVO extends BaseDTO {

    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;

    /**
     * 职位编码
     */
    @NotNull(message = "职位编码不能为空")
    @Schema(description = "职位编码")
    private String code;

    /**
     * 职位名称
     */
    @NotNull(message = "职位名称不能为空")
    @Schema(description = "职位名称")
    private String name;

    /**
     * 职级
     */
    @Schema(description = "职级")
    private Integer grade;

    /**
     * 排序（同职级，sort越小越排前面）
     */
    @Schema(description = "排序（同职级，sort越小越排前面）")
    private Integer sort;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}