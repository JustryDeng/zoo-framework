package com.ideaaedi.zoo.commonbase.entity.sys.bo;

import lombok.Data;

/**
 * <p>
 * api端点信息
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class ApiInfoBO {

    /**
     * 资源路径
     */
    private String path;

    /**
     * 请求该资源所需要的方法(多个之间使用逗号分割)
     */
    private String requestMethod;

}
