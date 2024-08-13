package com.ideaaedi.zoo.diy.feature.msg.api.mail.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Setter
@Getter
@ToString
public class MailFile {
    
    private MailFile() {
    }
    
    /**
     * 文件唯一id
     * <p>
     * 注：在使用内嵌文件时，需要此字段，此字段值将作为内嵌文件的唯一标识
     * </p>
     */
    private String uid;
    
    /**
     * 文件名
     * <p>
     * 注：一般在{@link #fileBytes}承载文件时需要；如果是{@link #fileDirect}承载文件的话，可不对此字段赋值，能自动推断
     * </p>
     */
    private String filename;
    
    /**
     * 文件content-type
     * <p>
     * 注：一般在{@link #fileBytes}承载文件时需要；如果是{@link #fileDirect}承载文件的话，可不对此字段赋值，能自动推断
     * </p>
     */
    private String contentType;
    
    /**
     * 文件
     * <p>
     * 注：与{@link #fileBytes}不能同时为null
     * </p>
     */
    private File fileDirect;
    
    /**
     * 文件
     * <p>
     * 注：与{@link #fileDirect}不能同时为null
     * </p>
     */
    private byte[] fileBytes;
    
    public static MailFile of(@Nonnull File fileDirect) {
        MailFile mailFile = new MailFile();
        mailFile.setFileDirect(fileDirect);
        return mailFile;
    }
    
    public static MailFile of(@Nonnull byte[] fileBytes) {
        MailFile mailFile = new MailFile();
        mailFile.setFileBytes(fileBytes);
        return mailFile;
    }
    
    public MailFile filename(String filename) {
        this.setFilename(filename);
        return this;
    }
    
    public MailFile uid(String uid) {
        this.setUid(uid);
        return this;
    }
    
    public MailFile contentType(String contentType) {
        this.setContentType(contentType);
        return this;
    }
    
}
