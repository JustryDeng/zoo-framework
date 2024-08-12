package com.ideaaedi.zoo.diy.feature.msg.sms.impl.sms4j.core;

import com.ideaaedi.zoo.diy.feature.msg.api.sms.SmsFaceUtil;
import org.dromara.sms4j.api.SmsBlend;
import org.dromara.sms4j.api.universal.SupplierConfig;
import org.dromara.sms4j.provider.factory.BaseProviderFactory;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
public class BaseProviderFactoryWrapper implements BaseProviderFactory<SmsBlendWrapper, SupplierConfig> {
    
    private final BaseProviderFactory<SmsBlend, SupplierConfig> proxy;
    
    private BaseProviderFactoryWrapper(BaseProviderFactory<SmsBlend, SupplierConfig> proxy) {
        this.proxy = proxy;
    }
    
    @Override
    public SmsBlendWrapper createSms(SupplierConfig supplierConfig) {
        SmsBlendWrapper smsBlendWrapper = new SmsBlendWrapper(proxy.createSms(supplierConfig));
        SmsFaceUtil.getSenderManager().add(smsBlendWrapper);
        return smsBlendWrapper;
    }
    
    @Override
    public Class<SupplierConfig> getConfigClass() {
        return proxy.getConfigClass();
    }
    
    @Override
    public String getSupplier() {
        return proxy.getSupplier();
    }
    
    public static  <T extends BaseProviderFactory<?, ?>> BaseProviderFactoryWrapper wrap(T proxy) {
        //noinspection unchecked
        return new BaseProviderFactoryWrapper((BaseProviderFactory<SmsBlend, SupplierConfig>)proxy);
    }
}
