package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-分组表 list req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-分组表 list req")
public class SysGroupListReqVO extends BasePageDTO {

    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;

    /**
     * 类型
     */
    @Schema(description = "类型")
    private String type;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 编码
     */
    @Schema(description = "编码")
    private String code;

    /**
     * 关联的部门id
     */
    @Schema(description = "关联的部门id")
    private Integer deptId;

    /**
     * 状态(1-正常)
     */
    @Schema(description = "状态(1-正常)")
    private Integer state;

}