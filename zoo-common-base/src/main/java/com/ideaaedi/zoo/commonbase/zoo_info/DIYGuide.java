package com.ideaaedi.zoo.commonbase.zoo_info;

import com.ideaaedi.commonds.tuple.BasicNameValue;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * DIY使用指南
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface DIYGuide extends Ordered {
    
    /*
     * diy组件名称
     *
     * @return diy组件名称
     */
    String name();
    
    /*
     * 介绍
     *
     * @return 介绍
     */
    String instruction();
    
    /*
     * 使用手册
     */
    String manual();
    
    /*
     * 增强功能清单
     */
    List<String> manifests();
    
    /*
     * 联系方式
     *
     * @return 联系方式
     */
    List<BasicNameValue> contacts();
    
    /*
     * 相关链接
     */
    List<BasicNameValue> links();
}
