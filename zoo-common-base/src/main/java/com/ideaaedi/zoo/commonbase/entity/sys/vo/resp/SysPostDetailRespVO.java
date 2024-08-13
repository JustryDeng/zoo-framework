package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统-职位表 detail resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-职位表 detail resp")
public class SysPostDetailRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

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