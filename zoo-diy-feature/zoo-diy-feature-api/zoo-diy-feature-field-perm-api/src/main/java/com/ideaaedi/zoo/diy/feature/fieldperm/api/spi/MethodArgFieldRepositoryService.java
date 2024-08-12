package com.ideaaedi.zoo.diy.feature.fieldperm.api.spi;

import com.ideaaedi.zoo.diy.feature.fieldperm.api.entity.MethodArgFieldInfo;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 存储服务
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface MethodArgFieldRepositoryService {
    
    /**
     * 初始化需要字段鉴权的方法的参数字段信息
     * <p>
     * 注：这在启动时会调用一次
     * </p>
     *
     * @param methodArgFieldInfoList 方法的参数字段
     */
    void initSavaOrUpdate(@Nonnull List<MethodArgFieldInfo> methodArgFieldInfoList);
    
    /**
     * 查询当前请求是否需要字段校验 以及 当前请求不允许赋值的字段列表
     *
     * @param methodUid 方法uid
     * @return <ul>
     *     <li>左（@Nonnull） - 当前请求是否需要进行字段权限校验</li>
     *     <li>右（@Nullable） - 当需要进行校验时，当前请求不允许赋值的字段列表</li>
     * </ul>
     */
    @Nonnull
    Pair<Boolean, List<MethodArgFieldInfo>> ifPermAndNotAllowedList(@Nonnull String methodUid);
}