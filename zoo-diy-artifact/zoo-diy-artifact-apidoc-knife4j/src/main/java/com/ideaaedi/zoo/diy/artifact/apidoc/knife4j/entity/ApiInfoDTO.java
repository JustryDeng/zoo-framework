package com.ideaaedi.zoo.diy.artifact.apidoc.knife4j.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.lang.reflect.Method;

/**
 * 接口信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Schema(description = "接口信息")
public class ApiInfoDTO {
    
    @Schema(description = "所在类")
    private Class<?> clazz;
    
    @Schema(description = "所在方法")
    private Method method;
    
    @Schema(description = "接口信息")
    private RequestMappingInfo requestMappingInfo;

}
