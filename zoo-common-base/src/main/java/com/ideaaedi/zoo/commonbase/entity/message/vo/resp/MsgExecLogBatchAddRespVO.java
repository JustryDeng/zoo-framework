package com.ideaaedi.zoo.commonbase.entity.message.vo.resp;

import com.ideaaedi.zoo.commonbase.entity.message.vo.req.MsgExecLogBatchAddReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 消息-执行日志表 batch-add resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "消息-执行日志表 batch add resp")
public class MsgExecLogBatchAddRespVO {

    /**
     * 是否全部成功(true-全部成功; false-全部失败或部分失败)
     */
    @Schema(description = "是否全部成功(true-全部成功; false-全部失败或部分失败)")
    private Boolean ifAllSuccess;

    /**
     * 数据集
     */
    @Schema(description = "数据集")
    private List<MsgExecLogBatchAddReqVO.AddItemParamDTO> dataList;
}