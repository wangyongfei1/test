package com.wisdomconstruction.wisdomConstruction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

@Configuration
public class ParameterNameDiscovererConf {

    @Bean
    public ParameterNameDiscoverer getParameterNameDiscoverer(){
        return new DefaultParameterNameDiscoverer();
    }

}
