package com.ideaaedi.zoo.commonbase.entity.excel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * excel导出动态头
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class ExcelExportDynamicHeaderDTO {
    
    /** excel头 */
    @Schema(description = "excel头")
    private String header;
    
    /** 占位符 */
    @Schema(description = "占位符")
    private String placeholder;
    
    @Schema(description = "cell值映射", hidden = true)
    private List<ExcelExportValueMappingDTO> cellValueMappingList;
    
    /**
     * 快速创建
     */
    public static ExcelExportDynamicHeaderDTO create(String header, String fieldName) {
        return create(header, fieldName, null);
    }
    
    /**
     * 快速创建
     */
    public static ExcelExportDynamicHeaderDTO create(String header, String fieldName, List<ExcelExportValueMappingDTO> cellValueMappingList) {
        ExcelExportDynamicHeaderDTO dynamicHeader = new ExcelExportDynamicHeaderDTO();
        dynamicHeader.setHeader(header);
        dynamicHeader.setPlaceholder("{." + fieldName + "}");
        dynamicHeader.setCellValueMappingList(cellValueMappingList);
        return dynamicHeader;
    }
}
