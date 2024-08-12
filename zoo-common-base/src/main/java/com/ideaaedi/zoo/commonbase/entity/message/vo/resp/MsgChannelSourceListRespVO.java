package com.ideaaedi.zoo.commonbase.entity.message.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息-入方向渠道表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "消息-入方向渠道表 list resp")
public class MsgChannelSourceListRespVO {

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
     * auth信息
     */
    @Schema(description = "auth信息")
    @ExcelProperty("auth信息")
    private Long authId;

    /**
     * 渠道名称
     */
    @Schema(description = "渠道名称")
    @ExcelProperty("渠道名称")
    private String name;

    /**
     * 渠道编码
     */
    @Schema(description = "渠道编码")
    @ExcelProperty("渠道编码")
    private String code;

    /**
     * 渠道状态(1-正常；2-禁用)
     */
    @Schema(description = "渠道状态(1-正常；2-禁用)")
    @ExcelProperty("渠道状态(1-正常；2-禁用)")
    private Integer state;

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