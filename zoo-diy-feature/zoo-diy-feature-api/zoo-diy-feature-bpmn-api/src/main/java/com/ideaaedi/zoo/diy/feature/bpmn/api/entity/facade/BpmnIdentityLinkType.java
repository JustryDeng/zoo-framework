package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

/**
 * 用于定义用户、组与任务或流程实例之间的关系类型
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnIdentityLinkType {
    
    /**
     * 办理人
     */
    String ASSIGNEE = "assignee";
    
    /**
     * 候选人
     */
    String CANDIDATE = "candidate";
    
    /**
     * 负责人
     */
    String OWNER = "owner";
    
    /**
     * 发起人呢
     */
    String STARTER = "starter";
    
    /**
     * 参与人
     */
    String PARTICIPANT = "participant";
    
    /**
     * 激活人（注：CMMN引擎中重新激活案例实例（Case Instance）的激活人）
     */
    String REACTIVATOR = "reactivator";
}
