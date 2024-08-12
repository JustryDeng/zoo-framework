package com.ideaaedi.zoo.diy.artifact.shardingsphere.algorithm;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.ideaaedi.zoo.commonbase.entity.BaseCodeMsgEnum;
import com.ideaaedi.zoo.commonbase.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * 日期(yyyy-MM-dd)分片算法
 * <br />
 * 此类已通过sharding的SPI加入spring容器(见{@link DateShardingAlgorithm#getType()}说明)，可直接使用Resource等进行依赖注入
 *
 * @author JustryDeng
 * @since 1.0.0
 */
@Slf4j
public abstract class DateShardingAlgorithm implements StandardShardingAlgorithm<String> {
    
    /**
     * 获取真实表后缀
     * <pre>
     *     真实表名 = 逻辑表名 + 后缀
     * </pre>
     *
     * @param shardingValue 分片列的值，yyyy-MM-dd格式的日期
     *
     * @return 真实表名的后缀
     */
    abstract String suffix(String shardingValue);
    
    /**
     * shardingValue.getValue()值只有一个，那么返回对应的实际表名
     *
     * @param availableTargetNames 可用的目标节点表(即：当前数据源下，actual-data-nodes对应的表)
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        // 逻辑表名， 如：trans_record
        final String logicTableName = shardingValue.getLogicTableName();
        // 分片列的当前值， 如：2022-10-06
        final String value = shardingValue.getValue();
        // 组装获得实际表名， 如："trans_record" + "_202210" = "trans_record_202210"
        return logicTableName + suffix(value);
    }
    
    /**
     * shardingValue.getValueRange()值对应一个范围，那么返回对应范围的多个实际表名
     * <br />
     * 如; 查询时指定 trans_date <= 2022-10-07 and trans_date >= 2022-08-07,
     *     那么 返回的表名集合就应该为 trans_record_202208 ... trans_record_202210
     *
     * @param availableTargetNames 可用的目标节点表(即：当前数据源下，actual-data-nodes对应的表)
     */
    @Override
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<String> shardingValue) {
        /*
         * 校验限定值与可用表名是匹配的。 如果不匹配，则说明sql写得有问题
         *
         * 如：多个分表关联查询时，没有做到对每个分表的分片列进行条件限定
         *    限定方式1：在where条件中明确对sql中涉及到的所有分片表进行分片列值限定
         *    限定方式2：在join关联时明确对分片表进行分片列值限定
         */
        final String logicTableName = shardingValue.getLogicTableName();
        if (!CollectionUtils.isEmpty(availableTargetNames)) {
            String firstRealTableName = availableTargetNames.iterator().next();
            if (!firstRealTableName.startsWith(logicTableName)) {
                throw new BaseException("logicTableName -> " + logicTableName + ", firstRealTableName -> " + firstRealTableName,
                        BaseCodeMsgEnum.SHARDING_SQL_MISUSE);
            }
        }
        // 获取所有的可用表名
        List<String>  targetRealTableList = new ArrayList<>(availableTargetNames);
        final Range<String> valueRange = shardingValue.getValueRange();
        
        /// 上界值
        StringBuilder upperTableInfo = new StringBuilder();
        if (valueRange.hasUpperBound()) {
            final String upperEndpoint = valueRange.upperEndpoint();
            String upperTableName = logicTableName + suffix(upperEndpoint);
            final BoundType upperBoundType = valueRange.upperBoundType();
            if (BoundType.CLOSED == upperBoundType) {
                upperTableInfo.append(" <= ").append(upperTableName);
            } else if (BoundType.OPEN == upperBoundType) {
                upperTableInfo.append(" < ").append(upperTableName);
            }
            targetRealTableList = targetRealTableList.stream().filter(realTableName -> {
                // BoundType.CLOSED - 闭区间， 上界包含当前upperEndpoint值 ， 即: <= upperEndpoint
                // BoundType.OPEN - 开区间， 上界不包含当前upperEndpoint值 ， 即: < upperEndpoint
                if (BoundType.CLOSED == upperBoundType) {
                    return realTableName.compareTo(upperTableName) <= 0;
                } else if (BoundType.OPEN == upperBoundType) {
                    return realTableName.compareTo(upperTableName) < 0;
                } else {
                    throw new RuntimeException("upperBoundType should not be null.");
                }
            }).collect(Collectors.toList());
        }
        
        /// 下界值
        StringBuilder lowerTableInfo = new StringBuilder();
        if (valueRange.hasLowerBound()) {
            final String lowerEndpoint = valueRange.lowerEndpoint();
            String lowerTableName = logicTableName + suffix(lowerEndpoint);
            final BoundType lowerBoundType = valueRange.lowerBoundType();
            if (BoundType.CLOSED == lowerBoundType) {
                lowerTableInfo.append(" >= ").append(lowerTableName);
            } else if (BoundType.OPEN == lowerBoundType) {
                lowerTableInfo.append(" > ").append(lowerTableName);
            }
            targetRealTableList = targetRealTableList.stream().filter(realTableName -> {
                // BoundType.CLOSED - 闭区间， 下界包含当前upperEndpoint值 ， 即: >= upperEndpoint
                // BoundType.OPEN - 开区间， 下界不包含当前upperEndpoint值 ， 即: > upperEndpoint
                if (BoundType.CLOSED == lowerBoundType) {
                    return realTableName.compareTo(lowerTableName) >= 0;
                } else if (BoundType.OPEN == lowerBoundType) {
                    return realTableName.compareTo(lowerTableName) > 0;
                } else {
                    throw new RuntimeException("lowerBoundType should not be null.");
                }
            }).collect(Collectors.toList());
        }
        
        if (targetRealTableList.size() == 0) {
            log.info("availableTargetNames -> {}", availableTargetNames);
            log.error("Cannot determine realTableName whose logicTableName is '{}'."
                            + (upperTableInfo.length() == 0 ? "" : "\tsatisfy: realTableName" + upperTableInfo)
                            + (lowerTableInfo.length() == 0 ? "" : "\tsatisfy: realTableName" + lowerTableInfo)
                    ,  logicTableName);
            throw new BaseException(BaseCodeMsgEnum.QUERY_TIME_RANGE_EXCEEDED_SHARDING_CONFIG_TIME_RANGE_LIMIT);
        }
        return targetRealTableList;
    }
    
    @Override
    public void init(Properties props) {
    
    }
}
