package com.ideaaedi.zoo.diy.artifact.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlScriptUtils;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 对{@link AbstractMethod#sqlWhereByMap(TableInfo)}、{@link AbstractMethod#sqlWhereEntityWrapper(boolean, TableInfo)} 进行重写，使按照无逻辑删除字段逻辑走
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
class PrivateForceSelectAbstractMethod extends AbstractMethod {
    
    static final PrivateForceSelectAbstractMethod INSTANCE = new PrivateForceSelectAbstractMethod("privateForceSelectAbstractMethod");
    
    private PrivateForceSelectAbstractMethod(String methodName) {
        super(methodName);
    }
    
    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected String sqlWhereByMap(TableInfo table) {
        ///if (table.isWithLogicDelete()) {
        ///    // 逻辑删除
        ///    String sqlScript = SqlScriptUtils.convertChoose("v == null", " ${k} IS NULL ",
        ///            " ${k} = #{v} ");
        ///    sqlScript = SqlScriptUtils.convertForeach(sqlScript, COLUMN_MAP, "k", "v", "AND");
        ///    sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and !%s.isEmpty", COLUMN_MAP, COLUMN_MAP), true);
        ///    sqlScript += (NEWLINE + table.getLogicDeleteSql(true, true));
        ///    sqlScript = SqlScriptUtils.convertWhere(sqlScript);
        ///    return sqlScript;
        ///} else {
        String sqlScript = SqlScriptUtils.convertChoose("v == null", " ${k} IS NULL ",
                " ${k} = #{v} ");
        sqlScript = SqlScriptUtils.convertForeach(sqlScript, COLUMN_MAP, "k", "v", "AND");
        sqlScript = SqlScriptUtils.convertWhere(sqlScript);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null and !%s", COLUMN_MAP,
                COLUMN_MAP_IS_EMPTY), true);
        return sqlScript;
        ///}
    }
    
    @Override
    protected String sqlWhereEntityWrapper(boolean newLine, TableInfo table) {
        /*
         * Wrapper SQL
         */
        String _sgEs_ = "<bind name=\"_sgEs_\" value=\"ew.sqlSegment != null and ew.sqlSegment != ''\"/>";
        String andSqlSegment = SqlScriptUtils.convertIf(String.format(" AND ${%s}", WRAPPER_SQLSEGMENT), String.format("_sgEs_ and %s", WRAPPER_NONEMPTYOFNORMAL), true);
        String lastSqlSegment = SqlScriptUtils.convertIf(String.format(" ${%s}", WRAPPER_SQLSEGMENT), String.format("_sgEs_ and %s", WRAPPER_EMPTYOFNORMAL), true);
        
        ////*
        /// * 存在逻辑删除 SQL 注入
        /// */
        ///if (table.isWithLogicDelete()) {
        ///    String sqlScript = table.getAllSqlWhere(true, true, true, WRAPPER_ENTITY_DOT);
        ///    sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY), true);
        ///    sqlScript = SqlScriptUtils.convertIf(_sgEs_ + NEWLINE + sqlScript + NEWLINE + andSqlSegment + NEWLINE + lastSqlSegment,
        ///            String.format("%s != null", WRAPPER), true);
        ///    sqlScript = SqlScriptUtils.convertWhere(table.getLogicDeleteSql(false, true) + NEWLINE + sqlScript);
        ///    return newLine ? NEWLINE + sqlScript : sqlScript;
        ///}
        
        /*
         * 普通 SQL 注入
         */
        String sqlScript = table.getAllSqlWhere(false, false, true, WRAPPER_ENTITY_DOT);
        sqlScript = SqlScriptUtils.convertIf(sqlScript, String.format("%s != null", WRAPPER_ENTITY), true);
        sqlScript = SqlScriptUtils.convertWhere(sqlScript + NEWLINE + andSqlSegment) + NEWLINE + lastSqlSegment;
        sqlScript = SqlScriptUtils.convertIf(_sgEs_ + NEWLINE + sqlScript, String.format("%s != null", WRAPPER), true);
        return newLine ? NEWLINE + sqlScript : sqlScript;
    }
    
    @Override
    public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
        throw new UnsupportedOperationException("PrivateForceSelectAbstractMethod just be a common-logic extract class.");
    }
}
