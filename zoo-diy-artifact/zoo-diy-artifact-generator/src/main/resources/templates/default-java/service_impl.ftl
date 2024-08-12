package ${service_impl.pkg};

<#list service_impl.importClasses as classLongName>
    <#if classLongName == "">

    <#else>
import ${classLongName};
    </#if>
</#list>

/**
* ${service_impl.comment}
*
* @author ${service_impl.author}
* @since ${service_impl.since}
*/
@Service
<#if service_impl.serviceName??>
public class ${service_impl.name} implements ${service_impl.serviceName} {
<#else>
public class ${service_impl.name} {
</#if>
<#if service_impl.mapperName??>

    @Resource
    private ${service_impl.mapperName} ${service_impl.unCapitalMapperName};
</#if>
<#if service_impl.entityReqVoName??>

    /**
     * 查信息
     *
     * @param req 参数
     *
     * @return 相关信息
     */
<#if service_impl.serviceName??>
    @Override
</#if>
    public ${service_impl.entityRespVoName} info(${service_impl.entityReqVoName} req) {
        return null;
    }
</#if>
<#if service_impl.entityListReqVoName??>

     /**
      * 列表
     *
     * @param req 参数
     *
     * @return 列表信息
     */
<#if service_impl.serviceName??>
    @Override
</#if>
<#if service_impl.properties.unified_pageInfo_class_name!?length gt 0>
    public ${service_impl.properties.unified_pageInfo_class_name}<${service_impl.entityListRespVoName}> list(${service_impl.entityListReqVoName} req) {
<#else>
    public ${service_impl.entityListRespVoName} list(${service_impl.entityListReqVoName} req) {
</#if>
          return null;
    }
</#if>
}