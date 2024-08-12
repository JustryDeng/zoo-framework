package ${package.Entity}.req;

<#if swagger>
import io.swagger.v3.oas.annotations.media.Schema;
</#if>
<#if entityLombokModel>
import lombok.Data;
import lombok.EqualsAndHashCode;
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>
import ${Placeholder_BaseDTO};

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * <p>
 * ${table.comment!} batch-add req
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
@EqualsAndHashCode(callSuper = true)
    <#if chainModel>
@Accessors(chain = true)
    </#if>
</#if>
public class ${originEntityName}BatchAddReqVO extends BaseDTO {

    /**
     * 是否逐个处理
     */
    @NotNull(message = "ifOneByOne cannot be null")
    <#if swagger>
    @Schema(description = "是否逐个处理(true-逐个处理, 其中失败的不影响成功的; false-统一处理, 要么全成功，要么全失败)")
    </#if>
    private Boolean ifOneByOne;

    /**
     * 数据集
     */
    @Valid
    <#if swagger>
    @Schema(description = "数据集")
    </#if>
    @NotNull(message = "reqList cannot be null")
    @Size(min = 1, message = "reqList cannot be empty")
    private List<AddItemParamDTO> dataList;

    /**
    * @see ${originEntityName}AddReqVO
    */
    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class AddItemParamDTO extends ${originEntityName}AddReqVO {

        /**
         * 是否成功
         */
        <#if swagger>
        @Schema(description = "是否成功")
        </#if>
        private Boolean result;

        /**
         * 失败原因
         */
        <#if swagger>
        @Schema(description = "失败原因")
        </#if>
        private String failInfo;
    }
}