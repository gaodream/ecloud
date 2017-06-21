package com.ecloud.deploy.master.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import com.ecloud.deploy.master.common.SerializerUtil;
import com.ecloud.deploy.master.common.model.ContainerMetricBean;
import com.ecloud.deploy.master.common.model.HostMetricBean;
import com.ecloud.deploy.master.common.model.RedisHostKpiBean;
import com.ecloud.deploy.master.common.service.ManagerService;

/**
 * 处理redis中的指标，聚合入库
 * @author gaogao
 *
 */
@Component
public class AggregateTask {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	          
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private ManagerService managerService;
	
	
	//@Scheduled(fixedRateString="${ecloud.master.task.agegreate.cron}")
	public void aggregateCtnKpi(){
		long max = System.currentTimeMillis();
		long min = max - 1000*60*60 - 1;
		List<ContainerMetricBean> result = new ArrayList<ContainerMetricBean>();
		redisTemplate.execute((RedisConnection conn)->{
			//1.当前所有服务
			Set<byte[]> servKeys = conn.keys(("HPS:CTN:*").getBytes());
			for(byte[] byteServKey : servKeys){
				String servKey = new String(byteServKey).replace("CTN","CTNKPI");
				//2.该服务下所有容器(field对用TaskId)
				Map<byte[], byte[]> ctnMap = conn.hGetAll(byteServKey);
				
				Iterator<Map.Entry<byte[], byte[]>> itor = ctnMap.entrySet().iterator();
				while (itor.hasNext()) {
					Map.Entry<byte[], byte[]> entry = itor.next();
					//判断value中的状态是否为runnig
					
					//如果不是break，如果是如下
					String ctnKpiKey = servKey + ":" + new String(entry.getKey());
					//EC:KPI:1000000016:1000000040:ec1000000040_1000000016-1000000040_commservice-0.8900ef56-fcc7-11e6-833b-0242ef1767eb
					Set<byte[]> tmp = conn.zRevRangeByScore(ctnKpiKey.getBytes(),min,max);
					int size = tmp.size();
					if(size<=0) continue;
					double totalCpu =0,maxCpu =0,minCpu =0 ;//CPU信息
					double totalMem =0,maxMem =0,minMem =0 ;//内存信息
					double totalNetUpIO = 0,totalNetDownIO =0,totalDiscUsage=0 ;//网路和磁盘信息
					long collectime = 0;
					String ctnId = "";
					ContainerMetricBean ctnMetric = new ContainerMetricBean();
					String[] ids = ctnKpiKey.split(":");
					ctnMetric.setAppId(Long.parseLong(ids[2]));
					ctnMetric.setTaskId(ids[4]);
					for(byte[] bytekpi : tmp){
						ContainerMetricBean metric = SerializerUtil.decode(bytekpi, ContainerMetricBean.class);
						ctnId = metric.getCtnId();
						double tmpCpu = metric.getAvgCpu();
						double tmpMem = metric.getAvgMem();
						if(maxCpu<tmpCpu)  maxCpu = tmpMem;
						if(minCpu>tmpCpu)  minCpu = tmpCpu;
						if(maxMem<tmpMem)  maxMem = tmpMem;
						if(minMem>tmpMem)  minMem = tmpMem;
						totalMem = totalMem + tmpMem;
						totalCpu = totalCpu + tmpMem;
						totalNetUpIO = totalNetUpIO + metric.getNetUpIO() ;
						totalNetDownIO = totalNetDownIO + metric.getNetDownIO();
						totalDiscUsage = totalDiscUsage + metric.getAvgDisk();
						collectime = metric.getCollectTime();
					}
					ctnMetric.setCtnId(ctnId);
					ctnMetric.setCollectTime(collectime);
					ctnMetric.setMaxCpu(maxCpu);
					ctnMetric.setMinCpu(minCpu);
					ctnMetric.setAvgCpu(totalCpu / size); 
					ctnMetric.setMaxMem(maxMem);
					ctnMetric.setMinMem(minMem);
					ctnMetric.setAvgMem(totalMem / size );
					ctnMetric.setLimitMem(totalMem);
					ctnMetric.setNetDownIO(totalNetDownIO / size);
					ctnMetric.setNetUpIO(totalNetUpIO / size);
					ctnMetric.setLimitDisk(totalDiscUsage / size);
					result.add(ctnMetric);
						
				}
			}
			return null;
		});
		if(result.size()>0) {
			System.err.println("Data will be inserted  into mysql.");
			managerService.doBatchInsertCtn(result);
		}else{
			System.err.println("no data.");
			
		}
	}
	
