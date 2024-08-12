package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统-角色表 detail resp
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "系统-角色表 detail resp")
public class SysRoleDetailRespVO {

    /**
     * id
     */
    @Schema(description = "id")
    private Long id;
    
    /**
     * 租户编码
     */
    @Schema(description = "租户编码")
    private String tenant;
    
    /**
     * 所属业务租户的名称
     */
    @Schema(description = "所属业务租户的名称")
    private String tenantName;
    
    /**
     * 角色编码
     */
    @Schema(description = "角色编码")
    private String code;

    /**
     * 角色名称
     */
    @Schema(description = "角色名称")
    private String name;

    /**
     * 角色类型
     */
    @Schema(description = "角色类型")
    private Integer type;
    
    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    
    /**
     * 和角色绑定的菜单
     */
    @Schema(description = "和角色绑定的菜单")
    List<SysQueryMenuRespVO> boundMenuList;
    
    /**
     * 和角色直接绑定的api
     */
    @Schema(description = "和角色直接绑定的api")
    List<SysApiResourceListRespVO> directlyBoundApiList;
    
    /**
     * 状态(1-正常；2-禁用)
     */
    @Schema(description = "状态(1-正常；2-禁用)")
    private Integer state;
    
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createdBy;
    
    /**
     * 创建人姓名
     */
    @Schema(description = "创建人姓名")
    private String createdByName;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private Long updatedBy;
    
    /**
     * 修改人姓名
     */
    @Schema(description = "修改人姓名")
    private String updatedByName;
    
    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedAt;
    
}