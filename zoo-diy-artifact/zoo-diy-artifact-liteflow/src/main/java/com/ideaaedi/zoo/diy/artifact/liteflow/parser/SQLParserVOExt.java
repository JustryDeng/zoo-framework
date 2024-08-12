package com.ideaaedi.zoo.diy.artifact.liteflow.parser;

import com.yomahub.liteflow.parser.sql.vo.SQLParserVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 增强点：增加chainDeleteField、chainNotDeleteValue、scriptDeleteField、scriptNotDeleteValue用于指定规则表、脚本表的逻辑删除字段及未删除值
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SQLParserVOExt extends SQLParserVO {
    
    /**
     * chain表的逻辑删除字段
     */
    private String chainDeleteField;
    
    /**
     * chain表的逻辑删除，未删除值
     */
    private String chainNotDeleteValue;
    
    /**
     * script表的逻辑删除字段
     */
    private String scriptDeleteField;
    
    /**
     * script表的逻辑删除，未删除值
     */
    private String scriptNotDeleteValue;
    
    
}
