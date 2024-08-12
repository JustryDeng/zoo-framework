package com.ideaaedi.zoo.diy.feature.bpmn.api.bpmn20.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ideaaedi.zoo.commonbase.zoo_support.RawSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 用户任务
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Bpmn20UserTask extends Bpmn20Task implements RawSupport {
    
    /**
     * 负责人
     */
    protected String owner;
    
    /**
     * 分类
     */
    protected String category;
    
    /**
     * 办理人
     */
    protected String assignee;
    
    /**
     * 候选人
     */
    protected List<String> candidateUsers;
    
    /**
     * 候选组
     */
    protected List<String> candidateGroups;
    
    /**
     * 任务截至日期
     */
    private String dueDate;
    
    /**
     * 原始对象
     */
    @JsonIgnore
    @JSONField(serialize = false)
    protected Object raw;
    
}
