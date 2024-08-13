package com.ideaaedi.zoo.commonbase.zoo_component.auth;

/*
 * 认证url白名单
 */
public interface AuthUrlWhitelist extends Whitelist<String> {
    
    /*
     * 是否对那些认证之后的请求，再进行鉴权
     *
     * @return 是否对那些认证之后的请求，再进行鉴权
     */
    default boolean validUrlPermit() {
        return true;
    }
}
