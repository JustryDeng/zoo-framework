package com.ideaaedi.zoo.diy.artifact.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ideaaedi.zoo.commonbase.zoo_util.ZooContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * 设置字段自动填充 <br /> <br />
 * <a href="https://baomidou.com/pages/4c6bcf/">官网</a>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {
    
    private static final OperatorIdProvider DEFAULT_OPERATOR_ID_PROVIDER = new OperatorIdProvider() {
    };
    
    private final OperatorIdProvider operatorIdProvider;
    
    public MybatisPlusMetaObjectHandler(OperatorIdProvider operatorIdProvider) {
        this.operatorIdProvider = operatorIdProvider;
    }
    
    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = operatorIdProvider == null ? DEFAULT_OPERATOR_ID_PROVIDER.operatorId(metaObject) :
                operatorIdProvider.operatorId(metaObject);
        // 这里是PO的字段名而不是表的列名
        LocalDateTime now = LocalDateTime.now();
        this.strictInsertFill(metaObject, "createdBy", Long.class, userId);
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, now);
        this.strictUpdateFill(metaObject, "updatedBy", Long.class, userId);
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, now);
    }
    
    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = operatorIdProvider == null ? DEFAULT_OPERATOR_ID_PROVIDER.operatorId(metaObject) :
                operatorIdProvider.operatorId(metaObject);
        // 这里是PO的字段名而不是表的列名
        this.strictUpdateFill(metaObject, "updatedBy", Long.class, userId);
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
    }
    
    /**
     * 数据操作人id提供器
     *
     * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
     * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
     * @since 1.0.0
     */
    public interface OperatorIdProvider {
        
        /**
         * 获取数据操作人id
         *
         * @param metaObject 当前mybatis sql操作元数据
         *
         * @return 数据操作人id
         */
        @Nullable
        default Long operatorId(MetaObject metaObject) {
            return ZooContext.Auth.currUserId();
        }
    }
}
