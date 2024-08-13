package com.ideaaedi.zoo.commonbase.entity.sys.vo.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 图片下载响应
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
public class SysFileDownloadImgRespVO {
	
	/**
	 * 文件id
	 */
	@Schema(description = "文件id")
    private Long fileId;
	
	/**
	 * 文件名
	 */
	@Schema(description = "文件名")
    private String originalFilename;
	
	
	/**
	 * base64后的内容
	 */
	@Schema(description = "base64后的内容")
	private String content;
	
	/**
	 * 获取简要信息
	 *
	 * @return 简要信息
	 */
	public String briefInfo(){
		String briefContent = content;
		if (briefContent.length() >= 100) {
			briefContent = briefContent.substring(0, 10) + " ...... " + briefContent.substring(80, 100);
		}
		return String.format("fileId -> %d, originalFilename -> %s, briefContent -> %s", fileId, originalFilename, briefContent);
	}
}
