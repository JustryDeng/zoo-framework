package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import com.ideaaedi.zoo.commonbase.enums.sys.DeptTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-组织架构表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-组织架构表 update req")
public class SysDeptUpdateReqVO extends BaseDTO {

    /**
     * id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "id")
    private Integer id;

    /**
     * 父部门id（无父部们则填0. 不修改则传原值或null）
     */
    @Schema(description = "父部门id（无父部们则填0. 不修改则传原值或null）")
    private Integer pid;

    /**
     * 编码（设计上允许重复）
     */
    @Schema(description = "部门编码（设计上允许重复）")
    private String code;

    /**
     * 部门名称
     */
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