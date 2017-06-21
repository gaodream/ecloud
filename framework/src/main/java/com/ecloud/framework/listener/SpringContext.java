package com.ecloud.framework.listener;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringContext implements ApplicationContextAware {
	
    private static ApplicationContext context; 

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContext.context = context;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static <T> T getBean(Class<T> clazz){
    	return context.getBean(clazz);
    }
}
