package com.ecloud.deploy.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class SpringContext implements ApplicationContextAware {
	
	private Logger LOG = LoggerFactory.getLogger(getClass());

/*	@Autowired
	private Environment env;*/

	private static ApplicationContext applicationContext; // Spring应用上下文环境

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		LOG.info("<<<<<<     SpringContext      >>>>>");
		SpringContext.applicationContext = applicationContext;
		
		
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}
	
	

}
