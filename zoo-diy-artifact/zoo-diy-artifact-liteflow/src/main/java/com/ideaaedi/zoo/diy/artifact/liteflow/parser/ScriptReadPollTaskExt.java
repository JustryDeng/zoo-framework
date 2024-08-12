package com.ideaaedi.zoo.diy.artifact.liteflow.parser;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.yomahub.liteflow.parser.sql.polling.impl.ScriptReadPollTask;
import com.yomahub.liteflow.parser.sql.read.SqlRead;
import com.yomahub.liteflow.parser.sql.read.vo.ScriptVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 增强点：save、delete时，记录日志
 */
@Slf4j
public class ScriptReadPollTaskExt extends ScriptReadPollTask {
    public ScriptReadPollTaskExt(SqlRead<ScriptVO> read) {
        super(read);
    }
    
    @Override
    public void doSave(List<ScriptVO> saveElementList) {
        super.doSave(saveElementList);
        if (!CollectionUtils.isEmpty(saveElementList)) {
            if (log.isInfoEnabled()) {
                log.info("to save(insert or update) scripts -> {}", JSON.toJSONString(saveElementList, JSONWriter.Feature.NotWriteDefaultValue));
            }
        }
    }
    
    @Override
    public void doDelete(List<String> deleteElementId) {
        super.doDelete(deleteElementId);
        if (!CollectionUtils.isEmpty(deleteElementId)) {
            if (log.isInfoEnabled()) {
                log.info("to remove scripts(nodeIds) -> {}", JSON.toJSONString(deleteElementId, JSONWriter.Feature.NotWriteDefaultValue));
            }
        }
    }
}