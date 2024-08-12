package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.sys.dto.DeptIdPostIdDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 用户绑定部门和职位 req
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2022/9/8 16:10:40
 */
@Data
public class SysUserBindDeptPostReqVO {

    /**
     * 用户id
     */
    @NotNull(message = "id不能为空")
    @Schema(description = "用户id")
    private Long userId;

    /**
     * 部门职位信息
     */
    @Valid
    @NotNull(message = "部门职位信息不能为空")
    @Schema(description = "部门和职位（注：用户将主归属到第一个部门下）")
    private List<DeptIdPostIdDTO> deptPostList;
}
