package com.ideaaedi.zoo.commonbase.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.url.UrlImageConverter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.alibaba.excel.util.IoUtils;
import com.ideaaedi.zoo.commonbase.support.EnumDescriptor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URLConnection;

/**
 * easyexecl转换器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.1.E
 */
public interface EasyExcelConverter {
    
    /**
     * 枚举name 转换器
     */
    class EnumNameConverter implements Converter<Enum<?>> {
        
        private final Class<?> clazz;
        
        public EnumNameConverter(Class<?> clazz) {
            this.clazz = clazz;
        }
        
        @Override
        public Class<?> supportJavaTypeKey() {
            return clazz;
        }
        
        @Override
        public WriteCellData<Enum<?>> convertToExcelData(Enum<?> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            return new WriteCellData<>(value == null ? "" : value.name());
        }
    }
    
    /**
     * 枚举EnumDescriptor 转换器
     */
    class EnumDescriptorConverter implements Converter<EnumDescriptor> {
    
        private final Class<?> clazz;
    
        public EnumDescriptorConverter(Class<?> clazz) {
            this.clazz = clazz;
        }
    
        @Override
        public Class<?> supportJavaTypeKey() {
            return clazz;
        }
        
        @Override
        public WriteCellData<Enum<?>> convertToExcelData(EnumDescriptor enumDescriptor, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            return new WriteCellData<>(enumDescriptor == null ? "" : enumDescriptor.obtainDescription());
        }
    }
    
    /**
     * url地址转换为图片
     * <p/>
     * 参考{@link UrlImageConverter}
     *
     * @since 1.0.0
     */
    class UrlStrImageConverter implements Converter<String> {
        
        public static int urlConnectTimeout = 1000;
        
        public static int urlReadTimeout = 5000;
        
        @Override
        public Class<?> supportJavaTypeKey() {
            return String.class;
        }
        
        @Override
        public WriteCellData<?> convertToExcelData(String url, ExcelContentProperty contentProperty,
                                                   GlobalConfiguration globalConfiguration) throws IOException {
            if (StringUtils.isBlank(url)) {
                return new WriteCellData<>("");
            }
            InputStream inputStream = null;
            try {
                URLConnection urlConnection = URI.create(url).toURL().openConnection();
                urlConnection.setConnectTimeout(urlConnectTimeout);
                urlConnection.setReadTimeout(urlReadTimeout);
                inputStream = urlConnection.getInputStream();
                byte[] bytes = IoUtils.toByteArray(inputStream);
                return new WriteCellData<>(bytes);
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
    }
    
    /**
     * 迭代器 转换器
     */
    class IterableConverter implements Converter<Iterable<?>> {
        
        private final Class<?> clazz;
        
        public IterableConverter(Class<?> clazz) {
            this.clazz = clazz;
        }
        
        @Override
        public Class<?> supportJavaTypeKey() {
            return clazz;
        }
        
        @Override
        public WriteCellData<Enum<?>> convertToExcelData(Iterable<?> value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            return new WriteCellData<>("不支持对此数组字段进行导出");
        }
    }
}
