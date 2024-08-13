package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto;

import com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade.BpmnIdentityLinkType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 流程定义查询
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnHisProcessInstanceQuery {
    
    /**
     * 流程实例名称
     */
    @Nullable
    private String processInstanceName;
    
    /**
     * 流程实例ids
     */
    @Nullable
    private Collection<String> processInstanceIds;
    
    /**
     * 流程定义id
     */
    @Nullable
    private String processDefinitionId;
    
    /**
     * 流程定义key
     */
    @Nullable
    private String processDefinitionKey;
    
    /**
     * 流程定义keys
     */
    @Nullable
    private Collection<String> processDefinitionKeys;
    
    /**
     * 流程定义分类
     */
    @Nullable
    private String processDefinitionCategory;
    
    /**
     * 流程定义名称
     */
    @Nullable
    private String processDefinitionName;
    
    /**
     * 流程定义版本
     */
    @Nullable
    private Integer processDefinitionVersion;
    
    /**
     * 流程定义业务键
     */
    @Nullable
    private String processInstanceBusinessKey;
    
    /**
     * 部署id
     */
    @Nullable
    private String deploymentId;
    
    /**
     * 部署ids
     */
    @Nullable
    private Collection<String> deploymentIds;
    
    /**
     * 流程业务状态
     */
    @Nullable
    private String businessKey;
    
    /**
     * 流程业务状态
     */
    @Nullable
    private String businessStatus;
    
    /**
     * 业务实例是否已完成（true-查询已完成的；false-查询未完成的）
     */
    @Nullable
    private Boolean ifFinished;
    
    /**
     * 运行时的业务实例是否已删除（true-查询已删除的；false-查询未删除的）
     */
    @Nullable
    private Boolean ifDeleted;
    
    /**
     * 查询与指定用户有关联的流程实例 <br /> 注：这里的"关联"通常意味着用户作为任务的办理人、候选办理人、启动人等参与到了流程实例中
     */
    @Nullable
    private String userId;
    
    /**
     * 查询与指定类型的指定用户有关联的流程实例
     * <ul>
     *     <li>左：用户id</li>
     *     <li>右：类型，可用值见{@link BpmnIdentityLinkType}</li>
     * </ul>
     */
    @Nullable
    private Pair<String, String> userIdAndTypePair;
    
    /**
     * 查询与指定组（中任一item）有关联的流程实例
     */
    @Nullable
    private Collection<String> groups;
    
    /**
     * 查询与指定类型的指定组有关联的流程实例
     * <ul>
     *     <li>左：组id</li>
     *     <li>右：类型，可用值见{@link BpmnIdentityLinkType}</li>
     * </ul>
     */
    @Nullable
    private Pair<String, String> groupIdAndTypePair;
    
    /**
     * 查询相关变量中存在指定键值对的流程实例
     * <ul>
     *     <li>左：键</li>
     *     <li>右：值</li>
     * </ul>
     */
    @Nullable
    private Collection<Pair<String, String>> variableValuePairColl;
    
    /**
     * 流程实例开始时间早于指定时间
     */
    @Nullable
    private LocalDateTime startedBefore;
    
    /**
     * 流程实例开始时间晚于指定时间
     */
    @Nullable
    private LocalDateTime startedAfter;
    
    /**
     * 流程实例结束时间早于指定时间
     */
    @Nullable
    private LocalDateTime finishedBefore;
    
    /**
     * 流程实例结束时间晚于指定时间
     */
    @Nullable
    private LocalDateTime finishedAfter;
    
    /**
     * 流程实例发起人
     */
    @Nullable
    private String startedBy;
    
    /**
     * 是否查询出流程实例相关的变量
     */
    private Boolean queryProcessVariables;
    
}
