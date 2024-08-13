package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统-数据字典类别表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-数据字典类别表 list resp")
public class SysDictTypeListRespVO {

    /**
     * 字典类型id
     */
    @Schema(description = "字典类型id")
    @ExcelProperty("字典类型id")
    private Integer id;
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    @TableField("tenant")
    private String tenant;
    
    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码（全局唯一）")
    @ExcelProperty("字典类型编码")
    private String code;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    @ExcelProperty("字典类型名称")
    private String name;

    /**
     * 备注
     */
    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

}