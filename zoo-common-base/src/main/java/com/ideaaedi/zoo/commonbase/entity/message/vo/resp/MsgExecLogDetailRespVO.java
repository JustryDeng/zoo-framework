package com.ideaaedi.zoo.commonbase.entity.message.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 消息-执行日志表 detail resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "消息-执行日志表 detail resp")
public class MsgExecLogDetailRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;

    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;

    /**
     * 消息的年月日
     */
    @Schema(description = "消息的年月日")
    private String yyyyMmDd;

    /**
     * 消息 id
     */
    @Schema(description = "消息 id")
    private Long msgId;

    /**
     * 触发本次执行的期望执行时间
     */
    @Schema(description = "触发本次执行的期望执行时间")
    private LocalDateTime expectExecTime;

    /**
     * 实际执行时间
     */
    @Schema(description = "实际执行时间")
    private LocalDateTime actualExecTime;

    /**
     * 耗时（毫秒）
     */
    @Schema(description = "耗时（毫秒）")
    private Integer consumeTime;

    /**
     * 执行是否成功（仅代表执行这个动作是否成功；0-未知；1-成功；2-失败）
     */
    @Schema(description = "执行是否成功（仅代表执行这个动作是否成功；0-未知；1-成功；2-失败）")
    private Integer execIfSuccess;

    /**
     * 执行结果
     */
    @Schema(description = "执行结果")
    private String execResult;

    /**
     * 是否已删除(0-未删除；1-已删除)
     */
    @Schema(description = "是否已删除(0-未删除；1-已删除)")
    private Integer deleted;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private Long updatedBy;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedAt;

}