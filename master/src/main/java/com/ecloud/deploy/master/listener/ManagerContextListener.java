package com.ecloud.deploy.master.listener;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.context.ContextLoaderListener;

import com.ecloud.common.constant.EConstants;
import com.ecloud.common.rpc.udp.NettyEndpoint;
import com.ecloud.common.zookeeper.ZKClient;
import com.ecloud.deploy.master.common.MessageCache;
import com.ecloud.deploy.master.handler.ManagerHandler;

@WebListener
public class ManagerContextListener extends ContextLoaderListener {

    private static final Logger LOG = LoggerFactory.getLogger(ManagerContextListener.class);

    private PathChildrenCache managerListener = null;
    private PathChildrenCache marathonListener = null;
    private TreeCache scaleListener = null;
    private NettyEndpoint endpoint;

    private String zkConnections ;
    private int zkTimeout;
    private int rpcPort;
    private int rpcTimeOut ;
	@Autowired
    private Environment env;
    
    public ManagerContextListener(Environment env){
    	this.env = env;
    	this.zkConnections = env.getProperty("ecloud.master.zookeeper.connection");
    	this.zkTimeout = env.getProperty("ecloud.master.zookeeper.session.timeOut",Integer.class,3000);
    	this.rpcPort = env.getProperty("ecloud.master.netty.udp.port",Integer.class,9968);
    	this.rpcTimeOut = env.getProperty("ecloud.master.agent.rpc.message.timeOut",Integer.class,20);
    }
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	LOG.info("init Manager Listener");
    	try {
    		if(ZKClient.connect(zkConnections,zkTimeout)){
				managerListener = ZKClient.addPathListener(EConstants.MASTER_ZK_PATH, new ManagerElectionListener(env));
				marathonListener = ZKClient.addPathListener(EConstants.MASTER_ZK_PATH,new MarathonElectionListener(env));
				scaleListener = ZKClient.addTreeListener(EConstants.APP_ZK_PATH ,new ScaleListener(env));
    		}
    	} catch (Exception e) {
    		LOG.error("Start listener error :{}",e.getMessage());
    	}
    	
    	endpoint = new NettyEndpoint(MessageCache.getHost(),rpcPort,rpcTimeOut);
    	endpoint.registHandler(new ManagerHandler(env,endpoint));
    	endpoint.start();
    }

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		try {
			if(null!=managerListener) managerListener.close();
			if(null!=marathonListener) marathonListener.close();
			if(null!=scaleListener) scaleListener.close();
			if(null!=endpoint) endpoint.stop();
			if(ZKClient.isConnected()) ZKClient.close();
		} catch (IOException e) {
			LOG.error("Stop listener error :{}",e.getMessage());
		}
	}
    
}
