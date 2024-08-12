package com.ideaaedi.zoo.diy.feature.expression.engine.api.spi;

import com.ideaaedi.zoo.diy.feature.expression.engine.api.entity.ExpressionScriptInfoDTO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * 表达式脚本加载器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface ExpressionScriptLoader {
    
    /**
     * 获取表达式脚本信息
     *
     * @param bizKey 业务键
     *
     * @return 与业务键关联的表达式脚本信息集合<br/>注：业务上将使用第一个可执行的表达式（即：将使用第一个
     *            {@link ExpressionScriptInfoDTO#getSupportScript()}结果为true的
     *            对象的{@link ExpressionScriptInfoDTO#getExpressionScript()}运行）
     */
    @Nullable
    List<ExpressionScriptInfoDTO> load(@Nonnull String bizKey);
}