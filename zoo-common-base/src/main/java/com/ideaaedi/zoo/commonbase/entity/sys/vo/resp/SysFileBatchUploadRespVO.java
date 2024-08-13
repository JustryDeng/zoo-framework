package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 批量上传文件响应
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 2021.0.1.D
 */
@Data
public class SysFileBatchUploadRespVO {
    
    /**
     * (带后缀的)文件名-文件id map
     */
    @Schema(description = "(带后缀的)文件名-文件id map")
    private Map<String, Long> fileNameAndFileIdMap;
    
    /**
     * 文件详情集合
     * <p>
     * 有序的，对应上传时文件的顺序
     */
    @Schema(description = "文件详情集合（注：有序的，对应上传时文件的顺序）")
    private List<SysFileDetailRespVO> fileDetailList;
}
