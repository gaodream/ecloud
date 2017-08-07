package com.ecloud.generator.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration //相当于Spring application.xml配置文件
//@EnableWebMvc //JSP模式下不能加该属性
@EnableTransactionManagement
@AutoConfigureAfter(Environment.class)
public class WebConfig implements EnvironmentAware {
	
	@Autowired
    private Environment env;
    
	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix(env.getProperty("spring.mvc.view.prefix"));
		resolver.setSuffix(env.getProperty("spring.mvc.view.suffix"));
		return resolver;
	}
	
	@Bean
	public Filter characterEncodingFilter() {
	  CharacterEncodingFilter characterEncodingFilter =new CharacterEncodingFilter();
	  characterEncodingFilter.setEncoding("UTF-8");
	  characterEncodingFilter.setForceEncoding(true);
	  return characterEncodingFilter;
	}


	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

}
