package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import com.ideaaedi.zoo.commonbase.entity.sys.dto.SysDeptBriefInfoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 系统-组织架构表 brief resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptBriefInfoRespVO extends SysDeptBriefInfoDTO {
    
    /**
     * 直接父部门简讯
     */
    @Schema(description = "直接父部门简讯")
    private SysDeptBriefInfoRespVO parentDept;
    
    /**
     * 直接子部门简讯
     */
    @Schema(description = "直接子部门简讯")
    private List<SysDeptBriefInfoRespVO> childrenDept;
}