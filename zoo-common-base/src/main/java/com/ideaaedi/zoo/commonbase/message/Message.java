package com.ideaaedi.zoo.commonbase.message;

import com.ideaaedi.zoo.commonbase.entity.BaseDTO;
import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 消息模型
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface Message extends Serializable {
    
    /**
     * 根消息id
     *
     * @return 根消息id
     */
    default long rootId() {
        return 0L;
    }
    
    /**
     * 父消息id
     *
     * @return 父消息id
     */
    default long parentId() {
        return 0L;
    }
    
    /**
     * 消息标题
     *
     * @return 消息标题
     */
    @Nullable
    default String title() {
        return null;
    }
    
    /**
     * 消息内容
     *
     * @return 消息内容
     */
    @Nonnull
    String content();
    
    /**
     * 消息内容类型
     *
     * @return 消息内容类型
     */
    @Nonnull
    default MimeType contentType() {
        return MediaType.TEXT_PLAIN;
    }
    
    /**
     * 消息内容的标签
     *
     * @return 消息内容的标签
     */
    @Nonnull
    default Set<String> contentTags() {
        return new LinkedHashSet<>(4);
    }
    
    /**
     * 消息过期时间(单位秒; 0-永不过期)
     *
     * @return 消息过期时间(单位秒; 0-永不过期)
     */
    @Nonnull
    default long ttl() {
        return 0L;
    }
    
    /**
     * 业务端产生消息的时间
     *
     * @return 业务端产生消息的时间
     */
    @Nonnull
    LocalDateTime generateTime();
    
    /**
     * 消息状态
     *
     * @return 消息状态
     */
    @Nonnull
    State state();
    
    /**
     * 消费配置
     *
     * @return 消费配置
     */
    @Nonnull
    ConsumeConfig consumeConfig();
    
    /**
     * 消息消费配置
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
     * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    class ConsumeConfig extends BaseDTO {
        
        /**
         * 是否异步消费(true-异步消费; false-同步消费)
         */
        private boolean async;
        
        /**
         * 消费时间
         * <p>
         * 当{@link ConsumeConfig#async}为true，且consumeTimeList不为空时，此字段生效
         */
        private List<LocalDateTime> consumeTimeList;
        
    }
    
    /**
     * 消息状态
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
     * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    enum State implements EnumDescriptor {
        
        DRAFT(-3, "草稿态"),
        INVALID(-2, "已失效"),
        EXPIRED(-1, "已过期"),
        TO_EXEC(0, "待执行"),
        EXEC_SUCCESS(1, "执行成功"),
        EXEC_FAIL(2, "执行失败"),
        EXEC_ING(3, "执行中")
        ;
        
        /** 编码 */
        final int code;
        
        /** 描述 */
        final String desc;
        
        State(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        
        @Override
        public String obtainDescription() {
            return this.code + "-" + this.desc;
        }
    }
}
