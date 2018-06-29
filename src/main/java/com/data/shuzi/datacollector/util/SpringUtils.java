package com.data.shuzi.datacollector.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author zizuo.zdh
 * @ClassName SpringUtils
 * @Description TODO
 * @Date 2018/6/25 14:44
 * @Version 1.0
 **/
@Component
public class SpringUtils implements ApplicationContextAware {
    private static  ApplicationContext applicationContext=null;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext=applicationContext;
    }
    /**
    *@author zizuo.zdh
    *@Description TODO
    *@Date 2018/6/25 14:47
    *@Param [beanName]
    *@return java.lang.Object
    *
    **/
    public static Object getBeans(String beanName){
        return applicationContext.getBean(beanName);
    }
}
