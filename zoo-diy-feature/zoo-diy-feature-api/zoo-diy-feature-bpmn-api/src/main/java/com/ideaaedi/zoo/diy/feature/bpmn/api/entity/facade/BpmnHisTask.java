package com.ideaaedi.zoo.diy.feature.bpmn.api.entity.facade;

import java.time.LocalDateTime;

/**
 * 相关任务
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BpmnHisTask extends BpmnHisDateTime, BpmnTask {
    
    /*
     * 删除原因
     *
     * @return 'completed' | 'deleted' | any other user defined string
     */
    String getDeleteReason();
    
    /**
     * 任务结束时间
     *
     * @return 任务结束时间
     */
    LocalDateTime getEndTime();
    
    /**
     * 任务完成人
     *
     * @return 任务完成人
     */
    String getCompletedBy();
    
    /**
     * 任务总耗时
     * <p>
     * between {@link #getEndTime()} and {@link #getCreateTime()} in milliseconds.
     * </p>
     *
     * @return 任务总耗时
     */
    Long getDurationInMillis();
    
    /**
     * 任务执行耗时
     * <p>
     * between {@link #getEndTime()} and {@link #getClaimTime()} in milliseconds.
     * </p>
     *
     * @return 任务执行耗时
     */
    Long getWorkTimeInMillis();
}
