package com.wisdomconstruction.wisdomConstruction.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("springBeanFactoryTool")
public class SpringBeanFactoryTool implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(@Autowired ApplicationContext applicationContext) {
        synchronized (SpringBeanFactoryTool.class) {
            SpringBeanFactoryTool.applicationContext = applicationContext;
        }
    }

    /**
     * 根据bean name 获取bean
     * @param name
     * @return Object
     */
    public static Object getBeanByName(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据类名获取到bean
     * @param clazz
     * @return T
     */
    public static <T> T getBeanByType(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 判断bean是否存在
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 判断bean是否是单例模式
     * @param name
     * @return boolean
     */
    public static boolean isSingleton(String name) {
        return applicationContext.isSingleton(name);
    }

}
