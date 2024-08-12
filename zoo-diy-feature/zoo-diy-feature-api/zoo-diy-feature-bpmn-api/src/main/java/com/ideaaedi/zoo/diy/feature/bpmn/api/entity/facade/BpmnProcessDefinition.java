package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;

/**
 * 流程实例
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnProcessDefinition extends MoreSupport {
    /**
     * id
     *
     * @return id
     */
    String getId();
    
    /**
     * 唯一key
     *
     * @return 唯一key
     */
    String getKey();
    
    /**
     * 流程定义名称
     *
     * @return 流程定义名称
     */
    String getName();
    
    /**
     * 分类
     *
     * @return 分类
     */
    String getCategory();
    
    /**
     * 描述
     *
     * @return 描述
     */
    String getDescription();
    
    /**
     * 版本号
     *
     * @return 版本号
     */
    String getVersion();
    
    /**
     * 生成此流程模型使用的资源名
     *
     * @return 使用的资源名
     */
    String getResourceName();
    
    /**
     * 部署此流程模型的对应部署id
     *
     * @return 对应部署id
     */
    String getDeploymentId();
    
    /**
     * 所属租户
     *
     * @return 所属租户
     */
    String getTenant();
    
    /**
     * 此流程模型是否处于挂起停用状态
     *
     * @return 是否处于挂起停用状态
     */
    Boolean getIfSuspended();
}
