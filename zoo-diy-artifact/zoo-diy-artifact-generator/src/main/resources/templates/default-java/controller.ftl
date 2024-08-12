package ${controller.pkg};

<#list controller.importClasses as classLongName>
    <#if classLongName == "">

    <#else>
import ${classLongName};
    </#if>
</#list>

/**
 * ${controller.comment}
 *
 * @author ${controller.author}
 * @since ${controller.since}
 */
@Slf4j
@Validated
@RestController
<#if controller.enableSwagger>
<#if controller.hasZooKnife4jExt>
@ApiTag(name = "${controller.comment}", order = 100)
<#else>
@Tag(name = "${controller.comment}")
</#if>
</#if>
@RequestMapping("/${controller.pojoNameHyphen}")
public class ${controller.name} {
<#if controller.serviceName??>

    @Resource
    private ${controller.serviceName} ${controller.unCapitalServiceName};
</#if>
<#if controller.entityReqVoName??>

    /**
     * 信息
     *
     * @param req 参数
     *
     * @return 相关信息
     */
    @PostMapping("/info")
    <#if controller.enableSwagger>
    @Operation(summary = "信息")
    </#if>
    <#if controller.enableSwagger && controller.hasZooKnife4jExt>
    @ApiOperationSupport(order = 1)
    </#if>
    <#if controller.properties.unified_result_class_name!?length gt 0>
    public ${controller.properties.unified_result_class_name}<${controller.entityRespVoName}> info(@RequestBody @Validated ${controller.entityReqVoName} req) {
    <#else>
    public ${controller.entityRespVoName} info(@RequestBody @Validated ${controller.entityReqVoName} req) {
    </#if>
        <#if controller.properties.unified_result_class_name!?length gt 0>
            <#if controller.serviceName??>
        return ${controller.properties.unified_result_class_name}.success(${controller.unCapitalServiceName}.info(req));
            <#else>
        return ${controller.properties.unified_result_class_name}.success();
            </#if>
        <#else>
            <#if controller.serviceName??>
        return ${controller.unCapitalServiceName}.list(req);
            <#else>
        return null;
            </#if>
        </#if>
    }
</#if>
<#if controller.entityListReqVoName??>

    /**
     * 列表
     *
     * @param req 参数
     *
     * @return 列表信息
     */
    @PostMapping("/list")
    <#if controller.enableSwagger>
    @Operation(summary = "列表")
    </#if>
    <#if controller.enableSwagger && controller.hasZooKnife4jExt>
    @ApiOperationSupport(order = 2)
    </#if>
    <#if controller.properties.unified_result_class_name!?length gt 0>
        <#if controller.properties.unified_pageInfo_class_name!?length gt 0>
    public ${controller.properties.unified_result_class_name}<${controller.properties.unified_pageInfo_class_name}<${controller.entityListRespVoName}>> list(@RequestBody @Validated ${controller.entityListReqVoName} req) {
        <#else>
    public ${controller.properties.unified_result_class_name}<${controller.entityListRespVoName}> list(@RequestBody @Validated ${controller.entityListReqVoName} req) {
        </#if>
    <#else>
        <#if controller.properties.unified_pageInfo_class_name!?length gt 0>
    public ${controller.properties.unified_pageInfo_class_name}<${controller.entityListRespVoName}> list(@RequestBody @Validated ${controller.entityListReqVoName} req) {
        <#else>
    public ${controller.entityListRespVoName} info(@RequestBody @Validated ${controller.entityListReqVoName} req) {
        </#if>
    </#if>
        <#if controller.properties.unified_result_class_name!?length gt 0>
            <#if controller.serviceName??>
        return ${controller.properties.unified_result_class_name}.success(${controller.unCapitalServiceName}.list(req));
            <#else>
        return ${controller.properties.unified_result_class_name}.success();
            </#if>
        <#else>
            <#if controller.serviceName??>
        return ${controller.unCapitalServiceName}.list(req);
            <#else>
        return null;
            </#if>
        </#if>
    }
</#if>
}