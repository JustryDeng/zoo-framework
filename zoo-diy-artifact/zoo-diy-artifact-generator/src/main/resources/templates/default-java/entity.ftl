package ${entity.pkg};

<#list entity.importClasses as classLongName>
    <#if classLongName == "">

    <#else>
import ${classLongName};
    </#if>
</#list>

/**
 * ${entity.comment!"(non-javadoc)"}
 *
 * @author ${entity.author}
 * @since ${entity.since}
 */
<#if entity.enableLombok>
@Data
    <#if entity.superClassType!?length gt 0>
@EqualsAndHashCode(callSuper = true)
    </#if>
</#if>
<#if entity.enableSwagger>
@Schema(description = "${entity.comment!entity.name}")
</#if>
<#if entity.superClassType!?length gt 0>
public class ${entity.name} extends ${entity.superClassType} <#if entity.superInterfaceTypes!?length gt 0>implements ${entity.superInterfaceTypes} </#if>{
<#else>
public class ${entity.name} <#if entity.superInterfaceTypes!?length gt 0>implements ${entity.superInterfaceTypes} </#if>{
</#if>
<#if entity.enableSerializable>

    @Serial
    private static final long serialVersionUID = 1L;
</#if>
<#-- ----------  BEGIN 字段循环遍历  ---------->

<#list entity.fields as field>
    <#if field.comment!?length gt 0>
        <#if entity.enableSwagger>
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

<#if !entity.enableLombok>
    <#list entity.fields as field>
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
        return "${entity.name}{" +
    <#list entity.fields as field>
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