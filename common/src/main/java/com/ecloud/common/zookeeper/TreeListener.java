package com.ecloud.common.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TreeListener implements TreeCacheListener {

	private static Logger LOG = LoggerFactory.getLogger(TreeListener.class);

	@Override
	public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
		String data = "";
		switch (event.getType()) {
		case NODE_ADDED:
			data = ZKClient.getData(event.getData().getPath());
			nodeAdded(event.getData().getPath(), data, event);
			break;
		case NODE_REMOVED:
			nodeRemoved(event.getData().getPath(), event);
			break;
		case NODE_UPDATED:
			data = ZKClient.getData(event.getData().getPath());
			childUpdated(event.getData().getPath(), data, event);
			break;
		case CONNECTION_RECONNECTED:
			LOG.warn("CONNECTION_RECONNECTED....");
			break;
		case CONNECTION_SUSPENDED:
		case CONNECTION_LOST:
			LOG.warn("Connection error,waiting...");
			break;
		default:
			LOG.info(" default Type {}", event.getType().name());
			break;
		}

	}

	public void childUpdated(String path, String data, TreeCacheEvent event) {
		
	}

	public void nodeRemoved(String path, TreeCacheEvent event) {
		
	}

	public void nodeAdded(String path, String data, TreeCacheEvent event) {
		
	}

}
