package com.ideaaedi.zoo.commonbase.zoo_support;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * 扩展
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface MoreSupport {
    
    /**
     * 更多信息
     *
     * @return 更多信息
     */
    @Nullable
    default Map<String, Object> more() {
        return null;
        
    }
}
