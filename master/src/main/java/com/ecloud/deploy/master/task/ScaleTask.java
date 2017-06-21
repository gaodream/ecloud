package com.ecloud.deploy.master.task;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ecloud.common.marathon.model.ScaleVO;
import com.ecloud.deploy.master.common.MessageCache;

/**
 * 处理自动扩缩容
 * @author gaogao
 *
 */
@Component
public class ScaleTask {
	
	
	//@Scheduled(fixedRateString="${ecloud.master.task.agegreate.cron}")
	public void handAutoScale(){
		Map<String,String> scaleInfo = MessageCache.getAppScale();
		scaleInfo.values().parallelStream().forEach((String appStr)->{
			ScaleVO scaleVO = JSON.parseObject(appStr, ScaleVO.class);
			/*
			 * 满足如下条件：
			 * 	1.应用在运行中 
			 *  2.扩缩容类型为自动;手动[1],自动[2]。-->只有自动扩缩容才会同步到zookeeper次调价你不需要判断
			 *  3.服务状态在运行中
			 */
			if(MessageCache.containsApp(scaleVO.getMarathonAppId())&&scaleVO.getStatus()==2){
				
				
				
				
				
			}
		});
	}

}
