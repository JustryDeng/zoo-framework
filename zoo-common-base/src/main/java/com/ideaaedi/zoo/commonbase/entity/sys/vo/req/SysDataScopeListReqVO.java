package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import com.ideaaedi.zoo.commonbase.enums.sys.DataScopeTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>
 * 系统-数据操作范围 list req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-数据操作范围 list req")
public class SysDataScopeListReqVO extends BasePageDTO {
    
    /**
     * 租户
     */
    @Schema(description = "租户")
    private String tenant;

    /**
     * 操作类型
     */
    @Schema(description = "操作类型")
    private DataScopeTypeEnum type;
    
    /**
     * 用户id集合
     */
    @Schema(description = "用户id集合")
    private List<Long> userIdList;

    /**
     * 部门id集合
     */
    @Schema(description = "部门id集合")
    private List<Integer> deptIdList;
    
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;
    
    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String deptName;
}