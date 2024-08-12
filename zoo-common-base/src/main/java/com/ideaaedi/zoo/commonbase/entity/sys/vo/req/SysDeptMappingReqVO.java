package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.constant.JdSymbolConstant;
import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * <p>
 * 构建部门名称path req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-组织架构表 add req")
public class SysDeptMappingReqVO extends BaseDTO {
    
    /**
     * 部门路径集合
     * <p>
     * 注：部门path可以不必从根节点开始. 从任意节点开始均可
     */
    @Schema(description = "部门路径集合")
    private Collection<String> deptPathColl;

    /**
     * 在根据deptPath生成deptPathName前,是否尝试对deptPath进行shrink.
     * <p>
     * 此参数的目的是：使用户只能看到该租户下的对应路径
     */
    @NotNull(message = "ifShrink不能为空")
    @Schema(description = "在根据deptPath生成deptPathName前,是否尝试对deptPath进行shrink", $comment = "此参数的目的是：控制用户只能看到该租户下的对应路径")
    private Boolean ifShrink;
    
    /**
     * 部门名称路径分隔符. 默认为/
     */
    @Schema(description = "部门名称路径分隔符. 默认为/")
    private String deptPathNameSeparator = JdSymbolConstant.SLASH;

}