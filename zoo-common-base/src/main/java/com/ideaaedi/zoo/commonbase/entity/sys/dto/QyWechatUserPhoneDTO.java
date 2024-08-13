package com.ideaaedi.zoo.commonbase.entity.sys.dto;

import lombok.Data;

/**
 * 企业微信用户手机号
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class QyWechatUserPhoneDTO {
    
    private String mobile;
    
    private WatermarkDTO watermark;
    
    @Data
    public static class WatermarkDTO {
        
        private String appid;
        
        private Integer timestamp;
    }
}
