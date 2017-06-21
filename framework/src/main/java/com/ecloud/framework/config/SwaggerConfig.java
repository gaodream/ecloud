package com.ecloud.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration 
//不要继承extends WebMvcConfigurationSupport，特殊情况下继承非boot
public class SwaggerConfig {  
	  
	    @Bean  
	    public Docket createRestApi() {  
	        return new Docket(DocumentationType.SWAGGER_2)  
	                .apiInfo(apiInfo())  
	                .select()  
	                .apis(RequestHandlerSelectors.basePackage("com.ecloud.home"))  
	                .paths(PathSelectors.any())  
	                .build();  
	    }  
	  
	    private ApiInfo apiInfo() {  
	        return new ApiInfoBuilder()  
	                .title("ECLOUD [API SERVER] 接口")  
	                .termsOfServiceUrl("ECLOUD ")  
	                .description("更多问题 请关注 ECLOUD")
	                .license("ECLOUD 1.0.RELEASE")
	                .contact(new Contact("ECLOUD产品组","", "gaogao_java@sina.com"))
	                .version("1.0.RELEASE")
	                .build();  
	    }  
}
