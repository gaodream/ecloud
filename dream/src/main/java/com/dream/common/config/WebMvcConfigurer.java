package com.dream.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dream.common.interceptor.MessageInterceptor;
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
	
	@Bean //这样写的目的是为了拦截器中能拿到bean
  	public MessageInterceptor interceptor() {
	  return new MessageInterceptor();
  	}
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(interceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {  
        registry.addMapping("/**")  
                .allowedOrigins("*") 
                .allowCredentials(true)//就是这个啦  
                .allowedMethods("GET", "POST", "DELETE", "PUT")  
                .maxAge(3600);
    }
}