package com.ideaaedi.zoo.diy.artifact.liteflow.parser;

import com.yomahub.liteflow.parser.sql.read.impl.ChainRead;
import org.apache.commons.lang3.StringUtils;

/**
 * 增强点：过滤，使只查询逻辑未删除
 */
public class ChainReadExt extends ChainRead {
    
    private final SQLParserVOExt config;
    
    public ChainReadExt(SQLParserVOExt config) {
        super(config);
        this.config = config;
    }
    
    @Override
    public String buildQuerySql() {
        String chainDeleteField = this.config.getChainDeleteField();
        String chainNotDeleteValue = this.config.getChainNotDeleteValue();
        if (StringUtils.isAnyBlank(chainDeleteField, chainNotDeleteValue)) {
            return super.buildQuerySql();
        }
        return String.format("%s AND %s=%s", super.buildQuerySql(), chainDeleteField, chainNotDeleteValue);
    }
}
