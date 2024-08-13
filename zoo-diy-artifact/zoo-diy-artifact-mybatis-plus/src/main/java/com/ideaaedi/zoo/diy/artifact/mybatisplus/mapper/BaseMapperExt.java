package com.ideaaedi.zoo.diy.artifact.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.injector.methods.Delete;
import com.baomidou.mybatisplus.core.injector.methods.DeleteBatchByIds;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.core.injector.methods.SelectBatchByIds;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.injector.methods.SelectCount;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.session.ResultHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface BaseMapperExt<T> extends BaseMapper<T> {
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，根据 ID 查询 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectById(Serializable)}
     *
     * @param id 主键ID
     */
    T forceSelectById(Serializable id);
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，查询（根据ID 批量查询） <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectBatchIds(Collection)}
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     */
    List<T> forceSelectBatchIds(@Param(Constants.COLL) Collection<? extends Serializable> idList);
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，查询（根据ID 批量查询） <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectBatchIds(Collection, ResultHandler)}
     *
     * @param idList        idList 主键ID列表(不能为 null 以及 empty)
     * @param resultHandler resultHandler 结果处理器 {@link ResultHandler}
     * @since 3.5.4
     */
    void forceSelectBatchIds(@Param(Constants.COLL) Collection<? extends Serializable> idList, ResultHandler<T> resultHandler);
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，根据 entity 条件，查询一条记录
     * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
     * <p> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectOne(Wrapper)}</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    default T forceSelectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        return this.forceSelectOne(queryWrapper, true);
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，根据 entity 条件，查询一条记录，现在会根据{@code throwEx}参数判断是否抛出异常，如果为false就直接返回一条数据
     * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
     * <p> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectOne(Wrapper, boolean)}</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @param throwEx      boolean 参数，为true如果存在多个结果直接抛出异常
     */
    default T forceSelectOne(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper, boolean throwEx) {
        List<T> list = new ArrayList<>();
        this.forceSelectList(queryWrapper, resultContext -> {
            T resultObject = resultContext.getResultObject();
            list.add(resultObject);
            if (!throwEx || resultContext.getResultCount() > 1) {
                resultContext.stop();
            }
        });
        int size = list.size();
        if (size == 1) {
            return list.get(0);
        } else if (size > 1) {
            if (throwEx) {
                throw new TooManyResultsException("Expected one result (or null) to be returned by forceSelectOne(), but found multiple records");
            }
            return list.get(0);
        }
        return null;
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，根据 entity 条件，查询全部记录 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectList(Wrapper)}
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    List<T> forceSelectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，根据 entity 条件，查询全部记录 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectList(Wrapper, ResultHandler)}
     *
     * @param queryWrapper  实体对象封装操作类（可以为 null）
     * @param resultHandler 结果处理器 {@link ResultHandler}
     * @since 3.5.4
     */
    void forceSelectList(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper, ResultHandler<T> resultHandler);
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，根据 Wrapper 条件，判断是否存在记录 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#exists(Wrapper)}
     *
     * @param queryWrapper 实体对象封装操作类
     * @return 是否存在记录
     */
    default boolean forceExists(Wrapper<T> queryWrapper) {
        Long count = this.forceSelectCount(queryWrapper);
        return null != count && count > 0;
    }
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，根据 Wrapper 条件，查询总记录数 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectCount(Wrapper)}
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    Long forceSelectCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤，根据 entity 条件，查询全部记录（并翻页） <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectList(IPage, Wrapper)}
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @since 3.5.3.2
     */
    List<T> forceSelectList(IPage<T> page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    
    /**
     * 不自动追加逻辑删除字段(如果有的话)过滤， 根据 entity 条件，查询全部记录（并翻页） <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#selectPage(IPage, Wrapper)}
     *
     * @param page         分页查询条件
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     */
    default <P extends IPage<T>> P forceSelectPage(P page, @Param(Constants.WRAPPER) Wrapper<T> queryWrapper) {
        List<T> list = forceSelectList(page, queryWrapper);
        page.setRecords(list);
        return page;
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
    int forceDeleteBatchIds(@Param(Constants.COLL) Collection<?> idList);
    
    /**
     * 硬删除 <p /> 注：当数据库表模型不存在逻辑删除字段时，此方法等价于{@link BaseMapper#delete(Wrapper)}
     *
     * @param queryWrapper 条件
     *
     * @return 受影响行数
     */
    int forceDelete(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    
    class ForceSelectById extends SelectById {
        public ForceSelectById() {
            super("forceSelectById");
        }
        @Override
        @SuppressWarnings("deprecation")
        protected String sqlWhereByMap(TableInfo table) {
            return PrivateForceSelectAbstractMethod.INSTANCE.sqlWhereByMap(table);
        }
    
        @Override
        protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
            return PrivateForceSelectAbstractMethod.INSTANCE.sqlWhereEntityWrapper(newLine, table);
        }
        
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            SqlMethod sqlMethod = SqlMethod.SELECT_BY_ID;
            SqlSource sqlSource = super.createSqlSource(configuration, String.format(sqlMethod.getSql(),
                    sqlSelectColumns(tableInfo, false),
                    tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty(),
                    EMPTY), Object.class);
            return this.addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
        }
    }
    
    class ForceSelectBatchByIds extends SelectBatchByIds {
        public ForceSelectBatchByIds() {
            super("forceSelectBatchIds");
        }
        @Override
        @SuppressWarnings("deprecation")
        protected String sqlWhereByMap(TableInfo table) {
            return PrivateForceSelectAbstractMethod.INSTANCE.sqlWhereByMap(table);
        }
    
        @Override
        protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
            return PrivateForceSelectAbstractMethod.INSTANCE.sqlWhereEntityWrapper(newLine, table);
        }
        
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            SqlMethod sqlMethod = SqlMethod.SELECT_BATCH_BY_IDS;
            SqlSource sqlSource = super.createSqlSource(configuration, String.format(sqlMethod.getSql(),
                    sqlSelectColumns(tableInfo, false), tableInfo.getTableName(), tableInfo.getKeyColumn(),
                    SqlScriptUtils.convertForeach("#{item}", COLL, null, "item", COMMA),
                    EMPTY), Object.class);
            return addSelectMappedStatementForTable(mapperClass, methodName, sqlSource, tableInfo);
        }
    }
    
    class ForceSelectCount extends SelectCount {
        public ForceSelectCount() {
            super("forceSelectCount");
        }
        @Override
        @SuppressWarnings("deprecation")
        protected String sqlWhereByMap(TableInfo table) {
            return PrivateForceSelectAbstractMethod.INSTANCE.sqlWhereByMap(table);
        }
    
        @Override
        protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
            return PrivateForceSelectAbstractMethod.INSTANCE.sqlWhereEntityWrapper(newLine, table);
        }
    }
    
    class ForceSelectList extends SelectList {
        public ForceSelectList() {
            super("forceSelectList");
        }
        @Override
        @SuppressWarnings("deprecation")
        protected String sqlWhereByMap(TableInfo table) {
            return PrivateForceSelectAbstractMethod.INSTANCE.sqlWhereByMap(table);
        }
    
        @Override
        protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
            return PrivateForceSelectAbstractMethod.INSTANCE.sqlWhereEntityWrapper(newLine, table);
        }
    }
    
    class ForceDeleteById extends DeleteById {
        public ForceDeleteById() {
            super("forceDeleteById");
        }
        
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            SqlMethod sqlMethod = SqlMethod.DELETE_BY_ID;
            String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(), tableInfo.getKeyProperty());
            return this.addDeleteMappedStatement(mapperClass, this.methodName, super.createSqlSource(this.configuration, sql, Object.class));
        }
    }
    
    
    class ForceDeleteBatchByIds extends DeleteBatchByIds {
        public ForceDeleteBatchByIds() {
            super("forceDeleteBatchIds");
        }
        
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            SqlMethod sqlMethod = SqlMethod.DELETE_BATCH_BY_IDS;
            String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), tableInfo.getKeyColumn(), SqlScriptUtils.convertForeach(SqlScriptUtils.convertChoose("@org.apache.ibatis.type.SimpleTypeRegistry@isSimpleType(item.getClass())", "#{item}", "#{item." + tableInfo.getKeyProperty() + "}"), "coll", (String)null, "item", ","));
            SqlSource sqlSource = super.createSqlSource(this.configuration, sql, Object.class);
            return this.addDeleteMappedStatement(mapperClass, this.methodName, sqlSource);
        }
    }

    class ForceDelete extends Delete {
        public ForceDelete() {
            super("forceDelete");
        }
        
        @Override
        public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
            SqlMethod sqlMethod = SqlMethod.DELETE;
            String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), this.sqlWhereEntityWrapper(true, tableInfo), this.sqlComment());
            SqlSource sqlSource = super.createSqlSource(this.configuration, sql, modelClass);
            return this.addDeleteMappedStatement(mapperClass, this.methodName, sqlSource);
        }
    }
}