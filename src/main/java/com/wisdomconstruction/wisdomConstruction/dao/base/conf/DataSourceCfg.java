package com.wisdomconstruction.wisdomConstruction.dao.base.conf;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Druid连接池配置
 */
@Configuration
@ConfigurationProperties(prefix = "masterdb.dbcfg")
public class DataSourceCfg {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceCfg.class);

    /** 数据源参数 */
    private Map<String, String> druiddbCfg = new HashMap<>();

    /**
     * 得到数据源
     * 
     * @throws Exception
     * @return DataSource
     */
    @Primary
    @Bean
    public DataSource generateDataSource() throws Exception {
        logger.info("====================配置操作mysql数据库=====================");
        //配合安全整改，配置文件不准出现password
        druiddbCfg.put(DruidDataSourceFactory.PROP_PASSWORD, druiddbCfg.get("safeKey"));
        DataSource dataSource = DruidDataSourceFactory.createDataSource(druiddbCfg);
        logger.info("DataSource创建成功");
        return dataSource;
    }

    /**
     * @return Map
     */
    public Map<String, String> getDruiddbCfg() {
        return druiddbCfg;
    }

    /**
     * @param druiddbCfg 数据源参数
     */
    public void setDruiddbCfg(Map<String, String> druiddbCfg) {
        this.druiddbCfg = druiddbCfg;
    }

}
