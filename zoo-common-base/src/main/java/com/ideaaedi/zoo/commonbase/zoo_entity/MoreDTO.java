package com.ideaaedi.zoo.commonbase.zoo_entity;

import com.ideaaedi.zoo.commonbase.zoo_support.MoreSupport;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;

/**
 * 扩展信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class MoreDTO implements MoreSupport {
    
    /*
     * "更多信息"
     */
    @Schema(description = "更多信息")
    private Map<String, Object> more;
    
    @Override
    public Map<String, Object> more() {
        return this.more;
    }
}
