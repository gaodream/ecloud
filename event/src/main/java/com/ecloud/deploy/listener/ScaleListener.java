package com.ecloud.deploy.listener;

import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;

import com.ecloud.common.zookeeper.TreeListener;
import com.ecloud.deploy.common.MessageCache;

public class ScaleListener extends TreeListener {

	@Value("${ecloud.master.zookeeper.connection}")
	private String zkConnections;
	@Value("${ecloud.master.zookeeper.session.timeOut:3000}")
	private int zkTimeout;

	public ScaleListener(Environment env) {}
	
	@Override
	public void childUpdated(String path, String data, TreeCacheEvent event) {
		super.childUpdated(path, data, event);
		MessageCache.putAppScale(path, data);
	}

	@Override
	public void nodeRemoved(String path, TreeCacheEvent event) {
		super.nodeRemoved(path, event);
		MessageCache.removeAppScale(path);
	}

	@Override
	public void nodeAdded(String path, String data, TreeCacheEvent event) {
		super.nodeAdded(path, data, event);
		MessageCache.putAppScale(path, data);
	}

}
