package com.ideaaedi.zoo.commonbase.entity.excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * excel导出设置
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.1.F
 */
@Data
@Schema(description = "excel导出设置")
public class ExcelExportSettings implements Serializable {
    
    /**
     * 是否对查询结果进行excel导出
     */
    @Schema(description = "是否对查询结果进行excel导出")
    private boolean excelExportEnable = false;
    
    /**
     * 是否以url的形式返回导出的文件（true-以url的形式返回；false-以响应流的形式返回）
     */
    @Schema(description = "是否以url的形式返回导出的文件（true-以url的形式返回；false(默认)-以响应流的形式返回） 注：为true时，服务端会额外存储导出的excel文件")
    private boolean returnExcelFileByUrl = false;
    
    /** 指定导出的excel文件名(不需要带后缀) */
    @Schema(description = "指定导出的excel文件名(不需要带后缀)")
    private String excelFilename;
    
    /** 指定sheet名（默认为sheet） */
    @Schema(description = "指定sheet名（默认为sheet）")
    private String sheetName = "sheet";
    
    /** 要导出的列 */
    @Schema(description = "要导出的列")
    private List<ExcelExportColumnDTO> includeColumnFieldList;
    
    /** 不导出的字段名集合 */
    @Schema(description = "不导出的字段名集合")
    private List<String> excludeColumnFiledNames;
}
