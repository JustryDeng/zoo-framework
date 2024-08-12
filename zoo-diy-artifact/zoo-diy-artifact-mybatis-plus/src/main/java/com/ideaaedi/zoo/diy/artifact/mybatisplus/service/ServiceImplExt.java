package com.ideaaedi.zoo.diy.artifact.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.mapper.BaseMapperExt;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * 扩展{@link ServiceImpl}
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class ServiceImplExt<M extends BaseMapperExt<T>, T> extends ServiceImpl<M, T> implements IServiceExt<T> {
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean update(Wrapper<T> updateWrapper) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        return this.update(tableInfo.newInstance(), updateWrapper);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int forceDeleteById(Serializable id) {
        return getBaseMapper().forceDeleteById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int forceDeleteBatchIds(Collection<?> idList) {
        return getBaseMapper().forceDeleteBatchIds(idList);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int forceDelete(Wrapper<T> queryWrapper) {
        return getBaseMapper().forceDelete(queryWrapper);
    }
    
    @Override
    public T forceGetOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return baseMapper.forceSelectOne(queryWrapper, throwEx);
    }
}