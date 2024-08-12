package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

import java.time.LocalDateTime;

/**
 * 流程实例
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnProcessInstance extends MoreSupport {
    /**
     * id
     *
     * @return id
     */
    String getId();
    
    /**
     *父流程id
     */
    String getPid();
    
    /**
     * 流程业务key
     */
    String getBusinessKey();
    
    /**
     * 流程业务状态
     */
    String getBusinessStatus();
    
    /**
     * 是否已暂停
     */
    Boolean getSuspended();
    
    /**
     * 是否已结束
     */
    Boolean getEnded();
    
    /**
     * 流程所属租户
     */
    String getTenant();
    
    /**
     * 流程名
     */
    String getName();
    
    /**
     * 描述
     */
    String getDescription();
    
    /**
     * 流程实例开始用户
     */
    String getStartUserId();
    
    /**
     * 流程实例开始时间
     */
    LocalDateTime getStartTime();
}
