package com.springbootMybatisRedisCache.util;

/*
获取springboot创建好的工厂
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    /**
     * springboot项目一旦启动就创建好工厂
     * 这个方法的意义是将创建好的工厂以参数形式传递给这个类
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;

    }

    /**
     * 提供在工厂中获取的对象   redisTemplate
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){

        return applicationContext.getBean(beanName);
    }


}
