package com.wisdomconstruction.wisdomConstruction.dbconf;

import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;

import java.io.File;

/**
 * 自定义输出文件配置
 *
 * @Author chenhongding
 * @since 2018-08-29.
 */
public class FileOutConfigImpl extends FileOutConfig {

    private String outputFilePath;

    /**
     * 输出文件
     *
     * @param tableInfo
     */
    @Override
    public String outputFile(TableInfo tableInfo) {
        return outputFilePath + File.separator + tableInfo.getEntityName() + "PropertiesEnum.java";
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }
}
