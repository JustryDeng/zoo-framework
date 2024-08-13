package com.ideaaedi.zoo.diy.feature.i18n.api;

import com.ideaaedi.zoo.diy.feature.i18n.api.provider.I18Translator;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.util.Locale;

/**
 * i18n工具类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public final class I18nUtil {
    
    private static final Object[] EMPTY_ARRAY = new Object[0];
    
    static I18Translator i18Translator;
    
    /**
     * 获取本地的i18n信息
     *
     * @param key 键值。 如: name=姓名, 中的 name
     *
     * @return key对应的value值
     */
    @Nonnull
    public static String i18n(@Nonnull String key) {
        if (i18Translator == null) {
            return key;
        }
        return i18Translator.i18n(key);
    }
    
    /**
     * 获取指定地区的i18n信息
     *
     * @param key 键值。 如: name=姓名, 中的 name
     * @param locale 区域实例
     *
     * @return key对应的value值
     */
    @Nonnull
    public static String i18n(@Nonnull String key, Locale locale) {
        if (i18Translator == null) {
            return key;
        }
        return i18Translator.i18n(key, locale);
    }
    
    /**
     * 获取本地的i18n信息。
     *
     * @param key 键值。 如: name=姓名, 中的 name
     * @param args 占位符对应的元素值
     *
     * @return key对应的value值
     */
    @Nonnull
    public static String i18n(@Nonnull String key, Object... args) {
        if (i18Translator == null) {
            return key;
        }
        return i18Translator.i18n(key, args);
    }
    
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
    public static String i18n(@Nonnull String key, Locale locale, Object... args) {
        if (i18Translator == null) {
            return key;
        }
        return i18Translator.i18n(key, locale, args);
    }
}
