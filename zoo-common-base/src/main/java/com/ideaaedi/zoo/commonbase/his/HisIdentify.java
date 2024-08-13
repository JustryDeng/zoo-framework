package com.ideaaedi.zoo.commonbase.his;

/**
 * 历史数据模型标识器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface HisIdentify {
    
    /**
     * 业务po模型的id字段名
     */
    String BIZ_ID_PROPERTY_NAME = "id";
    
    /**
     * 设置版本号
     */
    void setHisVersion(Long hisVersion);
    
    /**
     * 获取业务数据id
     *
     * @return 业务数据id
     */
    <T extends Number> T getId();
    
}
