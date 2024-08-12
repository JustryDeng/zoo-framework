package com.ideaaedi.zoo.diy.feature.msg.api.mail.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * 邮件发送信息
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailSendInfo {
    
    /**
     * 发件人
     * <pre>
     * {@code
     *  格式为：  {名称}<{邮箱地址}>                    或    {邮箱地址}
     *      如：  邓沙利文<dengeryanger@gmail.com>     或     dengeryanger@gmail.com
     * }
     * </pre>
     */
    private String from;
    
    /**
     * 收件人
     * <pre>
     * {@code
     *  格式为：  {名称}<{邮箱地址}>                    或    {邮箱地址}
     *      如：  邓沙利文<dengeryanger@gmail.com>     或     dengeryanger@gmail.com
     * }
     * </pre>
     */
    @Nonnull
    private Collection<String> to;
    
    /**
     * 主题
     */
    @Nonnull
    private String subject;
    
    /**
     * 内容
     */
    @Nonnull
    private String content;
    
    /**
     * 是否为html
     */
    private boolean isHtml;
    
    /**
     * 抄送
     * <pre>
     * {@code
     *  格式为：  {名称}<{邮箱地址}>                    或    {邮箱地址}
     *      如：  邓沙利文<dengeryanger@gmail.com>     或     dengeryanger@gmail.com
     * }
     * </pre>
     */
    @Nullable
    private Collection<String> cc;
    
    /**
     * 密送
     * <pre>
     * {@code
     *  格式为：  {名称}<{邮箱地址}>                    或    {邮箱地址}
     *      如：  邓沙利文<dengeryanger@gmail.com>     或     dengeryanger@gmail.com
     * }
     * </pre>
     */
    @Nullable
    private Collection<String> bcc;
    
    /**
     * 内嵌资源
     * <pre>
     *  内嵌资源的使用方式为：
     *    第一步：先在正文中使用特定格式的占位符占位
     *    第二步：然后添加对应的内嵌资源
     *    第三步：发送邮件时{@link #isHtml}设置为true，否则不会解析内嵌资源
     *
     *  jakarta mail的使用方式，如：
     *  {@code
     *   // 内嵌资源
     *  String uid = "uid9527";
     *  messageHelper.addInline(uid, imgFile);
     *
     *  // 构建邮件正文（通过src = "cid:xxx"的方式引用名为xxx的内嵌资源）
     *  String htmlBody = "<html><body><h1>你好!</h1>" +
     *          "<p>这是一封测试邮件。</p>" +
     *          "<p>下面是内嵌的图片：</p>" +
     *          "<img src=\"cid:" + uid + "\"></body></html>";
     *  }
     * </pre>
     *
     */
    @Nullable
    private Collection<MailFile> inlines;
    
    /**
     * 附件
     */
    @Nullable
    private Collection<MailFile> attachments;
}
