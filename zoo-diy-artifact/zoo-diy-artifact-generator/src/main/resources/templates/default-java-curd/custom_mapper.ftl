package ${package.Mapper};

<#if Placeholder_ExistMybatisPlusExt>
import com.ideaaedi.zoo.diy.artifact.mybatisplus.mapper.BaseMapperExt;
<#else>
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
</#if>
import ${package.Entity}.${entity};

<#if mapperAnnotation>
import org.apache.ibatis.annotations.Mapper;
</#if>

/**
* <p>
    * ${table.comment!} Mapper 接口
    * </p>
*
* @author ${author}
* @since ${date}
*/
<#if mapperAnnotation>
@Mapper
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
    <#if Placeholder_ExistMybatisPlusExt>
public interface ${table.mapperName} extends ${superMapperClass}Ext<${entity}> {
    <#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
    </#if>

}
</#if>
