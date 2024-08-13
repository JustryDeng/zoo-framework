package com.ideaaedi.zoo.commonbase.support;

import com.ideaaedi.zoo.commonbase.entity.sys.po.SysDictPO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * 字典信息填充器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.1.E
 */
public interface DictFiller extends Filler {
    
    /**
     * 获取dict_path字符串集合
     *
     * @see com.ideaaedi.cloud.commonspring.support.CommonService#obtainFileIdUrl(List) 参数介绍
     *
     * @return dict_path字符串集合
     */
    @Nullable
    List<String> obtainDictPathsList();
    
    /**
     * 填充字典信息
     *
     * @param dictPathAndDictMap key-字典路径; value-字典信息对象
     */
    void fillDict(@Nonnull Map<String, SysDictPO> dictPathAndDictMap);
}
