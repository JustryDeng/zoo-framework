package com.ideaaedi.zoo.commonbase.zoo_component.auth;

import javax.annotation.Nullable;
import java.util.Collection;

/*
 * 白名单
 */
public interface Whitelist<T> {
    
    /*
     * 白名单
     */
    @Nullable
    Collection<T> whitelist();
}
