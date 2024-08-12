package com.ideaaedi.zoo.diy.artifact.generator.face;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.ideaaedi.commonds.env.Env;
import com.ideaaedi.commonds.env.RequiredEnv;
import com.ideaaedi.commonds.env.Unit;
import com.ideaaedi.zoo.commonbase.zoo_face.Face;
import com.ideaaedi.zoo.diy.artifact.generator.code.Generator;
import com.ideaaedi.zoo.diy.artifact.generator.properties.ZooGeneratorProperties;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * 数据库文档生成器
 *
 * @author <font size = "20" color = "#3CAA3C"><a href="https://gitee.com/JustryDeng">JustryDeng</a></font> <img
 * src="https://gitee.com/JustryDeng/shared-files/raw/master/JustryDeng/avatar.jpg" />
 * @since 1.0.0
 */
@Slf4j
public class GeneratorFace4DatabaseDoc implements Generator, Face {
    
    static ZooGeneratorProperties.DatabaseDoc databaseDoc;
    
    @Override
    public String generateDesc() {
        return "this is a generator for database table";
    }
    
    /**
     * 生成数据库文档
     *
     * @see #generate(ZooGeneratorProperties.DatabaseDoc, Consumer)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static void generate() {
        generate(GeneratorFace4DatabaseDoc.databaseDoc.clone(), null);
    }
    
    /**
     * 生成数据库文档
     *
     * @param targetFileOutputDir 指定输出位置
     *
     * @see #generate(ZooGeneratorProperties.DatabaseDoc, Consumer)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static void generate(String targetFileOutputDir) {
        ZooGeneratorProperties.DatabaseDoc clone = GeneratorFace4DatabaseDoc.databaseDoc.clone();
        Objects.requireNonNull(clone.getDbJdbcUrl(), "miss config 'zoo.generator.database-doc.db-jdbc-url'");
        Objects.requireNonNull(clone.getDbUsername(), "miss config 'zoo.generator.database-doc.db-password'");
        Objects.requireNonNull(clone.getDbPassword(), "miss config 'zoo.generator.database-doc.db-username'");
        Objects.requireNonNull(clone.getDbDriverClassName(), "miss config 'zoo.generator.database-doc.db-driver-class-nam'");
        generate(clone, (databaseDoc) -> {
            databaseDoc.getEngineConfig().setFileOutputDir(targetFileOutputDir);
        });
    }
    
    /**
     * 生成数据库文档
     *
     * @see #generate(ZooGeneratorProperties.DatabaseDoc, Consumer)
     */
    @RequiredEnv(@Unit(Env.SPRING_BOOT))
    public static void generate(@Nonnull Consumer<ZooGeneratorProperties.DatabaseDoc> consumer) {
        ZooGeneratorProperties.DatabaseDoc clone = GeneratorFace4DatabaseDoc.databaseDoc.clone();
        Objects.requireNonNull(clone.getDbJdbcUrl(), "miss config 'zoo.generator.database-doc.db-jdbc-url'");
        Objects.requireNonNull(clone.getDbUsername(), "miss config 'zoo.generator.database-doc.db-password'");
        Objects.requireNonNull(clone.getDbPassword(), "miss config 'zoo.generator.database-doc.db-username'");
        Objects.requireNonNull(clone.getDbDriverClassName(), "miss config 'zoo.generator.database-doc.db-driver-class-nam'");
        generate(clone, consumer);
    }
    
    /**
     * 生成数据库文档
     *
     * @see #generate(ZooGeneratorProperties.DatabaseDoc, Consumer)
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nonnull ZooGeneratorProperties.DatabaseDoc databaseDoc) {
        generate(databaseDoc, null);
    }
    
    /**
     * 生成数据库文档
     *
     * @param databaseDoc 相关配置
     * @param consumer 对databaseDoc参数进行修改
     */
    @RequiredEnv(@Unit(Env.NONE))
    public static void generate(@Nonnull ZooGeneratorProperties.DatabaseDoc databaseDoc,
                                @Nullable Consumer<ZooGeneratorProperties.DatabaseDoc> consumer) {
        Objects.requireNonNull(databaseDoc, "databaseDoc cannot be null!");
        if (consumer != null) {
            consumer.accept(databaseDoc);
        }
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(Objects.requireNonNull(databaseDoc.getDbJdbcUrl(), "dbJdbcUrl cannot be null."));
        hikariConfig.setUsername(Objects.requireNonNull(databaseDoc.getDbUsername(), "dbUsername cannot be null."));
        hikariConfig.setPassword(Objects.requireNonNull(databaseDoc.getDbPassword(), "dbPassword cannot be null."));
        hikariConfig.setDriverClassName(Objects.requireNonNull(databaseDoc.getDbDriverClassName(), "dbDriverClassName cannot be null."));
        // 设置可以获取tables remarks信息
        hikariConfig.addDataSourceProperty("useInformationSchema", "true");
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(5);
        DataSource dataSource = new HikariDataSource(hikariConfig);
        
        ProcessConfig produceConfig = databaseDoc.getProduceConfig();
        EngineConfig engineConfig = databaseDoc.getEngineConfig();
        
        // Screw 配置入口
        Configuration config = Configuration.builder()
                .organization(databaseDoc.getOrganization())
                .organizationUrl(databaseDoc.getOrganizationUrl())
                .title(databaseDoc.getTitle())
                .version(databaseDoc.getVersion())
                .description(databaseDoc.getDescription())
                .dataSource(dataSource)
                .engineConfig(engineConfig)
                .produceConfig(produceConfig)
                .build();
        
        // 执行生成文档
        new DocumentationExecute(config).execute();
        String fileOutputDir = engineConfig.getFileOutputDir();
        log.info("generate database doc to -> '{}'.", fileOutputDir);
    }
}
