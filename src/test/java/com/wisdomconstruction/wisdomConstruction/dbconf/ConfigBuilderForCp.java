package com.wisdomconstruction.wisdomConstruction.dbconf;

import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 配置汇总 为creditpay提供跨工程输出文件支持
 *
 * @Author chenhongding
 * @since 2018-08-22.
 */
public class ConfigBuilderForCp extends ConfigBuilder {

    /**
     * <p>
     * 在构造器中处理配置
     * </p>
     *
     * @param packageConfig    包配置
     * @param dataSourceConfig 数据源配置
     * @param strategyConfig   表配置
     * @param template         模板配置
     * @param globalConfig     全局配置
     */
    public ConfigBuilderForCp(PackageConfig packageConfig, DataSourceConfig dataSourceConfig,
            StrategyConfig strategyConfig, TemplateConfig template, GlobalConfig globalConfig) {
        super(packageConfig, dataSourceConfig, strategyConfig, template, globalConfig);
    }

    public ConfigBuilderForCp(PackageConfig packageConfig, String modelPath, String mapperPath, String xmlPath,
            String servicePath, DataSourceConfig dataSourceConfig, StrategyConfig strategyConfig,
            TemplateConfig template, GlobalConfig globalConfig) {
        this(packageConfig, dataSourceConfig, strategyConfig, template, globalConfig);

        this.getTableInfoList()
                .forEach(tableInfo -> tableInfo.setFields(tableInfo
                                                                  .getFields()
                                                                  .stream()
                                                                  .map(field -> new TableFieldExt(strategyConfig, field))
                                                                  .collect(Collectors.toList())));

        moreHandlerPackage(template, globalConfig.getOutputDir(), packageConfig, modelPath, mapperPath, xmlPath,
                           servicePath);
    }

    private void moreHandlerPackage(TemplateConfig template, String outputDir, PackageConfig config, String modelPath,
            String mapperPath, String xmlPath, String servicePath) {
        Map<String, String> packageInfo = this.getPackageInfo();
        Map<String, String> pathInfo = this.getPathInfo();

        pathInfo.put(ConstVal.ENTITY_PATH,
                     joinPath(outputDir + File.separator + modelPath, packageInfo.get(ConstVal.ENTITY)));
        pathInfo.put(ConstVal.MAPPER_PATH,
                     joinPath(outputDir + File.separator + mapperPath, packageInfo.get(ConstVal.MAPPER)));
        pathInfo.put(ConstVal.XML_PATH, joinPath(outputDir + File.separator + xmlPath, packageInfo.get(ConstVal.XML)));
        pathInfo.put(ConstVal.SERVICE_PATH,
                     joinPath(outputDir + File.separator + servicePath, packageInfo.get(ConstVal.SERVICE)));
        pathInfo.put(ConstVal.SERVICEIMPL_PATH,
                     joinPath(outputDir + File.separator + servicePath, packageInfo.get(ConstVal.SERVICEIMPL)));

        StrategyConfig.DB_COLUMN_UNDERLINE = false;
        getTableInfoList().forEach(
                tableInfo -> tableInfo.setEntityName(this.getStrategyConfig(), tableInfo.getEntityName() + "DO"));
        StrategyConfig.DB_COLUMN_UNDERLINE = true;
    }

    /**
     * <p>
     * 连接路径字符串
     * </p>
     *
     * @param parentDir   路径常量字符串
     * @param packageName 包名
     * @return 连接后的路径
     */
    private String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\." , "\\" + File.separator);
        return parentDir + packageName;
    }
}
