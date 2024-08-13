package com.ideaaedi.zoo.diy.artifact.openfeign.properties;

import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Validated
@ConfigurationProperties(prefix = "zoo.openfeign")
public class ZooOpenfeignProperties {
    
    @Valid
    @NestedConfigurationProperty
    private List<HeaderTrans> appendHeaders;
    
    /**
     * feign发送请求时，往请求头中写入的信息
     */
    @Data
    public static class HeaderTrans {
        
        /** 请求头 */
        @NotBlank
        private String header;
        
        /** 请求头的值（或从source取值的键） */
        @NotBlank
        private String value;
    
        /** 请求头的值（存储源） */
        @NotNull(message = "value source cannot be null.")
        private HeaderValueSource source;
    
        /** 是否对放进请求头中的值进行url-encode */
        private boolean encodeValue = false;
        
        /** 当value不存在时，默认值 */
        private String defaultValue;
    }
    
    public enum HeaderValueSource implements EnumDescriptor {
        
        DIRECT("直接取配置的值"),
        
        MDC("从MDC取值"),
        
        REQUEST_HEADER("从请求头取值"),
        
        SPRING_ENV("从spring环境变量中取");
        
        private final String desc;
    
        HeaderValueSource(String desc) {
            this.desc = desc;
        }
    
        @Override
        public String obtainDescription() {
            return this.desc;
        }
    }
    
}
