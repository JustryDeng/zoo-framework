package com.ideaaedi.zoo.diy.artifact.tenantlike.holder;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mp相关数据持有器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class MybatisPlusInfoHolder implements SmartInitializingSingleton {
    
    /**
     * 记录所有租户表
     */
    @Getter
    private final Set<String> tenantTables = Collections.synchronizedSet(new HashSet<>());
    
    private final ApplicationContext applicationContext;
    
    /**
     * poClass - service映射
     */
    @Getter
    @SuppressWarnings("rawtypes")
    private final Map<Class, IService> clazzServiceMapping = new ConcurrentHashMap<>(64);
    
    /**
     * poClass - Constructor映射
     */
    @Getter
    @SuppressWarnings("rawtypes")
    private final Map<Class, Constructor> clazzConstructorMapping = new ConcurrentHashMap<>(128);
    
    public MybatisPlusInfoHolder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
    
    /**
     * 初始化租户表
     */
    @Override
    public void afterSingletonsInstantiated() {
        Set<String> poClassLongNameSet = new HashSet<>(64);
        List<TableInfo> tableInfos = TableInfoHelper.getTableInfos();
        // 采集表对应的模型类与service的映射关系
        if (!CollectionUtils.isEmpty(tableInfos)) {
            tableInfos.forEach(tableInfo -> {
                Class<?> entityClass = tableInfo.getEntityType();
                poClassLongNameSet.add(entityClass.getName());
                clazzConstructorMapping.put(entityClass, entityClass.getDeclaredConstructors()[0]);
            });
        }
        //noinspection rawtypes
        Map<String, ServiceImpl> iserviceMap = applicationContext.getBeansOfType(ServiceImpl.class);
        // 采集表对应的模型类与service的映射关系、采集所有的poClass
        if (!CollectionUtils.isEmpty(iserviceMap)) {
            iserviceMap.values().forEach(x -> {
                //noinspection unchecked
                Class<T> entityClass = x.getEntityClass();
                if (poClassLongNameSet.contains(entityClass.getName())) {
                    clazzServiceMapping.put(entityClass, x);
                }
            });
        }
        
        try {
            for (String poClassLongName : poClassLongNameSet) {
                Class<?> clazz = Class.forName(poClassLongName);
                TableName tableNameAnno = clazz.getDeclaredAnnotation(TableName.class);
                if (tableNameAnno == null) {
                    continue;
                }
                String tableName = tableNameAnno.value();
                boolean existTenantColumn = true;
                try {
                    clazz.getDeclaredField(BaseConstant.TENANT_PROPERTY_NAME);
                } catch (NoSuchFieldException e) {
                    existTenantColumn = false;
                }
                if (existTenantColumn) {
                    if (tableName.contains(BaseConstant.MYSQL_ANTI_ESCAPE)) {
                        tableName = tableName.replace(BaseConstant.MYSQL_ANTI_ESCAPE, BaseConstant.EMPTY);
                    }
                    tenantTables.add(tableName);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        log.info("tenantTables -> {}", JSON.toJSONString(tenantTables));
    }
}