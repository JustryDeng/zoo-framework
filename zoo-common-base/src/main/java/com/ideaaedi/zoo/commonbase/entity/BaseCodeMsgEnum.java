package com.ideaaedi.zoo.commonbase.entity;

import lombok.Getter;

/**
 * 基础 code message
 * <p>
 * 错误码:
 *     <ul>
 *         <li>报错  1xxxxx</li>
 *     </ul>
 * </p>
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Getter
public enum BaseCodeMsgEnum implements CodeMsgProvider {
    
    /** item */
    SUCCESS("000000", "成功"),
    FAILURE("100000", "系统错误"),
    GATEWAY_ROUTE_ERROR("100001", "网关分发请求失败.%s"),
    USER_NON_EXIST_ERROR("100002", "用户不存在"),
    USER_PASSWORD_ERROR("100003", "用户名密码错误"),
    PHONE_IS_OCCUPIED("100004", "手机号已被占用"),
    CERT_IS_OCCUPIED("100005", "证件号已被占用"),
    NO_DATA_SCOPE_READ("100006", "没有可读取的数据域"),
    NO_DATA_SCOPE_UPDATE("100007", "没有可修改（删除）的数据域"),
    DATA_NON_EXIST_OR_NON_DATA_PERMISSION("100008", "数据不存在或没有数据权限"),
    HTTP_MSG_READ_ERROR("100009", "读取数据失败，请检查数据格式"),
    DUPLICATE_REQUEST("100010", "操作太频繁，请稍后再试"),
    MISSING_SERVLET_REQUEST_PARAMETER_FAIL("100011", "缺少必要参数，请补充后重试"),
    HTTP_REQUEST_METHOD_NOT_SUPPORTED_FAIL("100012", "请使用支持的http方法"),
    ARGS_ILLEGAL("100400", "参数有误"),
    SYSTEM_INTERNAL_DATA_NON_ALLOW_UPDATE("100401", "系统内置数据，不可修改或删除"),
    HTTP_STATUS_CODE_ERROR("100403", "HTTP状态码错误.%s"),
    SPRING_MVC_404_ERROR("100404", "url匹配失败, 404"),
    RPC_ERROR("100405", "rpc调用失败"),
    SERVICE_UNAVAILABLE_ERROR("100406", "服务暂不可用，请稍后再试"),
    NON_EXIST_MATCHED_SERVICE_ERROR("100407", "不存在对应的微服务%s"),
    CONVENIENT_TIP("100408", "%s"),
    FILE_SIZE_CANNOT_BE_ZERO("100409", "文件大小不能为0"),
    FILE_NON_EXIST("100410", "文件不存在"),
    LOAD_FILE_FAIL("100411", "加载文件失败"),
    ACCOUNT_NO_IS_OCCUPIED("100412", "账号%s已被使用"),
    ACCOUNT_NO_CANNOT_CONTAIN_SPECIAL_CHARACTERS("100413", "账号不能含有特殊字符"),
    SHARDING_SQL_MISUSE("100414", "多分表关联查询SQL编写有误，需要明确限定每个分表的分片列值"),
    QUERY_TIME_RANGE_EXCEEDED_SHARDING_CONFIG_TIME_RANGE_LIMIT("100415", "查询时间范围超出允许查询的最大范围"),
    SHARDING_AUTO_CREATE_TABLE_FAIL("100416", "初始化对应数据分片表失败"),
    SERVICE_UNREACHABLE_ERROR("100417", "服务不可达，若请求地址无误请稍后再试"),
    FIELD_PERMISSION_ERROR("100418", "参数字段权限不足异常，您不能赋值%s"),
    
    /** [100420 - 100500) 用户、部门、角色、菜单、资源等 */
    NON_EXIST_PARENT_MENU("100420", "父菜单不存在"),
    EXIST_SUB_DEPT("100421", "存在子部门，不可删除"),
    PARENT_DEPT_CANNOT_BE_SUB_DEPT("100422", "不能将自己的子部门设置为自己的父部们"),
    CODE_BEEN_OCCUPIED("100423", "编码%s已被使用"),
    OBTAIN_USER_TENANT_SCOPE_FAIL("100424", "获取用户数据域信息失败"),
    RELATED_DATA_EXCEED_CURR_USER_DATA_SCOPE("100425", "数据超出当前用户的数据权限范围"),
    ROLE_IS_USING("100426", "该角色被使用中，不可删除"),
    PARENT_MENU_CANNOT_BE_SUB_DEPT("100427", "不能将自己的子菜单 设置为自己的父菜单"),
    EXIST_SUB_MENU("100428", "存在子菜单，不可删除"),
    NEED_POINT_USER_TYPE_TO_LOGIN("100429", "用户存在多个用户类型，需要指定要登录的用户类型"),
    DO_NOT_DELETE_AKSK_USER_HERE("100430", "不能在这里删除合作方信息"),
    OBTAIN_USER_ID_FAIL("100431", "获取当前用户ID失败"),
    OBTAIN_USER_TENANT_INSERT_SCOPE_FAIL("100432", "获取当前用户数据写入域失败"),
    
    /** [100500 - 100600) 认证&鉴权 */
    INVALID_TOKEN("100500", "token无效或过期"),
    AUTH_FAIL("100501", "认证失败"),
    ACCESS_DENY("100502", "权限不足"),
    USER_ACCOUNT_NO_OR_PASSWORD_ERROR("100503", "用户账号或密码错误"),
    REFRESH_TOKEN_ERROR("100504", "refreshToken无效或过期"),
    OBTAIN_CURR_LOGIN_USER_INFO_FAIL("100505", "获取登陆者用户信息失败"),
    LOGIN_FAIL("100506", "oauth2登录失败"),
    CLIENT_CREDENTIAL_LOGIN_ERROR("100507", "client_credentials模式登陆异常"),
    MISS_JWT_TOKEN("100508", "jwt token缺失"),
    CHECK_TOKEN_FAIL("100509", "token校验异常"),
    TOKEN_INVALID("100510", "token无效"),
    AUTH_OCCUR_EXCEPTION("100511", "认证鉴权异常"),
    FILTER_AUTH_OCCUR_EXCEPTION("100512", "拦截器认证鉴权异常"),
    
    /** [100600 - 100700) 第三方对接 */
    WECHAT_NON_SUPPORT_TOKEN_BY_WECHAT("100600", "暂不支持微信小程序登录"),
    WECHAT_OBTAIN_OPENID_FAIL("100601", "获取微信openid失败，请稍后重试"),
    WECHAT_OBTAIN_PHONE_FAIL("100602", "获取微信手机号失败，请稍后重试"),
    WECHAT_PHONE_AUTH_CODE_IS_BLANK("100603", "手机查询授权码不应为空"),
    
    
    /** [100700 - 100800) 企业微信相关 */
    DECRYPT_QY_WECHAT_DATA_FAIL("100700", "解密企业微信数据失败"),
    QY_WECHAT_SILENT_LOGIN_FAIL("100701", "企业微信静默登录失败，请尝试手机号登录"),
    NON_SUPPORT_TOKEN_BY_QY_WECHAT("100702", "暂不支持企业微信登录"),
    ;
    
    private final String code;
    
    private final String message;
    
    BaseCodeMsgEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    @Override
    public String code() {
        return this.code;
    }
    
    @Override
    public String message() {
        return this.message;
    }
    
    @Override
    public String toString() {
        return "{\"code\":\"" + code + "\", \"message\":\"" + message + "\"}";
    }
}
