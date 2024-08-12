package com.ideaaedi.zoo.commonbase.entity.excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * excel导出列信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class ExcelExportColumnDTO {
    
    @Schema(description = "列名")
    private String name;
    
    @Schema(description = "字段名")
    private String field;
    
    @Schema(description = "cell值映射（注：只支持对原数据类型为STRING、NUMBER、BOOLEAN的值进行映射）")
    private List<ExcelExportValueMappingDTO> cellValueMappingList;
}
