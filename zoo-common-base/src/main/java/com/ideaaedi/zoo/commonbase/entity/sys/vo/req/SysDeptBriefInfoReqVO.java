package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.constant.JdSymbolConstant;
import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * <p>
 * 系统-组织架构表 brief req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-组织架构表 brief req")
public class SysDeptBriefInfoReqVO extends BaseDTO {
    
    /**
     * 部门id集合
     */
    @NotEmpty(message = "idColle不能为空")
    @Schema(description = "部门id集合")
    private Collection<Integer> idColle;
    
    /**
     * 是否查询直接子部门简讯
     */
    @Schema(description = "是否查询直接父部门简讯（默认为false）")
    private Boolean queryDirectParent = false;
    
    /**
     * 是否查询直接子部门简讯
     */
    @Schema(description = "是否查询直接子部门简讯（默认为false）")
    private Boolean queryDirectChildren = false;
    
    /**
     * 是否组装path为对应的中文名
     */
    @Schema(description = "是否组装path为对应的中文名（默认为false）")
    private Boolean assemblePathName = false;
    
    /**
     * 部门名称路径分隔符. 默认为/
     * <p>
     * 当{@link SysDeptBriefInfoReqVO#getAssemblePathName()}为true时，此字段生效
     */
    @Schema(description = "部门名称路径分隔符. 默认为/")
    private String pathNameSeparator = JdSymbolConstant.SLASH;
}