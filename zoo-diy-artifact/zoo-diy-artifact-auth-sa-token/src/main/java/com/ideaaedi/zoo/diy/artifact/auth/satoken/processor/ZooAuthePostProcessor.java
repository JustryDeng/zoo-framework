package com.ideaaedi.zoo.diy.artifact.auth.satoken.processor;

/**
 * 认证后处理器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface ZooAuthePostProcessor {
    
    /**
     * 认证后处理器（注，此时用户具备登录态）
     *
     * @param loginType 账号类别
     * @param loginId 账号id
     * @param tokenValue 本次登录产生的 token 值
     */
    void doAfterAuthe(String loginType, Object loginId, String tokenValue);
}
