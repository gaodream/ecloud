package com.ecloud.deploy.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ecloud.common.zookeeper.ZKClient;

/**  
 * 清理、同步zookeeper上相关信息【注册以及服务发现得内容】:
 *   1.根据最后更新时间清除Agents和Observers的注册信息
 *   2.定期清除zookeeper上得服务发现信息
 *   3.选主成功后从zookeeper上恢复Manager上得内存信息Agents,Observers,tasks
 *   3.定期将缓存数据[Agents,Observers]同步到zookeeper上
 *   @author gaogao
 */
@Component
public class CleanTask {
	
	private static final Logger LOG = LoggerFactory.getLogger(CleanTask.class);
	
	private static Set<String> serviceCache = new HashSet<String>();
	
	/*
	 * 清理zookeeper上得服务发现信息
	 */
	@Scheduled(fixedRateString="60000")
	public void cleanServiceDiscoveryNodeInZookeeper() {
        List<String> allNodes = new ArrayList<>();
        //TODO allNodes从zookeeper中获取
        // 检测上次是否存在
        for (String nodePath : allNodes) {
            if (serviceCache.contains(nodePath)) {
                // 如果已经存在，则说明已经满足在该周期内两次检测都为空，则将其清除
                try {
                    ZKClient.deletePath(nodePath);
                } catch (Exception e) {
                    LOG.warn("{}", e);
                }
            } else {
                // 如果不存在，则直接添加到集合中
            	serviceCache.add(nodePath);
            }
        }
    }
	
    
}
