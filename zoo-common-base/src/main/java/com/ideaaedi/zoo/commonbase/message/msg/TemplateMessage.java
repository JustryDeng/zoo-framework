package com.ideaaedi.zoo.commonbase.message.msg;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class TemplateMessage extends AbstractMessage {
    
    /**
     * 模板id
     */
    private Long templateId;
    
    /**
     * 模板要使用到的变量信息
     */
    private Map<String, Object> env;
    
}
