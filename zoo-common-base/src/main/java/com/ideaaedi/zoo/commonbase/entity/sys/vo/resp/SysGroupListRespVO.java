package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统-分组表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-分组表 list resp")
public class SysGroupListRespVO {

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
     * 类型
     */
    @Schema(description = "类型")
    @ExcelProperty("类型")
    private String type;

    /**
     * 名称
     */
    @Schema(description = "名称")
    @ExcelProperty("名称")
    private String name;

    /**
     * 编码
     */
    @Schema(description = "编码")
    @ExcelProperty("编码")
    private String code;

    /**
     * 排序（同type，sort越小越排前面）
     */
    @Schema(description = "排序（同type，sort越小越排前面）")
    @ExcelProperty("排序（同type，sort越小越排前面）")
    private Integer sort;

    /**
     * 关联的部门id
     */
    @Schema(description = "关联的部门id")
    @ExcelProperty("关联的部门id")
    private Integer deptId;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    /**
     * 状态(1-正常)
     */
    @Schema(description = "状态(1-正常)")
    @ExcelProperty("状态(1-正常)")
    private Integer state;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @ExcelProperty("修改时间")
    private LocalDateTime updatedAt;

}