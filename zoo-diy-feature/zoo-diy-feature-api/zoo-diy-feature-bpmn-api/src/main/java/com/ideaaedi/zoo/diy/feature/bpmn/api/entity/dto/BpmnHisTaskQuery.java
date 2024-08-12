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
 * 历史任务查询
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnHisTaskQuery {
    
    /**
     * 任务ids
     */
    private Collection<String> taskIds;
    
    /**
     * 任务名
     */
    private String name;
    
    /**
     * 任务描述
     */
    private String description;
    
    /**
     * 任务办理人
     */
    private String assignee;
    
    /**
     * 任务是否已经被分配有办理人了（true-已经分配了办理人；false-尚未分配了办理人）
     */
    private Boolean ifTaskAssigned;
    
    /**
     * 任务负责人
     */
    private String owner;
    
    /**
     * 任务所属流程实例id
     */
    private String processInstanceId;
    
    /**
     * 任务所属流程实业务键
     */
    private String processInstanceBusinessKey;
    
    /**
     * 任务所属执行实例id
     */
    private String executionId;
    
    /**
     * 任务申领人
     */
    private String claimedBy;
    
    /**
     * 任务类别
     */
    private String category;
    
    /**
     * 任务状态
     */
    private String state;
    
    /**
     * 任务截至日期在指定日期之前
     */
    private LocalDateTime taskDueBefore;
    
    /**
     * 任务截至日期在指定日期之后
     */
    private LocalDateTime taskDueAfter;
    
    /**
     * 任务是否已完成（true-任务已完成；false-任务未完成）
     */
    private Boolean ifFinished;
    
    /**
     * 任务所属流程是否已完成（true-流程已完成；false-流程未完成）
     */
    private Boolean ifProcessFinished;
    
    /**
     * 任务完成日期在指定日期之前
     */
    private LocalDateTime taskCompletedBefore;
    
    /**
     * 任务完成日期在指定日期之后
     */
    private LocalDateTime taskCompletedAfter;
    
    /**
     * <pre>
     * 左 - 是否按照任务开始时间排序
     * 右 - 排序方式
     * </pre>
     */
    private Pair<Boolean, ORDER_TYPE> orderByTaskStartTimePair;
    
    /**
     * <pre>
     * 左 - 是否按照任务完成时间排序
     * 右 - 排序方式
     * </pre>
     */
    private Pair<Boolean, ORDER_TYPE> orderByTaskEndTimePair;
    
    /**
     * 根据任务变量值进行精确查询
     * <pre>
     * 左 - 变量名
     * 右 - 变量值
     * </pre>
     */
    private Collection<Pair<String, Object>> taskVariableValueEqualsColl;
    
    /** 是否顺带查询出本地任务变量 */
    private Boolean queryTaskLocalVariables;
    
    /** 是否顺带查询出流程变量 */
    private Boolean queryProcessVariables;
}