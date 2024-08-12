package com.ideaaedi.zoo.diy.artifact.generator.code.process;

import com.ideaaedi.zoo.diy.artifact.generator.code.CodeGenerator;
import com.ideaaedi.zoo.diy.artifact.generator.code.config.ProcessConfig;
import com.ideaaedi.zoo.diy.artifact.generator.code.entity.PojoInfo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

/**
 * (non-javadoc)
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Data
@Slf4j
public abstract class JavaCodeGenerator implements CodeGenerator<PojoInfo[], Void> {
    
    private static final ThreadLocal<ProcessConfig> defaultProcessConfig = ThreadLocal.withInitial(ProcessConfig::new);
    
    protected final String ENCODING = StandardCharsets.UTF_8.name();
    
    @Nullable
    @Override
    public Void generate(@Nonnull PojoInfo... pojoInfos) {
        try {
            List<String> outputFileList = doGenerate(pojoInfos);
            if (defaultProcessConfig.get().isPrintOutputFileInfo()) {
                int size = outputFileList.size();
                StringBuilder sb = new StringBuilder(256);
                for (int i = 1; i <= size; i++) {
                    sb.append(String.format("%02d. ", i)).append(outputFileList.get(i - 1)).append(System.lineSeparator());
                }
                log.info("outputFileList -> {}{}", System.lineSeparator(), sb);
            }
        } finally {
            defaultProcessConfig.remove();
        }
        return null;
    }
    
    /**
     * 执行生成
     *
     * @param pojoInfos 实体信息
     *
     * @return 输出的文件信息（是文件路径还是文件内容，取决于实现）
     */
    protected abstract List<String> doGenerate(@Nonnull PojoInfo... pojoInfos);
    
    /**
     * 处理过程相关配置
     */
    public void configProcessConfig(Consumer<ProcessConfig> configConsumer) {
        if (configConsumer == null) {
            return;
        }
        configConsumer.accept(defaultProcessConfig.get());
    }
    
    @Override
    public String generateDesc() {
        return "this is a generator for java code";
    }
    
}
