package com.ideaaedi.zoo.commonbase.zoo_info;

import com.ideaaedi.commonds.tuple.BasicNameValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * starter向导手册
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
public class DefaultDIYGuide implements DIYGuide {
    
    /** 展示优先级 */
    protected int order = Integer.MAX_VALUE;
    
    /** diy组件名称 */
    @NotBlank(message = "diy component name cannot be blank.")
    protected String name;
    
    /** 介绍 */
    @NotBlank(message = "diy component instruction cannot be blank.")
    protected String instruction;
    
    /** 手册 */
    protected String manual;
    
    /** 增强功能清单 */
    protected List<String> manifests;
    
    /** 联系方式 */
    protected List<BasicNameValue> contacts;
    
    /** 相关链接（k-链接名，value-链接地址） */
    protected List<BasicNameValue> links;
    
    @Override
    public int getOrder() {
        return order;
    }
    
    @Override
    public String name() {
        return name;
    }
    
    @Override
    public String instruction() {
        return instruction;
    }
    
    @Override
    public String manual() {
        return manual;
    }
    
    @Override
    public List<String> manifests() {
        return manifests;
    }
    
    @Override
    public List<BasicNameValue> contacts() {
        return contacts;
    }
    
    @Override
    public List<BasicNameValue> links() {
        return links;
    }
}
