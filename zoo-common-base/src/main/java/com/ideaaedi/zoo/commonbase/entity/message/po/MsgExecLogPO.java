package com.ideaaedi.zoo.commonbase.entity.message.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息-执行日志表 po模型
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@TableName("msg_exec_log")
@Schema(description = "消息-执行日志表")
public class MsgExecLogPO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId("id")
    private Long id;

    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;

    @Schema(description = "消息的年月日")
    @TableField("yyyy_mm_dd")
    private String yyyyMmDd;

    @Schema(description = "消息 id")
    @TableField("msg_id")
    private Long msgId;

    @Schema(description = "触发本次执行的期望执行时间")
    @TableField("expect_exec_time")
    private LocalDateTime expectExecTime;

    @Schema(description = "实际执行时间")
    @TableField("actual_exec_time")
    private LocalDateTime actualExecTime;

    @Schema(description = "耗时（毫秒）")
    @TableField("consume_time")
    private Integer consumeTime;

    @Schema(description = "执行是否成功（仅代表执行这个动作是否成功；0-未知；1-成功；2-失败）")
    @TableField("exec_if_success")
    private Integer execIfSuccess;

    @Schema(description = "执行结果")
    @TableField("exec_result")
    private String execResult;

    @Schema(description = "是否已删除(0-未删除；1-已删除)")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;

    @Schema(description = "创建人")
    @TableField(value = "created_by", fill = FieldFill.INSERT)
    private Long createdBy;

    @Schema(description = "创建时间")
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @Schema(description = "修改人")
    @TableField(value = "updated_by", fill = FieldFill.INSERT_UPDATE)
    private Long updatedBy;

    @Schema(description = "修改时间")
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}