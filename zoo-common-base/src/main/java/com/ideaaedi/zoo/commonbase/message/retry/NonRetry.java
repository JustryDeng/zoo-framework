package com.ideaaedi.zoo.commonbase.message.retry;

import com.ideaaedi.zoo.commonbase.message.Retry;
import lombok.Data;

/**
 * 不重试
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class NonRetry implements Retry<Void> {
    
    @Override
    public Void param() {
        return null;
    }
}
