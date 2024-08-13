package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

import java.time.LocalDateTime;

/**
 * 评论
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnComment extends MoreSupport {
    
    /**
     * id
     */
    String getId();
    
    /**
     * 父id
     */
    String getPid();
    
    /**
     * 评论人
     */
    String getUserId();
    
    void setUserId(String userId);
    
    /**
     * 评论时间
     */
    LocalDateTime getDateTime();
    
    void setDateTime(LocalDateTime dateTime);
    
    /**
     * 关联的任务
     */
    String getTaskId();
    
    void setTaskId(String taskId);
    
    /**
     * 关联的流程实例
     */
    String getProcessInstanceId();
    
    void setProcessInstanceId(String processInstanceId);
    
    /**
     * 评论类型
     */
    String getType();
    
    void setType(String type);
    
    /**
     * 评论内容
     */
    String getFullMessage();
    
    void setFullMessage(String fullMessage);
}
