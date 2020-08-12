package com.wisdomconstruction.wisdomConstruction.dbconf;

import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.spring.boot.starter.MybatisPlusProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * SqlSession配置
 *
 * @Version jdk 1.8
 * @Date 2018-08-16.
 * @Author chenhongding
 */
@Configuration
public class SqlSessionConfig {

    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(DataSource dataSource,
            PaginationInterceptor paginationInterceptor, GlobalConfiguration globalConfig) {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        MybatisPlusProperties properties = new MybatisPlusProperties();
        properties.setMapperLocations(new String[] { "classpath*:/mapper/*/*.xml" });
        sqlSessionFactoryBean.setMapperLocations(properties.resolveMapperLocations());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.wisdomconstruction.wisdomConstruction.dao.base.domain.*");
        sqlSessionFactoryBean.setPlugins(new Interceptor[] { paginationInterceptor });
        sqlSessionFactoryBean.setGlobalConfig(globalConfig);
        return sqlSessionFactoryBean;
    }

}
