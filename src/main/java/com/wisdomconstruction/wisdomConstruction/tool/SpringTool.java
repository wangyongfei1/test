package com.wisdomconstruction.wisdomConstruction.tool;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author kongke
 * @DATE 2019/12/16
 */
@Component
public class SpringTool implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String beanName,Class<T> clazz){
        return applicationContext.getBean(beanName,clazz);
    }

    public static Object getBean(String beanName){
        return  applicationContext.getBean(beanName);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> targetClass) {
        return applicationContext.getBeansOfType(targetClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringTool.applicationContext = applicationContext;
    }
}
