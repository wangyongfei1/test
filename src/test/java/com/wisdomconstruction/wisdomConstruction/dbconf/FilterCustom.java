package com.wisdomconstruction.wisdomConstruction.dbconf;

import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 过滤正常环境数据源
 *
 * @Version jdk 1.8
 * @Date 2018-08-16.
 * @Author chenhongding
 */
public class FilterCustom implements TypeFilter {
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) {
        // 获取当前正在扫描类的信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        // 获取当前类路径的信息
        return classMetadata.getClassName().startsWith("com.tff.creditpay.dao.conf.DataSourceCfg");
    }
}
