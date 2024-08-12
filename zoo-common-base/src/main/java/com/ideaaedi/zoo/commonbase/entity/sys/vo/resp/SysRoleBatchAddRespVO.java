package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.ideaaedi.zoo.commonbase.entity.sys.vo.req.SysRoleBatchAddReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 系统-角色表 batch-add resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-角色表 batch add resp")
public class SysRoleBatchAddRespVO {

    /**
     * 是否全部成功(true-全部成功; false-全部失败或部分失败)
     */
    @Schema(description = "是否全部成功(true-全部成功; false-全部失败或部分失败)")
    private Boolean ifAllSuccess;

    /**
     * 数据集
     */
    @Schema(description = "数据集")
    private List<SysRoleBatchAddReqVO.AddItemParamDTO> dataList;
}