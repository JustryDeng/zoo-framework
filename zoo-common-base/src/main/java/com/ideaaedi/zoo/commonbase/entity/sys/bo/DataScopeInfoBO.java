package com.ideaaedi.zoo.commonbase.entity.sys.bo;

import com.ideaaedi.zoo.commonbase.entity.sys.vo.resp.SysApiResourceListRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 数据范围信息
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class DataScopeInfoBO {
    
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
     * 部门id
     */
    @Schema(description = "部门id")
    private Integer deptId;
    
    /**
     * 部门id路径
     */
    @Schema(description = "部门id路径")
    private String deptPath;
    
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;
    
    /**
     * 部门路径变更前的历史数据是否可见（1-可见；0-不可见）
     */
    @Schema(description = "部门路径变更前的历史数据是否可见（1-可见；0-不可见）")
    private Integer deptHisVisible;
    
    /**
     * 历史部门路径（不含当前的）
     */
    @Schema(description = "历史部门路径（不含当前的）")
    private Set<String> deptHisPathSet;
    
    /**
     * 应用于的指定api
     */
    @Schema(description = "应用于的指定api")
    private List<SysApiResourceListRespVO> apiList;
    
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
}
