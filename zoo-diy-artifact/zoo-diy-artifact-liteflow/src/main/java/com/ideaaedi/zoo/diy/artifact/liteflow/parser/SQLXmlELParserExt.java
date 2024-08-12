package com.ideaaedi.zoo.diy.artifact.liteflow.parser;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.StrUtil;
import com.yomahub.liteflow.core.FlowInitHook;
import com.yomahub.liteflow.parser.constant.ReadType;
import com.yomahub.liteflow.parser.el.ClassXmlFlowELParser;
import com.yomahub.liteflow.parser.sql.SQLXmlELParser;
import com.yomahub.liteflow.parser.sql.exception.ELSQLException;
import com.yomahub.liteflow.parser.sql.polling.SqlReadPollTask;
import com.yomahub.liteflow.parser.sql.read.SqlRead;
import com.yomahub.liteflow.parser.sql.read.SqlReadFactory;
import com.yomahub.liteflow.parser.sql.read.impl.ChainRead;
import com.yomahub.liteflow.parser.sql.read.impl.ScriptRead;
import com.yomahub.liteflow.parser.sql.util.JDBCHelper;
import com.yomahub.liteflow.parser.sql.vo.SQLParserVO;
import com.yomahub.liteflow.property.LiteflowConfig;
import com.yomahub.liteflow.property.LiteflowConfigGetter;
import com.yomahub.liteflow.util.JsonUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;

