package com.ideaaedi.zoo.diy.artifact.openfeign.holder;

import com.ideaaedi.zoo.diy.artifact.openfeign.properties.ZooOpenfeignProperties;
import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.Map;

/**
 * 本次feign http调用时的请求信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class OnceFeignInfo {
    
    /**
     * 目标地址前缀（协议可带可不带）
     * <p>
     * 注：前缀地址 + {@link FeignClient}解析出的api地址 = 完整的请求地址
     * </p>
     * <pre>
     * {@code
     *  如：http://127.0.0.1:8080
     * }
     * </pre>
     */
    private String urlPrefix;
    
    /**
     * 请求头
     * <pre>
     * 此请求头在{@link ZooOpenfeignProperties#getAppendHeaders()}之后放入，
     * 如果存在同名key，则此headers的值会覆盖{@link ZooOpenfeignProperties#getAppendHeaders()}的值
     * </pre>
     */
    private Map<String, Object> headers;
    
    public static OnceFeignInfo of(String urlPrefix, Map<String, Object> headers) {
        OnceFeignInfo onceFeignInfo = new OnceFeignInfo();
        onceFeignInfo.setUrlPrefix(urlPrefix);
        onceFeignInfo.setHeaders(headers);
        return onceFeignInfo;
    }
    
    public static OnceFeignInfo of(String urlPrefix) {
        OnceFeignInfo onceFeignInfo = new OnceFeignInfo();
        onceFeignInfo.setUrlPrefix(urlPrefix);
        return onceFeignInfo;
    }
}
