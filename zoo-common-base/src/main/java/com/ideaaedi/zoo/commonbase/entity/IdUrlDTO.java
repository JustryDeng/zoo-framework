package com.ideaaedi.zoo.commonbase.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * id和url
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.1.E
 */
@Data
public class IdUrlDTO {
    
    @Schema(description = "id")
    private Long id;
    
    @Schema(description = "url")
    private String url;
    
    /**
     * 快速创建
     */
    public static IdUrlDTO create(Long id, String url) {
        IdUrlDTO dto = new IdUrlDTO();
        dto.setId(id);
        dto.setUrl(url);
        return dto;
    }
}
