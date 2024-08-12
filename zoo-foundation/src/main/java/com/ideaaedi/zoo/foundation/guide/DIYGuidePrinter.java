package com.ideaaedi.zoo.foundation.guide;

import com.ideaaedi.commonds.tuple.BasicNameValue;
import com.ideaaedi.commonds.tuple.Holder;
import com.ideaaedi.zoo.commonbase.zoo_info.DIYGuide;
import com.ideaaedi.zoo.foundation.properties.ZooFoundationGuideProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * DIY 组件概览打印
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
@AutoConfiguration
@Order(Ordered.HIGHEST_PRECEDENCE)
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
public class DIYGuidePrinter implements SmartInitializingSingleton {
    
    @Autowired(required = false)
    private ZooFoundationGuideProperties zooFoundationGuideProperties;
    
    @Autowired(required = false)
    private List<DIYGuide> diyGuideList;
    
    @Override
    public void afterSingletonsInstantiated() {
        if (zooFoundationGuideProperties == null || !zooFoundationGuideProperties.isEnable()) {
            return;
        }
        Holder<Integer> holder = Holder.of(1);
        Optional.ofNullable(diyGuideList).ifPresent(diyGuideList -> diyGuideList.forEach(diyGuide -> {
            Integer serialNo = holder.getValue();
            holder.setValue(serialNo + 1);
            String serialNoStr = String.format("%02d", serialNo);
            String name = diyGuide.name();
            log.info("Integrate component -> {}.{}", serialNoStr, AnsiOutput.toString(AnsiColor.BRIGHT_GREEN, name));
            if (zooFoundationGuideProperties.isShowDetail()) {
                String instruction = diyGuide.instruction();
                String manual = diyGuide.manual();
                List<String> manifests = diyGuide.manifests();
                List<BasicNameValue> contacts = diyGuide.contacts();
                List<BasicNameValue> links = diyGuide.links();
                
                String lineSeparator = System.lineSeparator();
                StringBuilder sb = new StringBuilder(1024);
                sb.append(lineSeparator).append(String.format("------------------------ %s.%s guide "
                        + "------------------------", serialNoStr, name));
                sb.append(lineSeparator).append("[instruction]");
                sb.append(lineSeparator).append(instruction);
                if (StringUtils.isNotBlank(manual)) {
                    sb.append(lineSeparator).append(lineSeparator).append("[manual]");
                    sb.append(lineSeparator).append(manual);
                }
                if (!CollectionUtils.isEmpty(manifests)) {
                    sb.append(lineSeparator).append(lineSeparator).append("[manifests]");
                    int size = manifests.size();
                    for (int i = 1; i <= size; i++) {
                        sb.append(lineSeparator).append(String.format("%02d", i)).append(".").append(manifests.get(i - 1));
                    }
                }
                if (!CollectionUtils.isEmpty(contacts)) {
                    sb.append(lineSeparator).append(lineSeparator).append("[contacts]");
                    for (BasicNameValue contact : contacts) {
                        sb.append(lineSeparator).append(contact.getName()).append(": ").append(contact.getValue());
                    }
                }
                if (!CollectionUtils.isEmpty(links)) {
                    sb.append(lineSeparator).append(lineSeparator).append("[links]");
                    for (BasicNameValue link : links) {
                        sb.append(lineSeparator).append(link.getName()).append(": ").append(link.getValue());
                    }
                }
                sb.append(lineSeparator);
                log.info(sb.toString());
            }
        }));
    }
}
