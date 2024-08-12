package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-职位表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-职位表 update req")
public class SysPostUpdateReqVO extends BaseDTO {

    /**
     * id
     */
    @NotNull(message = "职位id不能为空")
    @Schema(description = "id")
    private Long id;

    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;

    /**
     * 职位编码
     */
    @Schema(description = "职位编码")
    private String code;

    /**
     * 职位名称
     */
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
     * 状态(1-正常；2-禁用)
     */
    @Schema(description = "状态(1-正常；2-禁用)")
    private Integer state;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}