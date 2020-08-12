package com.wisdomconstruction.wisdomConstruction.dbconf;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.IdType;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 功能测试环境SQLite数据源配置
 *
 * @Version jdk 1.8
 * @Date 2018-08-16.
 * @Author chenhongding
 */
@Configuration
public class DataSourceConfig extends com.baomidou.mybatisplus.generator.config.DataSourceConfig {

    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:../creditpay-common-test/cptest.db?date_string_format=yyyy-MM-dd HH:mm:ss");
        return dataSourceBuilder.build();
    }

    @Bean
    public GlobalConfiguration globalConfig() {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();
        globalConfiguration.setIdType(IdType.ID_WORKER.getKey());
        return globalConfiguration;
    }

}
