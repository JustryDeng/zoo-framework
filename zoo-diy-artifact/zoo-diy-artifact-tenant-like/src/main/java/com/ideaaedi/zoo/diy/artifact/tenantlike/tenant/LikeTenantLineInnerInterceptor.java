package com.ideaaedi.zoo.diy.artifact.tenantlike.tenant;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.google.common.collect.Sets;
import com.ideaaedi.zoo.commonbase.constant.BaseConstant;
import com.ideaaedi.zoo.commonbase.entity.BaseCodeMsgEnum;
import com.ideaaedi.zoo.commonbase.exception.BaseException;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.DefaultTenantScope;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScope;
import com.ideaaedi.zoo.commonbase.zoo_component.tenantlike.TenantScopeHolder;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 多租户 like插件
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class LikeTenantLineInnerInterceptor extends TenantLineInnerInterceptor {
    
    /**
     * 查询有租户的相关表时， 允许查询出租户值为null的表
     */
    private final Set<String> allowQueryNullTenantTables = Sets.newHashSet("sys_dict", "sys_dict_type");
    
    private final TenantLineHandler tenantLineHandler;
    
    public LikeTenantLineInnerInterceptor(TenantLineHandler tenantLineHandler) {
        super(tenantLineHandler);
        this.tenantLineHandler = tenantLineHandler;
    }
    
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (shouldIgnoreTenant(true)) {
            return;
        }
        super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
    }
    
    /**
     * 处理插入
     */
    @Override
    protected void processInsert(Insert insert, int index, String sql, Object obj) {
        if (tenantLineHandler.ignoreTable(insert.getTable().getName())) {
            // 过滤退出执行
            return;
        }
        List<Column> columns = insert.getColumns();
        if (com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isEmpty(columns)) {
            // 针对不给列名的insert 不处理
            return;
        }
        String tenantIdColumn = tenantLineHandler.getTenantIdColumn();
        if (tenantLineHandler.ignoreInsert(columns, tenantIdColumn)) {
            // 针对已给出租户列的insert 不处理
            return;
        }
        columns.add(new Column(tenantIdColumn));
    
        // fixed gitee pulls/141 duplicate update
        List<Expression> duplicateUpdateColumns = insert.getDuplicateUpdateExpressionList();
        StringValue tenantScopeSv = (StringValue)tenantLineHandler.getTenantId();
        TenantScope tenantScope = JSON.parseObject(tenantScopeSv.getValue(), DefaultTenantScope.class);
        String tenant = tenantScope.insertTenant();
        if (StringUtils.isBlank(tenant)) {
            throw new IllegalArgumentException("obtain insert-tenant fail.");
        }
        Expression tenantExpression = new StringValue(tenant);
        if (com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isNotEmpty(duplicateUpdateColumns)) {
            EqualsTo equalsTo = new EqualsTo();
            equalsTo.setLeftExpression(new StringValue(tenantIdColumn));
            equalsTo.setRightExpression(tenantExpression);
            duplicateUpdateColumns.add(equalsTo);
        }
        
        Select select = insert.getSelect();
        if (select != null && (select.getSelectBody() instanceof PlainSelect)) {
            this.processInsertSelect(select.getSelectBody(), (String) obj);
        } else if (insert.getItemsList() != null) {
            // fixed github pull/295
            ItemsList itemsList = insert.getItemsList();
            if (itemsList instanceof MultiExpressionList) {
                ((MultiExpressionList) itemsList).getExpressionLists().forEach(el -> el.getExpressions().add(tenantExpression));
            } else {
                List<Expression> expressions = ((ExpressionList) itemsList).getExpressions();
                if (com.baomidou.mybatisplus.core.toolkit.CollectionUtils.isNotEmpty(expressions)) {//fix github issue 4998 jsqlparse 4.5 批量insert ItemsList不是MultiExpressionList 了，需要特殊处理
                    int len = expressions.size();
                    for (int i = 0; i < len; i++) {
                        Expression expression = expressions.get(i);
                        if (expression instanceof RowConstructor) {
                            ((RowConstructor) expression).getExprList().getExpressions().add(tenantExpression);
                        } else if (expression instanceof Parenthesis) {
                            RowConstructor rowConstructor = new RowConstructor()
                                    .withExprList(new ExpressionList(((Parenthesis) expression).getExpression(), tenantExpression));
                            expressions.set(i, rowConstructor);
                        } else {
                            if (len - 1 == i) { // (?,?) 只有最后一个expre的时候才拼接tenantId
                                expressions.add(tenantExpression);
                            }
                        }
                    }
                } else {
                    expressions.add(tenantExpression);
                }
            }
        } else {
            throw ExceptionUtils.mpe("Failed to process multiple-table update, please exclude the tableName or "
                    + "statementId");
        }
    }
    
    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        if (shouldIgnoreTenant(false)) {
            return;
        }
        super.processDelete(delete, index, sql, obj);
    }
    
    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        if (shouldIgnoreTenant(false)) {
            return;
        }
        super.processUpdate(update, index, sql, obj);
    }
    
    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        if (shouldIgnoreTenant(true)) {
            return;
        }
        super.processSelect(select, index, sql, obj);
    }
    
    /**
     * 是否忽略租户
     *
     * @param ifSelect true-查询；false-修改或删除
     *
     * @return 是否忽略租户
     */
    private boolean shouldIgnoreTenant(boolean ifSelect) {
        // 先从会话级临时数据域中查
        TenantScope tenantScope = TenantScopeHolder.SESSION_DATA_SCOPE_HOLDER.get().peekFirst();
        // 在从用户登录态中查
        if (tenantScope == null) {
            tenantScope = ZooContext.Tenant.currTenantScope();
        }
        if (tenantScope == null) {
            return false;
        }
        if (ifSelect) {
            Collection<String> readableTenants = tenantScope.readableTenants();
            return Optional.ofNullable(readableTenants)
                    .map(items -> items.stream().anyMatch(TenantScope.IGNORE_URD_SCOPE::equals))
                    .orElse(false);
        }
        Collection<String> modifiableTenants = tenantScope.modifiableTenants();
        return Optional.ofNullable(modifiableTenants)
                .map(items -> items.stream().anyMatch(TenantScope.IGNORE_URD_SCOPE::equals))
                .orElse(false);
    }
    
    /**
     * 适配update和delete
     */
    @Override
    protected Expression andExpression(Table table, Expression where, final String whereSegment) {
        final StringValue tenantScopeSv = (StringValue) tenantLineHandler.getTenantId();
        TenantScope tenantScope = JSON.parseObject(tenantScopeSv.getValue(), DefaultTenantScope.class);
        List<String> modifiableTenants = Optional.ofNullable(tenantScope)
                .map(TenantScope::modifiableTenants)
                .map(ArrayList::new)
                .orElse(null);
        if (CollectionUtils.isEmpty(modifiableTenants)) {
            throw new BaseException("curr userId -> " + ZooContext.Auth.currUserId(), BaseCodeMsgEnum.NO_DATA_SCOPE_UPDATE);
        }
        modifiableTenants.sort(Comparator.comparing(String::length));
        Column aliasColumn = this.getAliasColumn(table);
        // step1. 将modifiableTenant转换成多个like查询表达式
        // like 第一个
        BinaryExpression expression = buildLikeRightExpression(aliasColumn, modifiableTenants.get(0));
        // like 其它的
        int dataScopeListSize = modifiableTenants.size();
        if (dataScopeListSize > 1) {
            for (int i = 1; i < dataScopeListSize; i++) {
                LikeExpression itemExpression = buildLikeRightExpression(aliasColumn, modifiableTenants.get(i));
                expression = new OrExpression(expression, itemExpression);
            }
            DoubleValue one = new DoubleValue("1");
            expression = new AndExpression(new EqualsTo(one, one), new Parenthesis(expression));
        }
        
        if (where != null) {
            if (where instanceof OrExpression) {
                return new AndExpression(expression, new Parenthesis(where));
            } else {
                return new AndExpression(expression, where);
            }
        }
        return expression;
    }
    
    /**
     * 适配select
     */
    @Override
    protected Expression builderExpression(Expression currentExpression, List<Table> tables, final String whereSegment) {
        // 没有表需要处理直接返回
        if (CollectionUtils.isEmpty(tables)) {
            return currentExpression;
        }
        // 构造每张表的条件
        List<Table> tempTables = tables.stream()
                .filter(x -> !tenantLineHandler.ignoreTable(x.getName()))
                .collect(Collectors.toList());
        
        // 没有表需要处理直接返回
        if (CollectionUtils.isEmpty(tempTables)) {
            return currentExpression;
        }
    
        final StringValue tenantScopeSv = (StringValue) tenantLineHandler.getTenantId();
        TenantScope tenantScope = JSON.parseObject(tenantScopeSv.getValue(), DefaultTenantScope.class);
        List<String> readDataScopePaths = Optional.ofNullable(tenantScope)
                .map(TenantScope::readableTenants)
                .map(ArrayList::new)
                .orElse(null);
        if (CollectionUtils.isEmpty(readDataScopePaths)) {
            throw new BaseException("curr userId -> " + ZooContext.Auth.currUserId(), BaseCodeMsgEnum.NO_DATA_SCOPE_READ);
        }
        readDataScopePaths.sort(Comparator.comparing(String::length));
        List<Expression> customExpressions = tempTables.stream()
                .map(item -> {
                    Column aliasColumn = this.getAliasColumn(item);
                    String name = item.getName();
                    name = name.replace(BaseConstant.MYSQL_ANTI_ESCAPE, BaseConstant.EMPTY);
                    // step1. 将readDataScopePaths转换成多个like查询表达式
                    // like 第一个
                    Expression expression = buildLikeRightExpression(aliasColumn, readDataScopePaths.get(0));
                    // like 其它的
                    int dataScopeListSize = readDataScopePaths.size();
                    if (dataScopeListSize > 1) {
                        for (int i = 1; i < dataScopeListSize; i++) {
                            LikeExpression itemExpression = buildLikeRightExpression(aliasColumn,
                                    readDataScopePaths.get(i));
                            expression = new OrExpression(expression, itemExpression);
                        }
                        expression = new Parenthesis(expression);
                    }
                    
                    // step2. 个别特殊表， 允许所有用户查询tenant为null的数据
                    if (allowQueryNullTenantTables.contains(name)) {
                        // 同时允许查询值为null的数据的话，返回Or表达式
                        IsNullExpression isNullExpression = new IsNullExpression();
                        isNullExpression.setLeftExpression(aliasColumn);
                        OrExpression orExpression = new OrExpression(isNullExpression, expression);
                        // 再用括号Parenthesis括起来
                        expression = new Parenthesis(orExpression);
                    }
                    return expression;
                })
                .toList();
        
        // 注入的表达式
        Expression injectExpression = customExpressions.get(0);
        // 如果有多表，则用 and 连接
        if (customExpressions.size() > 1) {
            for (int i = 1; i < customExpressions.size(); i++) {
                injectExpression = new AndExpression(injectExpression, customExpressions.get(i));
            }
        }
        
        if (currentExpression == null) {
            return injectExpression;
        }
        if (currentExpression instanceof OrExpression) {
            return new AndExpression(new Parenthesis(currentExpression), injectExpression);
        } else {
            return new AndExpression(currentExpression, injectExpression);
        }
    }
    
    /**
     * 构建like表达式
     *
     * @param aliasColumn 表列
     * @param strToLikeRight 需要右模糊查询的字符串
     *
     * @return like表达式
     */
    @Nonnull
    private static LikeExpression buildLikeRightExpression(Column aliasColumn, String strToLikeRight) {
        Assert.isTrue(StringUtils.isNotBlank(strToLikeRight), "strToLikeRight cannot be blank.");
        LikeExpression itemExpression = new LikeExpression();
        itemExpression.setLeftExpression(aliasColumn);
        itemExpression.setRightExpression(new StringValue(strToLikeRight + "%"));
        return itemExpression;
    }
}
