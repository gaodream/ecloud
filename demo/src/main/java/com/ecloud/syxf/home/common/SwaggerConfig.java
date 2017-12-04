package com.ecloud.syxf.home.common;
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
	                .apis(RequestHandlerSelectors.basePackage("com.dream.home"))  
	                .paths(PathSelectors.any())  
	                .build();  
	    }  
	  
	    private ApiInfo apiInfo() {  
	        return new ApiInfoBuilder()  
	                .title("Dream 接口")  
	                .termsOfServiceUrl("Dream官网")  
	                .description("更多问题，请关注 : gaogao")
	                .license("Dream 1.0.RELEASE")
	                .contact(new Contact("站长","", "1039524749@qq.com"))
	                .version("1.0.RELEASE")
	                .build();  
	    }  
}
