package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 系统-数据操作范围 update req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-数据操作范围 update req")
public class SysDataScopeUpdateReqVO extends BaseDTO {
    
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为null")
    @Schema(description = "用户id")
    private Long userId;
    
    /**
     * 可读部门id集合
     */
    @Schema(description = "可读部门id集合。为null或空则表示移除该用户的所有可读数据范围")
    private List<Integer> readDeptIdList;
    
    /**
     * 可写（修改/删除）部门id集合
     */
    @Schema(description = "可写（修改/删除）部门id集合。为null或空则表示移除该用户的所有可写数据范围")
    private List<Integer> updateDeptIdList;
    
    /**
     * 部门路径变更前的历史数据是否可见（1-可见；0-不可见）
     */
    @Schema(description = "部门路径变更前的历史数据是否可见（1-可见；0-不可见） 注：不传则默认作可见")
    private Integer deptHisVisible;
    
    /**
     * 指定应用的api集合（为null或空则表示应用于所有api）
     */
    @Schema(description = "指定应用的api集合（为null或空则表示应用于所有api）")
    private List<Long> apiIds;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    
}