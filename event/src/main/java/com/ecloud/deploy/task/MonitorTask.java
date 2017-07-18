package com.ecloud.deploy.task;

import org.springframework.stereotype.Component;

/**
 * 监控平台各个组件的状态
 * @author gaogao
 *
 */
@Component
public class MonitorTask {
	
	//@Scheduled(fixedRateString="${ecloud.master.task.agegreate.cron}")
	public void listenComponet(){
		
		//1.zookeeper
		
		//2.Mesos 和 Slave
		
		//3.docker
		
		//4.Marathon
		
		//5.Manager 、 Agent 、Observer
		
		
	}
	

}
