package com.ideaaedi.zoo.commonbase.entity.excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * excel导出列 cell值映射信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class ExcelExportValueMappingDTO {
    
    @Schema(description = "原值")
    private String source;
    
    @Schema(description = "映射的值")
    private String target;
}