	//@Scheduled(fixedRateString="${ecloud.master.task.agegreate.cron}")
	public void aggregateHostKpi(){
		long max = System.currentTimeMillis();
		long min = max - 1000*60*60 - 1;
		List<HostMetricBean> result = new ArrayList<HostMetricBean>();
		redisTemplate.execute((RedisConnection conn)->{
			Set<byte[]> hostKeys = conn.keys(("EC:HOSTKPI:*").getBytes());
			for(byte[] byteHostKey : hostKeys){
				String ip = new String(byteHostKey).split(":")[2];
				Set<byte[]> tmp = conn.zRevRangeByScore(byteHostKey,min,max);
				int size = tmp.size();
				if(size<=0) continue;
				double totalCpu = 0,maxCpu = 0,minCpu = 0 ;//CPU信息
				double totalMem = 0,maxMem = 0,minMem = 0 ;//内存信息
				double totalLoad = 0,maxLoad = 0,minLoad =0 ;//负载信息
				double totalNetUpIO = 0,totalNetDownIO = 0,totalDiscUsage =0 ;//网路和磁盘信息
				long collectime = 0;
				String hostName = "";
				HostMetricBean hostMetric = new HostMetricBean();
				for(byte[] bytekpi : tmp){
					RedisHostKpiBean metric = SerializerUtil.decode(bytekpi, RedisHostKpiBean.class);
					double tmpCpu = metric.getCpuUsage();
					double tmpMem = metric.getCpuUsage();
					double tmpLoad = metric.getLoad();
					if(maxCpu<tmpCpu)  maxCpu = tmpMem;
					if(minCpu>tmpCpu)  minCpu = tmpCpu;
					if(maxMem<tmpMem)  maxMem = tmpMem;
					if(minMem>tmpMem)  minMem = tmpMem;
					if(maxLoad<tmpLoad)  maxLoad = tmpLoad;
					if(minLoad>tmpLoad)  minLoad = tmpLoad;
					totalMem = totalMem + tmpMem;
					totalCpu = totalCpu + tmpMem;
					totalLoad = totalLoad + tmpLoad;
					totalNetUpIO = totalNetUpIO + metric.getNetUpIO() ;
					totalNetDownIO = totalNetDownIO + metric.getNetDownIO();
					totalDiscUsage = totalDiscUsage + metric.getDiscUsage();
					collectime = metric.getCollectTime();
					hostName = metric.getHostName();
				}
				hostMetric.setHhkHostIp(ip);
				hostMetric.setHhkHostName(hostName);
				hostMetric.setHhkCollectTime(sdf.format(new Date(collectime)));
				hostMetric.setHhkMaxCpu(maxCpu);
				hostMetric.setHhkMinCpu(minCpu);
				hostMetric.setHhkAvgCpu(totalCpu / size); 
				hostMetric.setHhkMaxMem(maxMem);
				hostMetric.setHhkMinMem(minMem);
				hostMetric.setHhkAvgMem(totalMem / size );
				hostMetric.setHhkMaxLoad(maxLoad);
				hostMetric.setHhkMinLoad(minLoad);
				hostMetric.setHhkAvgLoad(totalLoad / size);
				hostMetric.setHhkTotalMem(totalMem);
				hostMetric.setHhkNetDownIo(totalNetDownIO / size);
				hostMetric.setHhkNetUpIo(totalNetUpIO / size);
				hostMetric.setHhkTotalDisk(totalDiscUsage / size);
				result.add(hostMetric);
			}
			
			if(result.size()>0) {
				System.err.println("Data will be inserted  into mysql.");
				managerService.doBatchInsertHost(result);
			}else{
				System.err.println("no data.");
				
			}
			return null;
		});
		
	}

}
