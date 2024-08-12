package ${service.pkg};

<#list service.importClasses as classLongName>
    <#if classLongName == "">

    <#else>
import ${classLongName};
    </#if>
</#list>

/**
* ${service.comment}
*
* @author ${service.author}
* @since ${service.since}
*/
public interface ${service.name} {

<#if service.entityReqVoName??>
    /**
     * 查信息
     *
     * @param req 参数
     *
     * @return 相关信息
     */
    ${service.entityRespVoName} info(${service.entityReqVoName} req);
</#if>
<#if service.entityListReqVoName??>

    /**
     * 列表
     *
     * @param req 参数
     *
     * @return 列表信息
     */
<#if service.properties.unified_pageInfo_class_name!?length gt 0>
    ${service.properties.unified_pageInfo_class_name}<${service.entityListRespVoName}> list(${service.entityListReqVoName} req);
<#else>
    ${service.entityListRespVoName} list(${service.entityListReqVoName} req);
</#if>
</#if>
}