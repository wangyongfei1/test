package com.wisdomconstruction.wisdomConstruction.dbconf;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MP生成器
 *
 * @Author chenhongding
 * @since 2018-08-22.
 */
public class MPGenerator {

    private static final String SYSTEM_USER_DIR = "user.dir";

    public static void main(String[] args) {
        MPGenerator mpGenerator = new MPGenerator();
        mpGenerator.generateCode();
    }

    public void generateCode() {
        String packageName = "com.wisdomconstruction.wisdomConstruction";
        boolean serviceNameStartWithI = false;
        generateByTables(serviceNameStartWithI, packageName, "equipment", "construction_equipment");
    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName,
            String modelName, String... tableNames) {

        String basePath = System.getProperty(SYSTEM_USER_DIR);

        GlobalConfig config = new GlobalConfig();
        String dbUrl = "jdbc:mysql://115.29.240.96:3306/labor";
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                        .setUrl(dbUrl)
                        .setUsername("root")
                        .setPassword("zqjs888")
                        .setDriverName("com.mysql.jdbc.Driver");
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setCapitalMode(true)
                      .setEntityLombokModel(true)
                      .setDbColumnUnderline(true)
                      .setNaming(NamingStrategy.underline_to_camel)
                      .setInclude(tableNames)
                      .entityTableFieldAnnotationEnable(true)
                      .setSuperEntityClass("com.wisdomconstruction.wisdomConstruction.dao.base.BaseBizModel")
                      .setSuperMapperClass("com.wisdomconstruction.wisdomConstruction.dao.base.mapper.BaseDAOMapper")
                      .setSuperServiceClass("com.wisdomconstruction.wisdomConstruction.dao.base.BaseDAO")
                      .setSuperServiceImplClass("com.wisdomconstruction.wisdomConstruction.dao.base.impl.BaseDAOImpl")
                      .setSuperEntityColumns("id", "is_delete", "gmt_create", "gmt_modified", "creator", "modifier");

        config.setActiveRecord(false)
              .setAuthor("huangc")
              .setBaseColumnList(true)
              .setBaseResultMap(true)
              .setOutputDir(basePath)
              .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sDAO")
                  .setServiceImplName("%sDAOImpl");
        }

        PackageConfig pg = new PackageConfig().setParent(null)
                                              .setEntity(packageName + ".dao.base.domain." + modelName)
                                              .setMapper(packageName + ".dao.base.mapper." + modelName)
                                              .setXml("mapper." + modelName)
                                              .setService(packageName + ".dao.base.service." + modelName)
                                              .setServiceImpl(packageName + ".dao.base.service." + modelName + ".impl");

        TemplateConfig tc = new TemplateConfig().setController(null)
//                                                .setService(null)
//                                                .setMapper(null)
                                                // 自定义实体模板
                                                .setEntity("/entity.java")
                                                //.setServiceImpl(null)
                                                .setXml(null);

        ConfigBuilder cb = new ConfigBuilderForCp(pg, "src/main/java/",
                                                  "src/main/java/",
                                                  "src/main/resources",
                                                  "src/main/java/",
                                                  dataSourceConfig, strategyConfig, tc, config);

        InjectionConfig ic = new InjectionConfigImpl();
        FileOutConfigImpl fc = new FileOutConfigImpl();
        fc.setTemplatePath("/entryPropertiesEnum.java.vm");
        String packagePath = "com.wisdomconstruction.wisdomConstruction.dao.base.enums." + modelName;
        fc.setOutputFilePath(basePath + "/src/main/java/" + packagePath.replaceAll("\\.", "/"));
        List<FileOutConfig> fcList = new ArrayList<>();
        fcList.add(fc);
        ic.setFileOutConfigList(fcList);
        Map<String, Object> pkgMap = new HashMap<>();
        pkgMap.put("packageName", packagePath);
        ic.setMap(pkgMap);

        cb.setInjectionConfig(ic);

        new AutoGenerator().setGlobalConfig(config)
                           .setDataSource(dataSourceConfig)
                           .setStrategy(strategyConfig)
                           .setPackageInfo(pg)
                           .setConfig(cb)
                           .execute();
    }
}
