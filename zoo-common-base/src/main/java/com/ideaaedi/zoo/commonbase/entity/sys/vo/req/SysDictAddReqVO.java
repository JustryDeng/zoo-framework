package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统-数据字典表 add req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-数据字典表 add req")
public class SysDictAddReqVO extends BaseDTO {

    /**
     * 所属字典类型
     */
    @NotNull(message = "所属字典类型不能为空")
    @Schema(description = "所属字典类型")
    private Integer dictTypeId;

    /**
     * 字典编码
     */
    @NotBlank(message = "字典编码不能为空")
    @Schema(description = "字典编码")
    private String code;

    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    @Schema(description = "字典名称")
    private String name;
    
    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String value;
    
    /**
     * 字典值json扩展
     */
    @Schema(description = "字典值json扩展")
    private String valueExt;
    
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