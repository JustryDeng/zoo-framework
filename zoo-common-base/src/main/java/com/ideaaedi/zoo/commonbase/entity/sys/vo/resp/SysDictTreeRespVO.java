package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统-数据字典 tree resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysDictTreeRespVO {
    
    /**
     * 字典id
     */
    @Schema(description = "字典id")
    private Integer id;
    
    /**
     * 字典类型id
     */
    @Schema(description = "字典类型id")
    private Integer dictTypeId;
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码（全局唯一）")
    private String dictTypeCode;
    
    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    private String dictTypeName;
    
    /**
     * 字典路径(格式为：类型id_字典id)
     */
    @Schema(description = "字典路径(格式为：类型id_字典id)")
    private String dictPath;

    /**
     * 字典编码
     */
    @Schema(description = "字典编码")
    private String dictCode;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String dictName;
    
    /**
     * 字典值
     */
    @Schema(description = "字典值")
    private String dictValue;
    
    /**
     * 字典值json扩展
     */
    @Schema(description = "字典值json扩展")
    private String dictValueExt;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}