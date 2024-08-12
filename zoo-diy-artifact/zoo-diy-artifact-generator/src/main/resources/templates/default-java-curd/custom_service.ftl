package ${package.Service};

import ${Placeholder_PageInfo};
<#if Placeholder_ExistMybatisPlusExt>
import com.ideaaedi.zoo.diy.artifact.mybatisplus.service.IServiceExt;
<#else>
import com.baomidou.mybatisplus.extension.service.IService;
</#if>
import ${package.Entity}.${entity};
import ${package.Entity}.req.${originEntityName}AddReqVO;
import ${package.Entity}.req.${originEntityName}BatchAddReqVO;
import ${package.Entity}.req.${originEntityName}ListReqVO;
import ${package.Entity}.req.${originEntityName}UpdateReqVO;
import ${package.Entity}.resp.${originEntityName}BatchAddRespVO;
import ${package.Entity}.resp.${originEntityName}DetailRespVO;
import ${package.Entity}.resp.${originEntityName}ListRespVO;

import javax.annotation.Nullable;

/**
 * <p>
 * ${briefTableComment} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
    <#if Placeholder_ExistMybatisPlusExt>
public interface ${table.serviceName} extends ${superServiceClass}Ext<${entity}> {
    <#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {
    </#if>

    /**
     * 增
     *
     * @param req 参数
     * @param queryDetail true-查询并返回详情; false-返回null
     *
     * @return 新增的数据详情（当queryDetail为true时，返回null）
     */
    @Nullable
    ${originEntityName}DetailRespVO add(${originEntityName}AddReqVO req, boolean queryDetail);

    /**
     * 批量增
     *
     * @param req 参数
     *
     * @return 批量新增结果
     */
    ${originEntityName}BatchAddRespVO batchAdd(${originEntityName}BatchAddReqVO req);

    /**
     * 删
     *
     * @param id 要删除数据的id
     *
     * @return 删除了的数据详情
     */
    ${originEntityName}DetailRespVO delete(<#list customAllFields as field><#if field.keyFlag>${field.propertyType}</#if></#list> id);

    /**
     * 改
     *
     * @param req 参数
     *
     * @return 修改后的数据详情
     */
    @Nullable
    ${originEntityName}DetailRespVO update(${originEntityName}UpdateReqVO req);

    /**
     * 查详情
     *
     * @param id 要查询数据的id
     *
     * @return 数据详情
     */
    @Nullable
    ${originEntityName}DetailRespVO detail(<#list customAllFields as field><#if field.keyFlag>${field.propertyType}</#if></#list> id);

    /**
     * 查列表
     *
     * @param req 参数
     *
     * @return 数据列表
     */
    PageInfo<${originEntityName}ListRespVO> list(${originEntityName}ListReqVO req);
}
</#if>