package com.ideaaedi.zoo.commonbase.entity;

import com.ideaaedi.zoo.commonbase.entity.excel.ExcelExportSettings;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * base page
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class BasePageDTO extends BaseDTO {
    
    /**
     * 页码
     */
    @Schema(description = "页码")
    private Integer pageNum = 1;
    
    /**
     * 每页条数（小于0时，会忽略pageNum进行全查）
     */
    @Schema(description = "每页条数（小于0时，会忽略pageNum进行全查）")
    private Integer pageSize = 10;
    
    /**
     * excel导出设置
     */
    @Schema(description = "excel导出设置", hidden = true)
    private ExcelExportSettings excelExportSettings;
}
