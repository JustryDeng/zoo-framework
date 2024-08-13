package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统-数据字典类别表 detail resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-数据字典类别表 detail resp")
public class SysDictTypeDetailRespVO {

    /**
     * 字典类型id
     */
    @Schema(description = "字典类型id")
    private Integer id;
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;
    
    /**
     * 字典类型编码
     */
    @Schema(description = "字典类型编码（全局唯一）")
    private String code;

    /**
     * 字典类型名称
     */
    @Schema(description = "字典类型名称")
    private String name;
    
    /**
     * 排序（越小越排前面）
     */
    @Schema(description = "排序（越小越排前面）")
    private Integer sort;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedAt;

}