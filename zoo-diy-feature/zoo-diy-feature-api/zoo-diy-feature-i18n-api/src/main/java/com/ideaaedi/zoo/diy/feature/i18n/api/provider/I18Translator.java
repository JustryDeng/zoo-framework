package com.ideaaedi.zoo.diy.feature.i18n.api.provider;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * i18n 翻译器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface I18Translator {
    
    /**
     * 获取本地的i18n信息
     *
     * @param key 键值。 如: name=姓名, 中的 name
     *
     * @return key对应的value值
     */
    @Nonnull
    String i18n(@Nonnull String key);
    
    /**
     * 获取指定地区的i18n信息
     *
     * @param key 键值。 如: name=姓名, 中的 name
     * @param locale 区域实例
     *
     * @return key对应的value值
     */
    @Nonnull
    String i18n(@Nonnull String key, Locale locale);
    
    /**
     * 获取本地的i18n信息。
     *
     * @param key 键值。 如: name=姓名, 中的 name
     * @param args 占位符对应的元素值
     *
     * @return key对应的value值
     */
    @Nonnull
    String i18n(@Nonnull String key, Object... args);
    
    /**
     * 获取指定地区的i18n信息。
     *
     * @param key 键值。 如: name=姓名, 中的 name
     * @param locale 区域实例
     * @param args 占位符对应的元素值
     *
     * @return key对应的value值
     */
    @Nonnull
    String i18n(@Nonnull String key, Locale locale, Object... args);
}
