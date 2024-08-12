package com.ideaaedi.zoo.commonbase.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页信息模型
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class PageInfo<T> implements Serializable {
    
    /** 总条数 */
    @Schema(description = "总条数")
    private long total;
    
    /** 页码 */
    @Schema(description = "页码")
    private int pageNum;
    
    /** 每页条数 */
    @Schema(description = "每页条数")
    private int pageSize;
    
    /** 数据集 */
    @Schema(description = "数据集")
    private List<T> dataList;
    
    /**
     * 快速构造
     */
    public static <T> PageInfo<T> of(long total, int pageNum, int pageSize) {
        return PageInfo.of(total, pageNum, pageSize, null);
    }
    
    /**
     * 快速构造
     */
    public static <T> PageInfo<T> of(long total, int pageNum, int pageSize, List<T> dataList) {
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setTotal(total);
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        pageInfo.setDataList(dataList);
        return pageInfo;
    }
}
