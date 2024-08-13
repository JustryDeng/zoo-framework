package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ideaaedi.zoo.commonbase.entity.sys.bo.DataScopeInfoBO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 系统-数据操作范围 detail resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-数据操作范围 detail resp")
public class SysDataScopeDetailRespVO {
    
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @ExcelProperty("用户id")
    private Long userId;
    
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @ExcelProperty("用户名")
    private String username;
    
    /**
     * 可读数据范围
     */
    @Schema(description = "可读数据范围")
    @ExcelProperty("可读数据范围")
    private List<DataScopeInfoBO> readDataScopeList;
    
    /**
     * 可写（修改/删除）数据范围
     */
    @Schema(description = "可写（修改/删除）数据范围")
    @ExcelProperty("可写（修改/删除）数据范围")
    private List<DataScopeInfoBO> updateDataScopeList;
    
}