package com.ecloud.deploy.agent.common;

import java.io.File;


/**
 * @author gaogao
 * 静态文件支持热加载
 */
public class AgentConfig extends Configuration{
	
	public final static String AGENT_CONF = "agent.properties";
	public final static String LOGBACK_CONF = "logback.xml";
	
	private static AgentConfig instance = null;
	
	private AgentConfig(){}
	
	public static synchronized AgentConfig getInstance(){
		if(null == instance){
			synchronized (AgentConfig.class) {
                if (instance == null) {
                    instance = new AgentConfig();   
                    instance.load(AGENT_CONF);
                }   
            }   
		}
		return instance;
	}
	
	public static synchronized AgentConfig getInstance(String path){
		if(null == instance){
			synchronized (AgentConfig.class) {
                if (instance == null) {
                    instance = new AgentConfig();   
                    instance.load(path + File.separator +AGENT_CONF);
                }   
            }   
		}
		return instance;
	}

}