/**
 * 参考{@link SQLXmlELParser}进行实现
 * <pre>
 * 增强点：
 * 1. 以{@link SQLXmlELParserExt#registerRead(SQLParserVOExt)}代替{@link SqlReadFactory#registerRead(SQLParserVO)}
 * 2. 以{@link SQLXmlELParserExt#registerSqlReadPollTask()}代替{@link SqlReadFactory#registerSqlReadPollTask(ReadType)}
 * 3. 以{@link ChainReadExt}代替{@link ChainRead}
 * 4. 以{@link ScriptReadExt}代替{@link ScriptRead}
 * 5. 以{@link SQLParserVOExt}代替{@link SQLParserVO},增加
 *    chainDeleteField、chainNotDeleteValue、
 *    scriptDeleteField、scriptNotDeleteValue，
 *    用于指定规则表、脚本表的逻辑删除字段及未删除值
 * 6. sql参考
 * {@code
 * CREATE TABLE `liteflow_chain`  (
 *     `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
 *     `application_name` varchar(32) NOT NULL COMMENT '所属应用',
 *     `chain_name` varchar(32) NOT NULL COMMENT '规则名称（定位chain的标识）',
 *     `chain_desc` varchar(64) NULL DEFAULT NULL COMMENT '规则描述',
 *     `el_data` text NOT NULL COMMENT '规则',
 *     `route` text NULL COMMENT '路由',
 *     `namespace` varchar(32) NULL DEFAULT NULL COMMENT '路由所属命名空间',
 *     `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（1-启用；0-不启用）',
 *     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除(0-未删除；1-已删除)',
 *     `created_by` bigint(20) NOT NULL COMMENT '创建人',
 *     `created_at` datetime NOT NULL COMMENT '创建时间',
 *     `updated_by` bigint(20) NOT NULL COMMENT '修改人',
 *     `updated_at` datetime NOT NULL COMMENT '修改时间',
 *     PRIMARY KEY (`id`) USING BTREE,
 *     INDEX `application_name_idx`(`application_name` ASC) USING BTREE,
 *     INDEX `chain_name_idx`(`chain_name` ASC) USING BTREE,
 *     INDEX `enable_idx`(`enable` ASC) USING BTREE,
 *     INDEX `namespace_idx`(`namespace` ASC) USING BTREE
 * ) ENGINE = InnoDB ROW_FORMAT = Dynamic;
 *
 * CREATE TABLE `liteflow_script`  (
 *     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
 *     `application_name` varchar(32) NOT NULL COMMENT '所属应用',
 *     `script_id` varchar(32) NOT NULL COMMENT '脚本id（定位node的标识）',
 *     `script_name` varchar(64) NULL DEFAULT NULL COMMENT '脚本名称',
 *     `script_data` text NOT NULL COMMENT '脚本',
 *     `script_type` enum('script','boolean_script','switch_script','for_script') NOT NULL COMMENT '脚本类型（script|boolean_script|switch_script|for_script）',
 *     `script_language` enum('qlexpress','groovy','js','python','lua','aviator','java','kotlin') NOT NULL COMMENT '脚本语言（qlexpress|groovy|js|python|lua|aviator|java|kotlin）',
 *     `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用（1-启用；0-不启用）',
 *     `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否已删除(0-未删除；1-已删除)',
 *     `created_by` bigint(20) NOT NULL COMMENT '创建人',
 *     `created_at` datetime NOT NULL COMMENT '创建时间',
 *     `updated_by` bigint(20) NOT NULL COMMENT '修改人',
 *     `updated_at` datetime NOT NULL COMMENT '修改时间',
 *     PRIMARY KEY (`id`) USING BTREE,
 *     INDEX `application_name_idx`(`application_name` ASC) USING BTREE,
 *     INDEX `script_id_idx`(`script_id` ASC) USING BTREE,
 *     INDEX `script_type_idx`(`script_type` ASC) USING BTREE,
 *     INDEX `script_language_idx`(`script_language` ASC) USING BTREE,
 *     INDEX `enable_idx`(`enable` ASC) USING BTREE
 * ) ENGINE = InnoDB ROW_FORMAT = Dynamic;
 * }
 *
 * </pre>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class SQLXmlELParserExt extends ClassXmlFlowELParser {
    
    private static SQLParserVOExt sqlParserVO;
    
    private static final String ERROR_MSG_PATTERN = "rule-source-ext-data {} is blank";
    
    private static final String ERROR_COMMON_MSG = "rule-source-ext-data is empty";
    
    /**
     * 构造函数
     */
    public SQLXmlELParserExt() {
        LiteflowConfig liteflowConfig = LiteflowConfigGetter.get();
        
        try {
            Map<String, String> ruleSourceExtDataMap = liteflowConfig.getRuleSourceExtDataMap();
            if (MapUtil.isNotEmpty(ruleSourceExtDataMap)) {
                sqlParserVO = BeanUtil.toBean(ruleSourceExtDataMap, SQLParserVOExt.class, CopyOptions.create());
            } else if (StrUtil.isNotBlank(liteflowConfig.getRuleSourceExtData())) {
                sqlParserVO = JsonUtil.parseObject(liteflowConfig.getRuleSourceExtData(), SQLParserVOExt.class);
            }
            if (Objects.isNull(sqlParserVO)) {
                throw new ELSQLException(ERROR_COMMON_MSG);
            }
            
            // 检查配置文件
            checkParserVO(sqlParserVO);
            
            // 初始化 JDBCHelper
            JDBCHelper.init(sqlParserVO);
            
            // 初始化 SqlReadFactory
            ///SqlReadFactory.registerRead(sqlParserVO); ext
            registerRead(sqlParserVO);
    
            // 注册轮询任务
            ///SqlReadFactory.registerSqlReadPollTask(ReadType.CHAIN); ext
            ///SqlReadFactory.registerSqlReadPollTask(ReadType.SCRIPT); ext
            registerSqlReadPollTask();
        } catch (ELSQLException elsqlException) {
            throw elsqlException;
        } catch (Exception ex) {
            throw new ELSQLException(ex);
        }
        
    }
    
    /**
     * ext 重写{@link SqlReadFactory#registerRead(SQLParserVO)}
     */
    private void registerRead(SQLParserVOExt config) throws NoSuchFieldException, IllegalAccessException {
        Field field = SqlReadFactory.class.getDeclaredField("READ_MAP");
        field.setAccessible(true);
        //noinspection unchecked
        Map<ReadType, SqlRead<?>> READ_MAP = (Map<ReadType, SqlRead<?>>)field.get(SqlReadFactory.class);
        READ_MAP.put(ReadType.CHAIN, new ChainReadExt(config));
        READ_MAP.put(ReadType.SCRIPT, new ScriptReadExt(config));
    }
    
    /**
     * ext 重写{@link SqlReadFactory#registerSqlReadPollTask(ReadType)}
     */
    private void registerSqlReadPollTask() throws NoSuchFieldException, IllegalAccessException {
        Field field = SqlReadFactory.class.getDeclaredField("POLL_TASK_MAP");
        field.setAccessible(true);
        //noinspection unchecked
        Map<ReadType, SqlReadPollTask<?>> POLL_TASK_MAP = (Map<ReadType, SqlReadPollTask<?>>)field.get(SqlReadFactory.class);
        POLL_TASK_MAP.put(ReadType.CHAIN, new ChainReadPollTaskExt(SqlReadFactory.getSqlRead(ReadType.CHAIN)));
        POLL_TASK_MAP.put(ReadType.SCRIPT, new ScriptReadPollTaskExt(SqlReadFactory.getSqlRead(ReadType.SCRIPT)));
    }
    
    @Override
    public String parseCustom() {
        try {
            JDBCHelper jdbcHelper = JDBCHelper.getInstance();
            String content = jdbcHelper.getContent();
            if (sqlParserVO.getPollingEnabled()) {
                FlowInitHook.addHook(() -> {
                    jdbcHelper.listenSQL();
                    return true;
                });
            }
            return content;
        } catch (Exception ex) {
            throw new ELSQLException(ex);
        }
    }
    
    /**
     * 检查配置文件并设置默认值
     *
     * @param sqlParserVO sqlParserVO
     */
    private void checkParserVO(SQLParserVO sqlParserVO) {
        if (sqlParserVO.isDefaultDataSource()) {
            return;
        }
        if (StrUtil.isEmpty(sqlParserVO.getUrl())) {
            throw new ELSQLException(StrFormatter.format(ERROR_MSG_PATTERN, "url"));
        }
        if (StrUtil.isEmpty(sqlParserVO.getDriverClassName())) {
            throw new ELSQLException(StrFormatter.format(ERROR_MSG_PATTERN, "driverClassName"));
        }
        if (Objects.isNull(sqlParserVO.getUsername())) {
            throw new ELSQLException(StrFormatter.format(ERROR_MSG_PATTERN, "username"));
        }
        if (Objects.isNull(sqlParserVO.getPassword())) {
            throw new ELSQLException(StrFormatter.format(ERROR_MSG_PATTERN, "password"));
        }
    }
}
