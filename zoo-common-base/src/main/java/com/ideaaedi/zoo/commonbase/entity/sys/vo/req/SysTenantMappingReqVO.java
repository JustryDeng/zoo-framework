package com.ideaaedi.zoo.commonbase.entity.sys.vo.req;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Collection;

/**
 * <p>
 * 系统-业务租户表 mapping req
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统-业务租户表 mapping req")
public class SysTenantMappingReqVO extends BaseDTO {
    
    /**
     * 要查询的租户集合
     */
    @Schema(description = "要查询的租户集合")
    @NotEmpty(message = "tenantColle不能为空 ")
    private Collection<String> tenantColle;
    
    /**
     * true-只查询tenantColle中属于业务租户的租户; false-查询tenantColle中的全量租户
     */
    @NotNull(message = "onlyBizTenant不能为空 ")
    @Schema(description = "查询范围控制（true-只查询tenantColle中属于业务租户的租户; false-查询tenantColle中的全量租户）")
    private Boolean onlyBizTenant;
}