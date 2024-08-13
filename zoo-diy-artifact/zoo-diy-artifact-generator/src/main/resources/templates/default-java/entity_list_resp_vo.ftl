package ${entity_list_resp_vo.pkg};

<#list entity_list_resp_vo.importClasses as classLongName>
    <#if classLongName == "">

    <#else>
import ${classLongName};
    </#if>
</#list>

/**
 * ${entity_list_resp_vo.comment!"(non-javadoc)"}
 *
 * @author ${entity_list_resp_vo.author}
 * @since ${entity_list_resp_vo.since}
 */
<#if entity_list_resp_vo.enableLombok>
@Data
    <#if entity_list_resp_vo.superClassType!?length gt 0>
@EqualsAndHashCode(callSuper = true)
    </#if>
</#if>
<#if entity_list_resp_vo.enableSwagger>
@Schema(description = "${entity_list_resp_vo.comment!entity_list_resp_vo.name}")
</#if>
<#if entity_list_resp_vo.superClassType!?length gt 0>
public class ${entity_list_resp_vo.name} extends ${entity_list_resp_vo.superClassType} {
<#else>
public class ${entity_list_resp_vo.name} {
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->

<#list entity_list_resp_vo.fields as field>
    <#if field.comment!?length gt 0>
        <#if entity_list_resp_vo.enableSwagger>
    @Schema(description = "${field.comment}")
        <#else>
    /**
     * ${field.comment}
     */
        </#if>
    </#if>
    private ${field.type} ${field.name};
</#list>
<#------------  END 字段循环遍历  ---------->

<#if !entity_list_resp_vo.enableLombok>
    <#list entity_list_resp_vo.fields as field>
        <#if field.type == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>
    public ${field.type} ${getprefix}${field.capitalName}() {
        return ${field.name};
    }
    </#list>

    @Override
    public String toString() {
        return "${entity_list_resp_vo.name}{" +
    <#list entity_list_resp_vo.fields as field>
        <#if field_index==0>
            "${field.name}=" + ${field.name} +
        <#else>
            ", ${field.name}=" + ${field.name} +
        </#if>
    </#list>
        "}";
    }

</#if>
}