package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 系统-aksk用户表 list resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-aksk用户表 list resp")
public class SysAkskUserListRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    @ExcelProperty("id")
    private Long id;

    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @ExcelProperty("用户id")
    private Long userId;

    /**
     * ak
     */
    @Schema(description = "ak")
    @ExcelProperty("ak")
    private String accessKey;

    /**
     * sk
     */
    @Schema(description = "sk")
    @ExcelProperty("sk")
    private String secretKey;

    /**
     * 用户组织名
     */
    @Schema(description = "用户组织名")
    @ExcelProperty("用户组织名")
    private String orgName;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @ExcelProperty("修改时间")
    private LocalDateTime updatedAt;

}