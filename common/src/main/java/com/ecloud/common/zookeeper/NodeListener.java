package com.ecloud.common.zookeeper;


import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class NodeListener implements NodeCacheListener {

	private static Logger LOG = LoggerFactory.getLogger(NodeListener.class);

	@Override
	public void nodeChanged() throws Exception {
		LOG.info("************nodeChanged*************");
		
	}
			
	
}
