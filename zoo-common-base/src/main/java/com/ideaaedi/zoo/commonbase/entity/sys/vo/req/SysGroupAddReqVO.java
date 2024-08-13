package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-分组表 add req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-分组表 add req")
public class SysGroupAddReqVO extends BaseDTO {

    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空")
    @Schema(description = "类型")
    private String type;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空")
    @Schema(description = "名称")
    private String name;

    /**
     * 编码
     */
    @NotBlank(message = "编码不能为空")
    @Schema(description = "编码")
    private String code;

    /**
     * 排序（同type，sort越小越排前面）
     */
    @Schema(description = "排序（同type，sort越小越排前面）")
    private Integer sort;

    /**
     * 关联的部门id
     */
    @Schema(description = "关联的部门id")
    private Integer deptId;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}