package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-角色表 batch add resp")
public class SysRoleBatchAddReqVO extends BaseDTO {

    /**
     * 是否逐个处理
     */
    @NotNull(message = "ifOneByOne cannot be null")
    @Schema(description = "是否逐个处理(true-逐个处理, 其中失败的不影响成功的; false-统一处理, 要么全成功，要么全失败)")
    private Boolean ifOneByOne;

    /**
     * 数据集
     */
    @Valid
    @Schema(description = "数据集")
    @NotNull(message = "reqList cannot be null")
    @Size(min = 1, message = "reqList cannot be empty")
    private List<AddItemParamDTO> dataList;

    /**
    * @see com.ideaaedi.zoo.commonbase.entity.sys.req.SysRoleAddReqVO
    */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class AddItemParamDTO extends SysRoleAddReqVO {

        /**
         * 是否成功
         */
        @Schema(description = "是否成功")
        private Boolean result;

        /**
         * 失败原因
         */
        @Schema(description = "失败原因")
        private String failInfo;
    }
}