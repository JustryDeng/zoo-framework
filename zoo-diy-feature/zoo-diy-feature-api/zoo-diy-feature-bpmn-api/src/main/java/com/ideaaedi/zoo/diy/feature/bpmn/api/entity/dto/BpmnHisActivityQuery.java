package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto;

import com.ideaaedi.zoo.diy.feature.bpmn.api.enums.ORDER_TYPE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 历史活动查询
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnHisActivityQuery {
    
    /**
     * 关联的活动（实例）id
     */
    private String activityId;
    
    /**
     * 关联的流程实例id
     */
    private String processInstanceId;
    
    /**
     * 关联的流程定义id
     */
    private String processDefinitionId;
    
    /**
     * 关联的执行实例id
     */
    private String executionId;
    
    /**
     * 活动名称
     */
    private String activityName;
    
    /**
     * 活动类型
     */
    private String activityType;
    
    /**
     * 活动类型集合
     */
    private Collection<String> activityTypes;
    
    /**
     * 任务办理人
     */
    private String taskAssignee;
    
    /**
     * 活动是否已完成（true-查询已完成的；false-查询未完成的）
     */
    private Boolean ifFinished;
    
    /**
     * 在指定时间之前开始
     */
    private LocalDateTime startedBefore;
    
    /**
     * 在指定时间之后开始
     */
    private LocalDateTime startedAfter;
    
    /**
     * 在指定时间之前完成
     */
    private LocalDateTime finishedBefore;
    
    /**
     * 在指定时间之后完成
     */
    private LocalDateTime finishedAfter;
    
    /**
     * 删除原因
     */
    private String deleteReason;
    
    /**
     * <pre>
     * 左 - 是否按照活动完成时间排序
     * 右 - 排序方式
     * </pre>
     */
    private Pair<Boolean, ORDER_TYPE> orderByHisActivityEndTimePair;
    
}