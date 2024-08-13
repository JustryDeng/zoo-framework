package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BasePageDTO;
import com.ideaaedi.zoo.commonbase.enums.sys.ApiTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 系统-api资源表 list req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-api资源表 list req")
public class SysApiResourceListReqVO extends BasePageDTO {
    
    /**
     * id集合
     */
    @Schema(description = "id集合", hidden = true)
    private Set<Long> idSet;
    
    /**
     * type
     */
    @Schema(description = "类型")
    private List<ApiTypeEnum> typeList;
    
    /**
     * 资源名
     */
    @Schema(description = "资源名")
    private String name;

    /**
     * 资源路径
     */
    @Schema(description = "资源路径")
    private String path;

    /**
     * 所属微服务
     */
    @Schema(description = "所属微服务")
    private String microService;

    /**
     * id集合
     */
    @Schema(description = "id集合", hidden = true)
    private Set<Long> apiIdSet;
}