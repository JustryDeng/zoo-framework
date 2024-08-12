package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统-职位表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-职位表 list resp")
public class SysPostListRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    @ExcelProperty("id")
    private Long id;

    /**
     * 租户
     */
    @Schema(description = "租户")
    @ExcelProperty("租户")
    private String tenant;

    /**
     * 职位编码
     */
    @Schema(description = "职位编码")
    @ExcelProperty("职位编码")
    private String code;

    /**
     * 职位名称
     */
    @Schema(description = "职位名称")
    @ExcelProperty("职位名称")
    private String name;

    /**
     * 职级
     */
    @Schema(description = "职级")
    @ExcelProperty("职级")
    private Integer grade;

    /**
     * 排序（同职级，sort越小越排前面）
     */
    @Schema(description = "排序（同职级，sort越小越排前面）")
    @ExcelProperty("排序（同职级，sort越小越排前面）")
    private Integer sort;

    /**
     * 状态(1-正常；2-禁用)
     */
    @Schema(description = "状态(1-正常；2-禁用)")
    @ExcelProperty("状态(1-正常；2-禁用)")
    private Integer state;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    /**
     * 是否已删除(0-未删除；1-已删除)
     */
    @Schema(description = "是否已删除(0-未删除；1-已删除)")
    @ExcelProperty("是否已删除(0-未删除；1-已删除)")
    private Integer deleted;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @ExcelProperty("创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createdAt;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    @ExcelProperty("修改人")
    private Long updatedBy;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @ExcelProperty("修改时间")
    private LocalDateTime updatedAt;

}