package com.dream.common.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.ecloud.framework.filter.AccessFilter;
import com.github.pagehelper.PageHelper;

/**
 * springboot集成mybatis的基本入口
 * 1）创建数据源
 * 2）创建SqlSessionFactory
 */
@Configuration //相当于Spring application.xml配置文件
//@EnableWebMvc //JSP模式下不能加该属性
@EnableTransactionManagement
@AutoConfigureAfter(Environment.class)
public class ApplicationConfig implements EnvironmentAware {
   
	@Autowired
    private Environment env;
    
	@Override
	public void setEnvironment(Environment environment) {
		this.env = environment;
	}
	 
/*	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix(env.getProperty("spring.mvc.view.prefix"));
		resolver.setSuffix(env.getProperty("spring.mvc.view.suffix"));
		return resolver;
	}*/
	@Bean
	public FreeMarkerViewResolver freeMarkerViewResolver() {
		FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
		resolver.setPrefix("");
		resolver.setSuffix(".ftl");
		resolver.setContentType("text/html; charset=UTF-8");
		resolver.setRequestContextAttribute("request");
		return resolver;
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
        
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        
        properties.setProperty("dialect", "mysql");
        //默认为false；设置为true时，会将RowBounds第一个参数offset当成pageNum页码使用
        //和startPage中的pageNum效果一样
        properties.setProperty("offsetAsPageNum", "true");
        //该参数默认为false 
        //设置为true时，使用RowBounds分页会进行count查询 
        properties.setProperty("rowBoundsWithCount", "true");
        
        //设置为true时，如果pageSize=0或者RowBounds.limit = 0就会查询出全部的结果 
        //相当于没有执行分页查询，但是返回结果仍然是Page类型）
        properties.setProperty("pageSizeZero", "true");
        //3.3.0版本可用 - 分页参数合理化，默认false禁用 -->
        //启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
        //禁用合理化时，如果pageNum<1或pageNum>pages会返回空数据 
        properties.setProperty("reasonable", "true");
        
        //3.5.0版本可用 - 为了支持startPage(Object params)方法 -->
        //增加了一个`params`参数来配置参数映射，用于从Map或ServletRequest中取值 -->
        //可以配置pageNum,pageSize,count,pageSizeZero,reasonable,不配置映射的用默认值 -->
        properties.setProperty("params", "pageNum=start;pageSize=limit;pageSizeZero=zero;reasonable=heli;count=contsql");
        
        pageHelper.setProperties(properties);
        //添加插件
        sessionFactory.setPlugins(new Interceptor[]{pageHelper});
        
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
   
   /**
    * filter
    * @return
    */
   @Bean
   public FilterRegistrationBean filter(){
	   FilterRegistrationBean register = new FilterRegistrationBean();
	   register.setFilter(new AccessFilter(env));
	   return register;
   }
   
/*   @Bean
   public ServletListenerRegistrationBean  listener(){
	   ServletListenerRegistrationBean  listener = new ServletListenerRegistrationBean();
	   listener.setListener(new ConfigurationListener(env));
	   return listener;
   }*/
   
}