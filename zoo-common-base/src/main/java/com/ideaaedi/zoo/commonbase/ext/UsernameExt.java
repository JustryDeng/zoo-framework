package com.ideaaedi.zoo.commonbase.ext;

import com.alibaba.fastjson2.JSON;
import com.ideaaedi.zoo.commonbase.entity.sys.po.SysUserPO;
import com.ideaaedi.zoo.commonbase.enums.sys.LoginSceneEnum;
import com.ideaaedi.zoo.commonbase.enums.sys.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Objects;

/**
 * 万物皆可username
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsernameExt {
    
    /** 当type为{@link UsernameType#APPLET_DIRECT_PHONE}或{@link UsernameType#QY_APPLET_DIRECT_PHONE}， 进行oauth2获取token时，密码传这个 */
    public static final String APPLET_DIRECT_PHONE_PASSWORD = "";
    
    /** 用户名类型 */
    private UsernameType type;
    
    /**
     * 用户标识
     * <ul>
     *     <li>当type为{@link UsernameType#ACCOUNT_NO_PASSWORD}, 此字段表示{@link SysUserPO#getAccountNo()}</li>
     *     <li>当type为{@link UsernameType#APPLET_DIRECT_PHONE}, 此字段表示{@link SysUserPO#getPhone()} ()}</li>
     *     <li>当type为{@link UsernameType#QY_APPLET_DIRECT_PHONE}, 此字段表示{@link SysUserPO#getPhone()} ()}</li>
     * </ul>
     */
    private String username;
    
    /**
     * 要登录的用户类型
     * <p>
     * accountNo是全局唯一的，当使用账密登录时，此参数可以不指定. 使用其它方式登陆时，使不使用此字段看自己的业务
     * <p>
     * 相同租户下，不同的用户类型，可以拥有相同的手机号、证件类型证件号等信息. 因此，如果不是通过accountNo账密登录的话，还需要一个用户类型进行匹配，否则可能匹配出多条记录进而报错
     */
    private UserTypeEnum userType;
    
    /**
     * 登录场景
     * <p>
     * 当type为{@link UsernameType#APPLET_DIRECT_PHONE}时，需要<br/>
     * 当type为{@link UsernameType#QY_APPLET_DIRECT_PHONE}时，需要
     */
    private LoginSceneEnum loginSceneEnum;
    
    /**
     * 登录时，查询数据的租户域
     * <p>
     * 当type为{@link UsernameType#APPLET_DIRECT_PHONE}时，需要<br/>
     * 当type为{@link UsernameType#QY_APPLET_DIRECT_PHONE}时，需要
     */
    private String tenant;
    
    /**
     * 将UsernameExt转换为username对象
     */
    public String buildUsername() {
        valid();
        return JSON.toJSONString(this);
    }
    
    /**
     * 校验
     */
    public void valid() {
        Objects.requireNonNull(this.type, "type cannot be null.");
        switch (type) {
            case ACCOUNT_NO_PASSWORD:
                Assert.isTrue(StringUtils.isNotBlank(this.username), "accountNo cannot be blank.");
                break;
            case APPLET_DIRECT_PHONE:
            case QY_APPLET_DIRECT_PHONE:
                Assert.isTrue(StringUtils.isNotBlank(this.username), "phone cannot be blank.");
                Assert.isTrue(StringUtils.isNotBlank(this.tenant), "tenant cannot be blank.");
                Objects.requireNonNull(loginSceneEnum, "loginSceneEnum cannot be blank.");
                break;
            default:
                throw new UnsupportedOperationException("un-support type '" + type + "'");
        }
    }
    
    /**
     * 类型
     */
    public enum UsernameType {
        
        /** 账号密码 */
        ACCOUNT_NO_PASSWORD,
        
        /** 微信小程序。 手机号 作为用户名，密码使用{@link UsernameExt#APPLET_DIRECT_PHONE_PASSWORD} */
        APPLET_DIRECT_PHONE,
        
        /** 企业微信小程序。 手机号 作为用户名，密码使用{@link UsernameExt#APPLET_DIRECT_PHONE_PASSWORD} */
        QY_APPLET_DIRECT_PHONE,
    }
}
