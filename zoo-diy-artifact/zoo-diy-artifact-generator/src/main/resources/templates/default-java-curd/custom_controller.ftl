package ${package.Controller};

<#if swagger && Placeholder_ExistKnife4jExt>
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
</#if>
import ${package.Entity}.req.${originEntityName}AddReqVO;
import ${package.Entity}.req.${originEntityName}BatchAddReqVO;
import ${package.Entity}.req.${originEntityName}ListReqVO;
import ${package.Entity}.req.${originEntityName}UpdateReqVO;
import ${package.Entity}.resp.${originEntityName}BatchAddRespVO;
import ${package.Entity}.resp.${originEntityName}DetailRespVO;
import ${package.Entity}.resp.${originEntityName}ListRespVO;
import ${package.Service}.${table.serviceName};
import ${Placeholder_PageInfo};
<#if wrapControllerResult>
import ${Placeholder_Result};
</#if>
<#if superControllerClassPackage??>
import ${superControllerClassPackage};
</#if>
<#if swagger>
    <#if Placeholder_ExistKnife4jExt>
import com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.annotation.ApiTag;
    <#else>
import io.swagger.v3.oas.annotations.tags.Tag;
    </#if>
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
</#if>
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>

/**
 * ${briefTableComment}
 *
 * @author ${author}
 * @since ${date}
 */
@Validated
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
<#if swagger>
<#if Placeholder_ExistKnife4jExt>
@ApiTag(name = "${briefTableComment}", order = 100)
<#else>
@Tag(name = "${briefTableComment}")
</#if>
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${originEntityNameHyphenStyle}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass} {
<#else>
public class ${table.controllerName} {
</#if>

    @Resource
    private ${table.serviceName} ${customLowerServiceName};

    /**
     * 增
     *
     * @param req 参数
     *
     * @return 新增的数据详情
     */
    @PostMapping("/add")
    <#if swagger>
    @Operation(summary = "增")
    <#if Placeholder_ExistKnife4jExt>
    @ApiOperationSupport(order = 1)
    </#if>
    </#if>
    public <#if wrapControllerResult>Result<</#if>${originEntityName}DetailRespVO<#if wrapControllerResult>></#if> add(@RequestBody @Validated ${originEntityName}AddReqVO req) {
        return <#if wrapControllerResult>Result.success(</#if>${customLowerServiceName}.add(req, true)<#if wrapControllerResult>)</#if>;
    }

    /**
     * 批量增
     *
     * @param req 参数
     *
     * @return 结果说明
     */
    @PostMapping("/batch-add")
    <#if swagger>
    @Operation(summary = "批量增")
    <#if Placeholder_ExistKnife4jExt>
    @ApiOperationSupport(order = 2)
    </#if>
    </#if>
    public <#if wrapControllerResult>Result<</#if>${originEntityName}BatchAddRespVO<#if wrapControllerResult>></#if> batchAdd(@RequestBody @Validated ${originEntityName}BatchAddReqVO req) {
        return <#if wrapControllerResult>Result.success(</#if>${customLowerServiceName}.batchAdd(req)<#if wrapControllerResult>)</#if>;
    }

    /**
     * 删
     *
     * @param id 要删除数据的id
     *
     * @return 删除了的数据详情
     */
    <#if swagger>
    @Operation(summary = "删")
    </#if>
    @DeleteMapping("/delete/{id}")
    <#if swagger && Placeholder_ExistKnife4jExt>
    @ApiOperationSupport(order = 3)
    </#if>
    public <#if wrapControllerResult>Result<</#if>${originEntityName}DetailRespVO<#if wrapControllerResult>></#if> delete(<#if swagger>@Parameter(description = "要删除数据的id")
                                              </#if>@PathVariable("id") @NotNull(message = "id cannot be null.") <#list customAllFields as field><#if field.keyFlag>${field.propertyType}</#if></#list> id) {
        return <#if wrapControllerResult>Result.success(</#if>${customLowerServiceName}.delete(id)<#if wrapControllerResult>)</#if>;
    }

    /**
     * 改
     *
     * @param req 参数
     *
     * @return 修改后的数据详情
     */
    @PutMapping("/update")
    <#if swagger>
    @Operation(summary = "改")
    <#if Placeholder_ExistKnife4jExt>
    @ApiOperationSupport(order = 4)
    </#if>
    </#if>
    public <#if wrapControllerResult>Result<</#if>${originEntityName}DetailRespVO<#if wrapControllerResult>></#if> update(@RequestBody @Validated ${originEntityName}UpdateReqVO req) {
        return <#if wrapControllerResult>Result.success(</#if>${customLowerServiceName}.update(req)<#if wrapControllerResult>)</#if>;
    }

    /**
     * 查列表
     *
     * @param req 参数
     *
     * @return 数据列表
     */
    @PostMapping("/list")
    <#if swagger>
    @Operation(summary = "查列表")
    </#if>
    <#if swagger && Placeholder_ExistKnife4jExt>
    @ApiOperationSupport(order = 5)
    </#if>
    public <#if wrapControllerResult>Result<</#if>PageInfo<${originEntityName}ListRespVO><#if wrapControllerResult>></#if> list(@RequestBody @Validated ${originEntityName}ListReqVO req) {
        return <#if wrapControllerResult>Result.success(</#if>${customLowerServiceName}.list(req)<#if wrapControllerResult>)</#if>;
    }

    /**
     * 查详情
     *
     * @param id 要查询数据的id
     *
     * @return 数据详情
     */
    <#if swagger>
    @Operation(summary = "查详情")
    </#if>
    @GetMapping("/detail/{id}")
    <#if swagger && Placeholder_ExistKnife4jExt>
    @ApiOperationSupport(order = 6)
    </#if>
    public <#if wrapControllerResult>Result<</#if>${originEntityName}DetailRespVO<#if wrapControllerResult>></#if> detail(<#if swagger>@Parameter(description = "要查询数据的id")
                                              </#if>@PathVariable("id") @NotNull(message = "id cannot be null.") <#list customAllFields as field><#if field.keyFlag>${field.propertyType}</#if></#list> id) {
        return <#if wrapControllerResult>Result.success(</#if>${customLowerServiceName}.detail(id)<#if wrapControllerResult>)</#if>;
    }

}
</#if>