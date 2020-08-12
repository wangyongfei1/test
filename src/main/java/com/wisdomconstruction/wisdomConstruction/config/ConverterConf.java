package com.wisdomconstruction.wisdomConstruction.config;

import com.wisdomconstruction.wisdomConstruction.common.converts.ResultModelWrapperConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConverterConf {

    @Bean
    public ResultModelWrapperConverter getResultModelWrapperConverter() {
        return new ResultModelWrapperConverter();
    }

}
