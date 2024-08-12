package ${package.ServiceImpl};

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
        <#list customAllFields as field>
            <#if field.keyFlag>
        <#-- 自增主键, 不需要导入IdWorker -->
                <#if field.keyIdentityFlag>
                <#else>
        <#-- 其它类型的主键（这里统一认为是雪花算法生成的id） -->
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
                </#if>
            </#if>
        </#list>
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${Placeholder_PageInfo};
<#if Placeholder_ExistMybatisPlusExt>
import  com.ideaaedi.zoo.diy.artifact.mybatisplus.service.ServiceImplExt;
<#else>
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
</#if>
import ${package.Entity}.${entity};
import ${package.Entity}.req.${originEntityName}AddReqVO;
import ${package.Entity}.req.${originEntityName}BatchAddReqVO;
import ${package.Entity}.req.${originEntityName}ListReqVO;
import ${package.Entity}.req.${originEntityName}UpdateReqVO;
import ${package.Entity}.resp.${originEntityName}BatchAddRespVO;
import ${package.Entity}.resp.${originEntityName}DetailRespVO;
import ${package.Entity}.resp.${originEntityName}ListRespVO;
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
<#list customImportPackages! as pkg>
import ${pkg};
</#list>
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * ${briefTableComment} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
    <#if Placeholder_ExistMybatisPlusExt>
public class ${table.serviceImplName} extends ${superServiceImplClass}Ext<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    <#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    </#if>

    @Nullable
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${originEntityName}DetailRespVO add(${originEntityName}AddReqVO req, boolean queryDetail) {
        ${entity} po = new ${entity}();
        BeanUtils.copyProperties(req, po);
        <#list customAllFields as field>
            <#if field.propertyType == "boolean">
                <#assign getprefix="is"/>
            <#else>
                <#assign getprefix="get"/>
            </#if>
            <#if field.keyFlag>
        <#-- 自增主键 -->
                <#if field.keyIdentityFlag>
        po.set${field.capitalName}(null);
        save(po);
        return queryDetail ? detail(po.${getprefix}${field.capitalName}()) : null;
                <#else>
        <#-- 其它类型的主键（这里统一认为是雪花算法生成的id） -->
        long id = IdWorker.getId();
        po.set${field.capitalName}(id);
        save(po);
        return queryDetail ? detail(id) : null;
                </#if>
            </#if>
        </#list>
    }

    @Override
    public ${originEntityName}BatchAddRespVO batchAdd(${originEntityName}BatchAddReqVO req) {
        Boolean ifOneByOne = req.getIfOneByOne();
        Objects.requireNonNull(ifOneByOne, "ifOneByOne cannot be null.");
        List<${originEntityName}BatchAddReqVO.AddItemParamDTO> dataList = req.getDataList();
        ${table.serviceName} thisService = (${table.serviceName}) AopContext.currentProxy();
        if (ifOneByOne) {
            for (${originEntityName}BatchAddReqVO.AddItemParamDTO item : dataList) {
                try {
                    thisService.add(item, false);
                    item.setResult(true);
                } catch (Exception e) {
                    item.setResult(false);
                    item.setFailInfo(e.getMessage());
                }
            }
        } else {
            // 出于性能及业务差异考虑, 这里不直接生成. 需要的话，请自己实现
            throw new UnsupportedOperationException("Un-support ifOneByOne is false.");
        }
        ${originEntityName}BatchAddRespVO resp = new ${originEntityName}BatchAddRespVO();
        resp.setDataList(dataList);
        resp.setIfAllSuccess(dataList.stream().allMatch(x -> x.getResult() != null && x.getResult()));
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${originEntityName}DetailRespVO delete(<#list customAllFields as field><#if field.keyFlag>${field.propertyType}</#if></#list> id) {
        Objects.requireNonNull(id, "id cannot be null.");
        ${originEntityName}DetailRespVO resp = detail(id);
        removeById(id);
        return resp;
    }

    @Nullable
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ${originEntityName}DetailRespVO update(${originEntityName}UpdateReqVO req) {
        <#list customAllFields as field>
            <#if field.keyFlag>
                <#if field.propertyType == "boolean">
                    <#assign getprefix="is"/>
                <#else>
                    <#assign getprefix="get"/>
                </#if>
        ${field.propertyType} id = req.${getprefix}${field.capitalName}();
            </#if>
        </#list>
        Objects.requireNonNull(id, "id cannot be null.");
        // mybatis-plus默认的updateById方法，更新策略默认是NOT_NULL（即更新数据时数据为NULL值时将不更新进数据库）
        ${entity} po = new ${entity}();
        BeanUtils.copyProperties(req, po);
        updateById(po);
        return detail(id);
    }

    @Nullable
    @Override
    public ${originEntityName}DetailRespVO detail(<#list customAllFields as field><#if field.keyFlag>${field.propertyType}</#if></#list> id) {
        Objects.requireNonNull(id, "id cannot be null.");
        ${entity} po = getById(id);
        if (po == null) {
            return null;
        }
        ${originEntityName}DetailRespVO resp = new ${originEntityName}DetailRespVO();
        BeanUtils.copyProperties(po, resp);
        return resp;
    }
    
    @Override
    public PageInfo<${originEntityName}ListRespVO> list(${originEntityName}ListReqVO req) {
        int pageNum = req.getPageNum();
        int pageSize = req.getPageSize();
        <#list customAllFields as field>
            <#if field.propertyType == "boolean">
                <#assign getprefix="is"/>
            <#else>
                <#assign getprefix="get"/>
            </#if>
        ${field.propertyType} ${field.propertyName} = req.${getprefix}${field.capitalName}();
        </#list>

        // 分页查
        IPage<${entity}> pageInfo = new Page<>(pageNum, pageSize);
        IPage<${entity}> page = this.baseMapper.selectPage(pageInfo,
                new LambdaQueryWrapper<${entity}>()
        <#list customAllFields as field>
            <#if field.propertyType == "boolean">
                <#assign getprefix="is"/>
            <#else>
                <#assign getprefix="get"/>
            </#if>
            .eq(<#if field.propertyType == "String">${field.propertyName} != null && ${field.propertyName}.trim().length() != 0<#else>${field.propertyName} != null</#if>,  ${entity}::${getprefix}${field.capitalName}, ${field.propertyName})
        </#list>
            .orderByDesc(${entity}::getId)
        );
        // 转换为resp模型
        List<${entity}> records = page.getRecords();
        List<${originEntityName}ListRespVO> list;
        if (CollectionUtils.isEmpty(records)) {
            list = Collections.emptyList();
        } else {
            list = records.stream().map(po -> {
                ${originEntityName}ListRespVO resp = new ${originEntityName}ListRespVO();
                BeanUtils.copyProperties(po, resp);
                return resp;
            }).collect(Collectors.toList());
        }
        return PageInfo.of(page.getTotal(), pageNum, pageSize, list);
    }
}
</#if>