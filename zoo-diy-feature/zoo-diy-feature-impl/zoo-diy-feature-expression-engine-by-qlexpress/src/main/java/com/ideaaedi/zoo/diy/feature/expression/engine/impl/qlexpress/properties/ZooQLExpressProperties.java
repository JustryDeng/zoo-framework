package com.ideaaedi.zoo.diy.feature.expression.engine.impl.qlexpress.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "zoo.expression-engine.qlexpress")
public class ZooQLExpressProperties {
    
    /**
     * 是否自动往（每次执行的）脚本上下文中追加 spring的上下文
     * <p>
     * 注：当从原上下文中获取不到变量时，再试着从spring上下文中获取变量
     * </p>
     */
    private boolean appendSpringContext = true;
    
    /**
     * 限制（每次执行的）脚本最大能申请的数组长度
     * <p>注：-1表示没有限制</p>
     */
    private int maxArrLength = -1;
    
    /**
     * 设置全局脚本超时时间
     * <p>注1：单位毫秒</p>
     * <p>注2：-1表示不限制时间</p>
     * <p>注3：全局超时时间和脚本自身设置的执行超时时间，触发任意一个都将触发超时</p>
     */
    private long globalTimeout = 60_000;
    
    /**
     * 是否需要高精度计算
     * <p>
     *  高精度计算在会计财务中非常重要，java的float、double、int、long存在很多隐式转换，做四则运算和比较的时候其实存在非常多的安全隐患。
     *  所以类似汇金的系统中，会有很多BigDecimal转换代码。而使用QLExpress，你只要关注数学公式本身
     *  订单总价 = 单价 * 数量 + 首重价格 + （ 总重量 - 首重） * 续重单价 ，然后设置这个属性即可，所有的中间运算过程都会保证不丢失精度。
     * </p>
     */
    private boolean isPrecise = true;
    
    /**
     * 是否使用逻辑短路特性
     * <p>
     *  在很多业务决策系统中，往往需要对布尔条件表达式进行分析输出，普通的java运算一般会通过逻辑短路来减少性能的消耗。
     *  例如规则公式： star > 10000 and shopType in ('tmall', 'juhuasuan') and price between (100, 900) 假设第
     *  一个条件 star>10000 不满足就停止运算。但业务系统却还是希望把后面的逻辑都能够运算一遍，并且输出中间过程，保证更快更好的做出决策。
     * </p>
     */
    private boolean isShortCircuit = true;
    
    /**
     * 是否输出所有的跟踪信息（debug级别的日志）
     * <p>
     *  注1：这个主要是是否输出脚本的编译解析过程，一般对于业务系统来说关闭之后会提高性能。
     * </p>
     * <p>
     *  注2：如果设置为true，那么还需要将对应包的log级别设置为DEBUG才会输出
     * </p>
     */
    private boolean isTrace = false;
}
