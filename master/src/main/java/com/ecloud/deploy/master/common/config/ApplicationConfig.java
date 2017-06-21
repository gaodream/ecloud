package com.ecloud.deploy.master.common.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.context.ContextLoaderListener;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.ecloud.deploy.master.filter.AccessFilter;
import com.ecloud.deploy.master.listener.ManagerContextListener;
import com.ecloud.deploy.master.listener.PingServlet;

/**
 * springboot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 */
@Configuration //相当于Spring application.xml配置文件
@EnableTransactionManagement
@AutoConfigureAfter(Environment.class)
public class ApplicationConfig implements EnvironmentAware {
    
	@Autowired
    private Environment env;
    
	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}


	/**
     * 创建数据源
     */
    @Bean(name="dataSource",destroyMethod = "close", initMethod="init") 
    public DataSource getDataSource() throws Exception{
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("spring.datasource.driver-class-name"));
        props.put("url", env.getProperty("spring.datasource.url"));
        props.put("username", env.getProperty("spring.datasource.username"));
        props.put("password", env.getProperty("spring.datasource.password"));
        props.put("minIdle", env.getProperty("spring.datasource.minIdle"));
        props.put("initialSize", env.getProperty("spring.datasource.initialSize"));
        props.put("maxActive", env.getProperty("spring.datasource.maxActive"));
        props.put("maxWait", env.getProperty("spring.datasource.maxWait"));
        props.put("validationQuery", env.getProperty("spring.datasource.validationQuery"));
        props.put("filters", env.getProperty("spring.datasource.filters"));
        return DruidDataSourceFactory.createDataSource(props);
    }
    
    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean(name="sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);//指定数据源(这个必须有，否则报错)
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
       // sessionFactory.setConfigLocation(resolver.getResource(env.getProperty("spring.mybatis.configLocation")));
        //下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
        sessionFactory.setTypeAliasesPackage(env.getProperty("spring.mybatis.typeAliasesPackage"));//指定基包
        sessionFactory.setMapperLocations(resolver.getResources(env.getProperty("spring.mybatis.mapperLocations")));//指定xml文件位置
        
        
        return sessionFactory.getObject();
    }

    

   @Bean
   public MapperScannerConfigurer mapperScannerConfigurer() {
      MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
      mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
      mapperScannerConfigurer.setBasePackage(env.getProperty("spring.mybatis.basePackage"));
      return mapperScannerConfigurer;
   }
   
   @Bean
   public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
       return new SqlSessionTemplate(sqlSessionFactory);
   }
   
   @Bean
   public FilterRegistrationBean filter(){
	   FilterRegistrationBean register = new FilterRegistrationBean();
	   register.setFilter(new AccessFilter());
	   return register;
   }	
   
   @Bean
   public ServletRegistrationBean listener(){
	   ServletRegistrationBean register = new ServletRegistrationBean();
	   register.setServlet(new PingServlet());
	   return register;
   }
   
   @Bean
   public ServletListenerRegistrationBean<ContextLoaderListener>  contextLoaderListener(){
	   ServletListenerRegistrationBean<ContextLoaderListener>  listener = new ServletListenerRegistrationBean<ContextLoaderListener>();
	   listener.setListener(new ManagerContextListener(env));
	   return listener;
   }
   
   
   
}