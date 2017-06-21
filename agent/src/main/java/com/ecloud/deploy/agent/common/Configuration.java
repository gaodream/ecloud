package com.ecloud.deploy.agent.common;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.event.ConfigurationEvent;
import org.apache.commons.configuration.event.ConfigurationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecloud.common.info.AgentNode;
import com.sun.management.OperatingSystemMXBean;

/**
 * @author gaogao
 *
 */
@SuppressWarnings("restriction")
public abstract class Configuration {
	
	protected static Logger LOG = LoggerFactory.getLogger(Configuration.class);
	
	private static Map<String, Object> envs = new ConcurrentHashMap<String, Object>();
	
	public final static String CONTEXT_PATH = "ecloud.context.path";
	public final static String CURRENT_HOST_IP = "ecloud.host.ip";
	public final static String CURRENT_HOST_NAME = "ecloud.host.name";
	public final static String CURRENT_HOST_CORES = "ecloud.host.cores";
	public final static String CURRENT_HOST_MEMORY = "ecloud.host.memory";
	
	private static PropertiesConfiguration propConfig;

    public static AgentNode getHostInfo(){
    	InetAddress inet = null;
    	AgentNode result = new AgentNode();
		try {
			inet = InetAddress.getLocalHost();
			result.setHost(inet.getHostAddress());
			result.setHostName(inet.getHostName());
		} catch (UnknownHostException e) {
			LOG.error("GET LOCAL HOST ERROR : {}",e.getMessage());
		}
		
		result.setCores(Runtime.getRuntime().availableProcessors());
        OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
    		 .getOperatingSystemMXBean();
        long totalMemorySize = osmxb.getTotalPhysicalMemorySize();
        result.setMemory((int)(totalMemorySize/1024/1024));
	    return result;
    }
   
    public void load(String fileName)  {
		//1.通用环境变量
		String contextPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		
		envs.put(CONTEXT_PATH, contextPath);
		AgentNode worker = getHostInfo();
		envs.put(CURRENT_HOST_IP, worker.getHost());
		envs.put(CURRENT_HOST_NAME, worker.getHostName());
		envs.put(CURRENT_HOST_CORES, worker.getCores());
		envs.put(CURRENT_HOST_MEMORY, worker.getMemory());
		
		//2.自定义变量
		propConfig = new PropertiesConfiguration();
		
        propConfig.setEncoding("UTF-8");
        
        propConfig.setFileName(fileName);
        
        propConfig.setDelimiterParsingDisabled(true);
        
        //当配置文件修改时，自动加载，不需重启系统
        //propConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
        
		propConfig.addConfigurationListener(new ConfigurationListener(){

			@Override
			public void configurationChanged(ConfigurationEvent event) {
				envs.put(event.getPropertyName(), event.getPropertyValue());
				if(event.isBeforeUpdate()){
					LOG.info("Config before change, [{}:{}]",event.getPropertyName(),event.getPropertyValue());
				}else{
					LOG.info("Config after  change, [{}:{}]",event.getPropertyName(),event.getPropertyValue());
				}
				
			}}
		);
        
        try {
			propConfig.load();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
        
    }
    
    
    /**
	 * 根据key获取对应的值
	 * @param key
	 * @return
	 */
	public void addProperty(String key,String value) {
		envs.put(key, value);
	}
	
	
	 /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean containsKey(String key){
        return envs.containsKey(key);
    }
	
	
	/**
     * 根据key获取对应的值
     * @param key
     * @return
     */
    public String getString(String key) {
        String result = "";
        if(key != null && !key.equals("")){
            Object obj = envs.get(key);
            if(obj != null){
                result = String.valueOf(obj);
            }
        }
        return result.trim();
    }
    
    public String getString(String key,String defaultValue) {
    	String result = defaultValue;
    	if(key != null && !key.equals("")){
    		Object obj = envs.get(key);
    		if(obj != null){
    			result = String.valueOf(obj);
    		}
    	}
    	return result.trim();
    }
    
  
    
    public int getInteger(String key, int defaultValue) {
        String value = getString(key);
        if( value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    public long getLong(String key, long defaultValue) {
        String value = getString(key);
        if( value == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
}
