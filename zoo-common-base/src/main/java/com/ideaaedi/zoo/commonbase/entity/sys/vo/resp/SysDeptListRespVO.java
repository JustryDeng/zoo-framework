package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import com.ideaaedi.zoo.commonbase.enums.sys.DeptTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统-组织架构表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-组织架构表 list resp")
public class SysDeptListRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    @ExcelProperty("id")
    private Integer id;

    /**
     * 父部门id（无父部们则填0）
     */
    @Schema(description = "父部门id（无父部们则填0）")
    @ExcelProperty("父部门id（无父部们则填0）")
    private Integer pid;
    
    /**
     * 父部门
     */
    @Schema(description = "父部门")
    @ExcelProperty("父部门")
    private String pName;

    /**
     * 编码（设计上允许重复）
     */
    @Schema(description = "编码（设计上允许重复）")
    @ExcelProperty("编码（设计上允许重复）")
    private String code;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    @ExcelProperty("部门名称")
    private String name;

    /**
     * 类型
     */
    @Schema(description = "类型")
    @ExcelProperty("类型")
    private DeptTypeEnum type;
    
    /**
     * 部门路径
     */
    @Schema(description = "部门路径")
    private String path;
    
    /**
     * 部门路径
     */
    @Schema(description = "部门路径")
    private String namePath;

    /**
     * 排序（同级部门，sort越小越排前面）
     */
    @Schema(description = "排序（同级部门，sort越小越排前面）")
    @ExcelProperty("排序（同级部门，sort越小越排前面）")
    private Integer sort;

}