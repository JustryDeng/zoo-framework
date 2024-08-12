package com.ideaaedi.zoo.diy.artifact.mybatisplus.handler;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

/**
 * mybatis List<T>类型转换
 *
 * @author JustryDeng
 * @since 1.0.0
 */
@Slf4j
@MappedTypes({Object.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public abstract class AbstractJsonListTypeHandler<T> extends AbstractJsonTypeHandler<List<T>> {
    
    @Override
    protected List<T> parse(String json) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        return JSON.parseArray(json, provideTClazz());
    }
    
    @Override
    protected String toJson(List<T> obj) {
        return JSON.toJSONString(obj, JSONWriter.Feature.WriteMapNullValue,
                JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteNullStringAsEmpty);
    }
    
    /**
     * 提工 T 代表的实体类类型
     *
     * @return T代表的实体类类型
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    protected abstract Class<T> provideTClazz();
    
    
    /**
     * List<Long>类型转换
     */
    public static class LongList extends AbstractJsonListTypeHandler<Long> {
        
        @Override
        protected Class<Long> provideTClazz() {
            return Long.class;
        }
    }
    
    /**
     * List<String>类型转换
     */
    public static class StringList extends AbstractJsonListTypeHandler<String> {
        
        @Override
        protected Class<String> provideTClazz() {
            return String.class;
        }
    }
}
