package ${package.Entity}.resp;

import ${package.Entity}.req.${originEntityName}BatchAddReqVO;
<#if swagger>
import io.swagger.v3.oas.annotations.media.Schema;
</#if>
<#if entityLombokModel>
import lombok.Data;
    <#if chainModel>
import lombok.experimental.Accessors;
    </#if>
</#if>

import java.util.List;

/**
 * <p>
 * ${table.comment!} batch-add resp
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Data
    <#if chainModel>
@Accessors(chain = true)
    </#if>
</#if>
public class ${originEntityName}BatchAddRespVO {

    /**
     * 是否全部成功(true-全部成功; false-全部失败或部分失败)
     */
    <#if swagger>
    @Schema(description = "是否全部成功(true-全部成功; false-全部失败或部分失败)")
    </#if>
    private Boolean ifAllSuccess;

    /**
     * 数据集
     */
    <#if swagger>
    @Schema(description = "数据集")
    </#if>
    private List<${originEntityName}BatchAddReqVO.AddItemParamDTO> dataList;
}