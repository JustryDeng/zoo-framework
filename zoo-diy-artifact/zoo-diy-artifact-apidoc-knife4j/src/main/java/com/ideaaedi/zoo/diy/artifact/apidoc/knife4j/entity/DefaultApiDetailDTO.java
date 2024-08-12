package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 接口详情信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "接口详情信息")
public class DefaultApiDetailDTO {
    
    @Schema(description = "所在类")
    private Class<?> clazz;
    
    @Schema(description = "所在方法")
    private Method method;
    
    @Schema(description = "类描述")
    private String classDesc;
    
    @Schema(description = "方法描述")
    private String methodDesc;
    
    @Schema(description = "接口路径")
    private Set<String> requestPathSet;
    
    @Schema(description = "接口方法")
    private Set<RequestMethod> requestMethodSet;

}
