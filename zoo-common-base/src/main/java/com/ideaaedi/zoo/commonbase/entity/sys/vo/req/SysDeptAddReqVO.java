package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import com.ideaaedi.zoo.commonbase.enums.sys.DeptTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-组织架构表 add req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-组织架构表 add req")
public class SysDeptAddReqVO extends BaseDTO {

    /**
     * 父部门id（无父部们则填0）
     */
    @NotNull(message = "父部门不能为空")
    @Schema(description = "父部门id（无父部们则填0）")
    private Integer pid;

    /**
     * 编码（设计上允许重复）
     */
    @Schema(description = "编码（设计上允许重复）")
    private String code;

    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    @Schema(description = "部门名称")
    private String name;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private DeptTypeEnum type;

    /**
     * 排序（同级部门，sort越小越排前面）
     */
    @Schema(description = "排序（同级部门，sort越小越排前面）")
    private Integer sort;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}