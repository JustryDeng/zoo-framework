package com.ideaaedi.zoo.diy.feature.expression.engine.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 表达式脚本信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpressionScriptInfoDTO {
    /**
     * 脚本id
     */
    private Serializable id;
    
    /**
     * 用于判断当前逻辑是否支持使用expressionScript的脚本
     * <p>
     * 注：此脚本的运行结果应该返回boolean值
     * </p>
     */
    private String supportScript;
    
    /**
     * 表达式脚本
     */
    private String expressionScript;
}
