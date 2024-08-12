package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-数据字典类别表 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-数据字典类别表 update req")
public class SysDictTypeUpdateReqVO extends BaseDTO {

    /**
     * 字典类型id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "字典类型id")
    private Integer id;

    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码（全局唯一）")
    private String code;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    private String name;
    
    /**
     * 排序（越小越排前面）
     */
    @Schema(description = "排序（越小越排前面）")
    private Integer sort;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}