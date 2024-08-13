package com.ideaaedi.zoo.commonbase.constant;

/**
 * 常量类
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public interface BaseConstant {
    
    /** jwt token的头名称 */
    String JWT_TOKEN_KEY = "Authorization";
    
    /** jwt token的前缀标识 */
    String JWT_TOKEN_PREFIX = "Bearer";
    
    /** token */
    String TOKEN = "token";
    
    /** 日志追踪id */
    String TRACE_ID = "traceId";
    
    /** 带业务信息的日志追踪id。既：traceId + 业务信息 = traceXd */
    String TRACE_XD = "traceXd";
    
    /** 系统缓存名同一前缀 */
    String CACHE_NAMES = "idea-aedi-cloud";
    
    /** 数据库租户字段名 */
    String TENANT_COLUMN_NAME = "tenant";
    
    /** 数据库租户字段名对应的 实体类字段名 */
    String TENANT_PROPERTY_NAME = "tenant";
    
    /** 当前登录用户id */
    String CURR_USER_ID = "__ACROSS_SERVICE_ZOO_CURR_USER_ID__";
    
    /** 当前登录用户id */
    String CURR_TENANT_SCOPE = "__ACROSS_SERVICE_ZOO_CURR_TENANT_SCOPE__";
    
    /** 当前登录用户所属租户 */
    String CURR_USER_TENANT = "__ACROSS_SERVICE_CURR_USER_TENANT__";
    
    /** 当前登录用户可操作数据读范围 */
    String CURR_USER_READ_DATA_SCOPE_PATHS = "__ACROSS_SERVICE_CURR_USER_readDataScopePaths__";
    
    /** 当前登录用户可操作数据写（修改/删除）范围 */
    String CURR_USER_UPDATE_DATA_SCOPE_PATHS = "__ACROSS_SERVICE_CURR_USER_updateDataScopePaths__";
    
    /**
     * 当前请求是否来自于其它微服务调用
     */
    String IF_CROSS_MICRO_SERVICE_REQUEST = "__IF_CROSS_MICRO_SERVICE_REQUEST__";
    
    /**
     * 当{@link BaseConstant#IF_CROSS_MICRO_SERVICE_REQUEST}为true时， 携带的内部数据
     */
    String CROSS_MICRO_SERVICE_TRANS_DATA = "__CROSS_MICRO_SERVICE_TRANS_DATA__";
    
    /** 最新的审计日志id */
    String LATEST_AUDIT_LOG_ID = "__LATEST_AUDIT_LOG_ID__";
    
    /**
     * 携带的异常信息标识
     */
    String OCCUR_JDX_EXCEPTION = "__OCCUR_JDX_EXCEPTION__";
    
    /** 请求是否进入了网关的回调（进入了，则说明网关处发生了异常） */
    String INTO_GATEWAY_FALLBACK = "__ServerWebExchange.attributes.INTO_GATEWAY_FALLBACK__";
    
    /**
     * 忽略分页，直接全查
     * <p>
     * mybatis-plus里，当pageSize的值小于0时，会忽略分页，直接全查
     */
    Integer IGNORE_PAGING = -1;
    
    /**
     * oauth2-server内部数据的id上限值（含）
     * <br/>
     * 在删除数据时，要删除的数据的id小于等于此值的，应不被允许删除
     */
    long SYS_INTERNAL_DATA_ID_EQ = 100L;
    
    /**
     * 系统内置的根部门(集团)id
     */
    long GROUP_DEPT_ID = 1L;
    
    /**
     * 空字符串
     */
    String EMPTY = "";
    
    /**
     * mysql防转义字符
     */
    String MYSQL_ANTI_ESCAPE = "`";
    
    /**
     * code
     */
    String CODE = "code";
    
    /**
     * msg
     */
    String MSG = "msg";
    
    /**
     * error
     */
    String ERROR = "error";
    
    /**
     * userId
     */
    String USER_ID = "userId";
    
    /**
     * access_token
     */
    String ACCESS_TOKEN = "access_token";
    
    /**
     * refresh_token
     */
    String REFRESH_TOKEN = "refresh_token";
    
    /**
     * userType
     */
    String USER_TYPE = "userType";
    
    /**
     * tenant
     */
    String TENANT = "tenant";
    
    /**
     * username
     */
    String USERNAME = "username";
    
    /**
     * password
     */
    String PASSWORD = "password";
    
    /**
     * client_id
     */
    String CLIENT_ID = "client_id";
    
    /**
     * client_secret
     */
    String CLIENT_SECRET = "client_secret";
    
    /**
     * grant_type
     */
    String GRANT_TYPE = "grant_type";
    
    /**
     * true
     */
    String TRUE = "true";
    
    /**
     * false
     */
    String FALSE = "false";
    
    /**
     * 网关微服务路由前缀
     */
    String GATEWAY_ROUTE_PREFIX = "ReactiveCompositeDiscoveryClient_";
    
    /**
     * 资源服务器id
     * <br />
     * 注：将所有微服务的资源ID都设置为SYSTEM_INNER_RESOURCE_ID即可
     */
    String SYSTEM_INNER_RESOURCE_ID = "SYSTEM_INNER_RESOURCE_ID";
    
    /**
     * 客户端id
     */
    String SYSTEM_USER_CLIENT_ID = "SYSTEM_USER_CLIENT_ID";
    
    /**
     * 客户端密钥
     */
    String SYSTEM_USER_CLIENT_SECRET = "Rl9!t@zRKQhQDHdnPahi~d+WgAx@HrZ+";
    
    /**
     * 授权方式(除了implicit和authorization_code，其余都支持)
     */
    String[] SYSTEM_USER_AUTHORIZED_GRANT_TYPES = {"client_credentials", "password", "refresh_token"};
    
    /**
     * dev环境
     */
    String ENV_DEV = "dev";
    
    /**
     * RedisTemplate的spring-bean name
     */
    String REDIS_TEMPLATE_NAME = "redisTemplate";
    
    /**
     * 默认密码
     */
    String SYS_DEFAULT_PWD = "123456";
    
    /**
     * 禁止账密登录的账号名 前缀
     */
    String BAN_ACCOUNT_PWD_LOGIN_ACCOUNT_NO_PREFIX = "__BAN_ACCOUNT_PWD_LOGIN__";
    
    /**
     * 访客名称前缀
     */
    String VISITOR_NAME_PREFIX = "访客_";
    
    /**
     * 默认的访客所属部门id
     */
    int DEFAULT_VISITOR_DEPT_ID = 2;
    
    /**
     * 默认的访客角色id
     */
    long DEFAULT_VISITOR_ROLE_ID = 2;
    
    /**
     * {@link BaseConstant#DEFAULT_VISITOR_DEPT_ID}对应的租户值
     */
    String DEFAULT_VISITOR_DEPT_TENANT = "1,2,";
}
