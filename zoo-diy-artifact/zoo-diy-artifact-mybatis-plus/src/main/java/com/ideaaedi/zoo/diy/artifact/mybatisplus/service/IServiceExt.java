package com.ideaaedi.zoo.diy.artifact.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.ideaaedi.zoo.diy.artifact.mybatisplus.mapper.BaseMapperExt;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IServiceExt<T> extends IService<T> {
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 根据 ID 查询
     *
     * @param id 主键ID
     */
    default T forceGetById(Serializable id) {
        return getBaseMapper().forceSelectById(id);
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 查询（根据ID 批量查询）
     *
     * @param idList 主键ID列表
     */
    default List<T> forceListByIds(Collection<? extends Serializable> idList) {
        return getBaseMapper().forceSelectBatchIds(idList);
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 根据 Wrapper，查询一条记录 <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default T forceGetOne(Wrapper<T> queryWrapper) {
        return forceGetOne(queryWrapper, true);
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 根据 Wrapper，查询一条记录
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    T forceGetOne(Wrapper<T> queryWrapper, boolean throwEx);
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 查询指定条件是否存在数据
     *
     * @see Wrappers#emptyWrapper()
     */
    default boolean forceExists(Wrapper<T> queryWrapper) {
        return getBaseMapper().forceExists(queryWrapper);
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 查询总记录数
     *
     * @see Wrappers#emptyWrapper()
     */
    default long forceCount() {
        return forceCount(Wrappers.emptyWrapper());
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 根据 Wrapper 条件，查询总记录数
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default long forceCount(Wrapper<T> queryWrapper) {
        return SqlHelper.retCount(getBaseMapper().forceSelectCount(queryWrapper));
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 查询列表
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default List<T> forceList(Wrapper<T> queryWrapper) {
        return getBaseMapper().forceSelectList(queryWrapper);
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 查询列表
     *
     * @param page         分页条件
     * @param queryWrapper queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @return 列表数据
     * @since 3.5.3.2
     */
    default List<T> forceList(IPage<T> page, Wrapper<T> queryWrapper) {
        return getBaseMapper().forceSelectList(page, queryWrapper);
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 查询所有
     *
     * @see Wrappers#emptyWrapper()
     */
    default List<T> forceList() {
        return forceList(Wrappers.emptyWrapper());
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 分页查询单表数据
     *
     * @param page 分页条件
     * @return 列表数据
     * @since 3.5.3.2
     */
    default List<T> forceList(IPage<T> page) {
        return forceList(page, Wrappers.emptyWrapper());
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 翻页查询
     *
     * @param page         翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    default <E extends IPage<T>> E forcePage(E page, Wrapper<T> queryWrapper) {
        return getBaseMapper().forceSelectPage(page, queryWrapper);
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 无条件翻页查询
     *
     * @param page 翻页对象
     * @see Wrappers#emptyWrapper()
     */
    default <E extends IPage<T>> E forcePage(E page) {
        return forcePage(page, Wrappers.emptyWrapper());
    }
    
    /**
     * 根据id硬删除 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#deleteById(Serializable)}
     *
     * @param id 数据id
     *
     * @return 受影响行数
     */
    int forceDeleteById(Serializable id);
    
    /**
     * 根据id批量硬删除 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#deleteBatchIds(Collection)}
     *
     * @param idList 数据id集合
     *
     * @return 受影响行数
     */
    int forceDeleteBatchIds(Collection<?> idList);
    
    /**
     * 硬删除 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#delete(Wrapper)}
     *
     * @param queryWrapper 条件
     *
     * @return 受影响行数
     */
    int forceDelete(Wrapper<T> queryWrapper);
    
    /**
     * 获取对应 entity 的 BaseMapper
     *
     * @return BaseMapper
     */
    BaseMapperExt<T> getBaseMapper();
}
