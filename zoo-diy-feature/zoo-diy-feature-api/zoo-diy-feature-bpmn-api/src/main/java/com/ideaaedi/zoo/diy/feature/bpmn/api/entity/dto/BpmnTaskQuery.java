package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.Collection;

/**
 * 任务查询
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmnTaskQuery {
    
    /** id集合 */
    @Nullable
    private Collection<String> ids;
    
    /** 名称 */
    @Nullable
    private String name;
    
    /** 状态 */
    @Nullable
    private String sate;
    
    /** 分配人 */
    @Nullable
    private String assignee;
    
    /** 流程实例的id */
    @Nullable
    private String processInstanceId;
    
    /** 流程定义的key */
    @Nullable
    private String processDefinitionKey;
    
    /** 部署id */
    @Nullable
    private String deploymentId;
    
    /** 是否顺带查询出本地任务变量 */
    private Boolean queryTaskLocalVariables;
    
    /** 是否顺带查询出流程变量 */
    private Boolean queryProcessVariables;
    
    /**
     * 办理人候选人
     * <br >
     * 注：不论任务是不是有多个候选人，只要候选人中包含了candidateUser指定的候选人，那么就能查出来
     */
    @Nullable
    private String candidateUser;
    
    /**
     * 办理人候选组
     * <br >
     * 注：不论任务是不是有多个候选组，只要候选组中包含了candidateGroup指定的候选组，那么就能查出来
     */
    @Nullable
    private String candidateGroup;
    
    /**
     * 办理人候选集合
     * <br >
     * 注：只要候选组中包含了taskCandidateGroupIn中的任意一个item，那么就能查出来
     */
    @Nullable
    private Collection<String> candidateGroups;
    
    /**
     * 查询指定用户的任务。<ul>
     *     <li>左：用户的id</li>
     *     <li>右：用户拥有的group</li>
     * </ul>
     * <br >
     * 注：查询范围为：办理人、候选人 、候选组，任意一个匹配都能查出来
     */
    @Nullable
    private Pair<String, Collection<String>> userIdAndUserGroupsPair;
}
