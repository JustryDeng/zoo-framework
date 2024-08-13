package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 系统-数据字典 tree req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDictTreeReqVO extends BaseDTO {
    
    /**
     * 字典路径(格式为：类型id_字典id)
     */
    @Schema(description = "字典路径(格式为：类型id_字典id)")
    private List<String> dictPaths;
    
    /**
     * 所属字典类型id
     */
    @Schema(description = "所属字典类型id")
    private List<Long> dictTypeIds;
    
    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码")
    private List<String> dictTypeCodes;
}