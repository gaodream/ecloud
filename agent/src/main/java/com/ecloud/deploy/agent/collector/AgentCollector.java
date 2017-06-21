package com.ecloud.deploy.agent.collector;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.newsclub.net.unix.AFUNIXSocketException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ecloud.common.constant.EConstants;
import com.ecloud.common.info.AgentMetrics;
import com.ecloud.common.info.ContainerMetrics;
import com.ecloud.common.model.Pair;
import com.ecloud.common.utils.FormatUtils;
import com.ecloud.deploy.agent.common.AgentConfig;
import com.ecloud.deploy.agent.common.EDockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Statistics;

public class AgentCollector {
	
	private static final Logger LOG = LoggerFactory.getLogger(AgentCollector.class);
	
	private static final Long TIMES = 1024L;
	
	private static Sigar sigar = new Sigar();
	
	private EDockerClient eDockerClient;
	
	private static Map<String,StatsCallback> callBackMap = new ConcurrentHashMap<String, StatsCallback>();
	
	//每个容器对应的指标
    private static Map<String,List<CtnSourceMetrics>> tmpMetrics = new ConcurrentHashMap<String,List<CtnSourceMetrics>>();
	
    //ctnId:<marathonId,taskId> 为了减少inspect动作
    private static Map<String,Pair<String,String>> ctnCache = new ConcurrentHashMap<String,Pair<String,String>>();
    
	private static String netName = null;
	
	private double lastDownNetIO;
	private double lastUpNetIO;
	
	private String host ;
	private int collectPeriod;

	public AgentCollector(){}
	public AgentCollector(AgentConfig config,EDockerClient eDockerClient) {
		this.host = config.getString(AgentConfig.CURRENT_HOST_IP);
		this.collectPeriod = config.getInteger("ecloud_agent_collect_period",30);
		this.eDockerClient = eDockerClient;
	}
	
	public AgentMetrics doCollect(){
		LOG.debug("Collector begin to collect....");
		AgentMetrics agentMetrics = null;
		//1.主机采集
		try {
			agentMetrics = doCollectHost();
		} catch (SigarException e) {
			LOG.error("HOST METRICS COLLECT ERROR : {} . " ,e.getMessage());
		}
		
		if(agentMetrics==null){
			agentMetrics = new AgentMetrics();
		}
		
		//2.容器采集
		List<ContainerMetrics> metrics = doCollectContainer();
		try {
			if(null!=metrics){
				agentMetrics.setContainerList(metrics);
			}
		}catch(Exception e){
			LOG.error("CONTAINER METRICS COLLECT ERROR  : {}",e.getMessage());
		}
		//LOG.debug("agentMetrics : {}",JSON.toJSONString(agentMetrics));
		return agentMetrics;
	}

	/**
	 * 采集主机相关资源
	 * @param metrics
	 * @return
	 * @throws SigarException
	 */
 	private AgentMetrics doCollectHost() throws SigarException{
 		AgentMetrics metrics = new AgentMetrics();
		//1.CPU部分
		CpuPerc[] cpus = sigar.getCpuPercList();
		double  totalCpu = 0;
		double idleCpu = 0;
		for(CpuPerc cpu : cpus){
			totalCpu += 1;
			idleCpu += cpu.getIdle();
		}
		//%之前的数值，保留两位
		metrics.setTotalCpu(FormatUtils.formatDouble(totalCpu,2));// CPU总量
		metrics.setUsedCpu(FormatUtils.formatDouble((totalCpu - idleCpu),2));// 当前总量
		metrics.setFreeCpu(FormatUtils.formatDouble(idleCpu,2));// 空闲总量
		
		//2.MEM部分 单位为M
		Mem mem = sigar.getMem();  
		metrics.setTotalMem(FormatUtils.formatDouble(mem.getTotal() / TIMES / TIMES,2)); // 内存总量
		metrics.setFreeMem(FormatUtils.formatDouble(mem.getFree() / TIMES / TIMES ,2));// 当前内存剩余量
		metrics.setUsedMem(FormatUtils.formatDouble((mem.getTotal() - mem.getFree()) /  TIMES / TIMES ,2)); // 当前内存使用量
		
        Swap swap = sigar.getSwap();
        metrics.setTotalSwapMem(FormatUtils.formatDouble(swap.getTotal() / TIMES / TIMES,2));// 交换区总量
        metrics.setFreeSwapMem(FormatUtils.formatDouble(swap.getFree() / TIMES / TIMES,2));// 当前交换区剩余量
        metrics.setUsedSwapMem(FormatUtils.formatDouble((swap.getTotal()-swap.getFree())/ TIMES / TIMES,2)); // 当前交换区使用量
        
        //3.磁盘 单位为M
        FileSystem[] fileList = sigar.getFileSystemList();
        long totalDisk = 0, usedDisk = 0;
        for (FileSystem fs : fileList) {
			try{
				FileSystemUsage stat = sigar.getFileSystemUsage(fs.getDirName()); 
				totalDisk += stat.getTotal();
				usedDisk += stat.getUsed();
			}catch(Exception ee){
				LOG.warn("the file {} has no permision for current user,so skip it ",fs.getDirName());
				continue;
			}
		}
        metrics.setTotalDisk(FormatUtils.formatDouble(totalDisk / TIMES,2));
        metrics.setUsedDisk(FormatUtils.formatDouble(usedDisk / TIMES,2));
        
        //4.磁盘IO部分
        // sigar.getFileSystemUsage("").get
		//5.NETIO部分
        double downNetIO = 0;
        double upNetIO = 0;
        if(netName==null){
        	String ifNames[] = null;
        	try{
        		ifNames = sigar.getNetInterfaceList();
        	}catch(Exception e){
        		LOG.error("Exception:"+e.getMessage());
        	}
        	for (String ifName : ifNames) {
        		NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(ifName);
        		if (host.equals(ifconfig.getAddress())) {
        			netName = ifName;
        			NetInterfaceStat ifstat = sigar.getNetInterfaceStat(netName);
        			downNetIO = downNetIO + ifstat.getRxBytes();
        			upNetIO = upNetIO + ifstat.getTxBytes();
        			break;
        		}
        	}
        }else{
        	NetInterfaceStat ifstat = sigar.getNetInterfaceStat(netName);
    		downNetIO = downNetIO + ifstat.getRxBytes();
    		upNetIO = upNetIO + ifstat.getTxBytes();
    		
        }
        if(lastDownNetIO==0||lastUpNetIO==0){
	        metrics.setDownNetIO(0);
	        metrics.setUpNetIO(0);
        }else{
        	metrics.setDownNetIO(FormatUtils.formatDouble((downNetIO - lastDownNetIO)/collectPeriod, 2));
        	metrics.setUpNetIO(FormatUtils.formatDouble((upNetIO -lastUpNetIO)/collectPeriod, 2));
        }
        lastDownNetIO = downNetIO;
        lastUpNetIO = upNetIO;
        return metrics;
	}
	
 	/**
	 * 采集容器相关资源
	 * @param metrics
	 * @return
	 * @throws SigarException
	 */
 	private List<ContainerMetrics> doCollectContainer(){
 		LOG.debug("********************doCollectContainer***********************");
 		List<Container> ctnList = null;
		try {
			ctnList = eDockerClient.getContainerList();
		} catch (Exception e) {
			if(e instanceof AFUNIXSocketException ){
        		callBackMap.clear();
        	}
            LOG.warn("when exec list cmd exception : {}", e);
		}

 		if(ctnList.isEmpty()){
 			return null; 
 		}

		List<ContainerMetrics> metricsList = new ArrayList<ContainerMetrics>();
		Set<String> ctnIds = new HashSet<String>();
		for(Container container : ctnList){
			//1.基本信息
			ContainerMetrics ctnMetrics = new ContainerMetrics();
			String ctnId = container.getId();
			String ctnName = container.getNames()[0].substring(1);
			
			//2.回调处理
			initStats(ctnId);
			ctnIds.add(ctnId);
			
			//3.信息组装
			ctnMetrics.setCtnId(ctnId);
			ctnMetrics.setCtnName(ctnName);
			
			List<CtnSourceMetrics> metricList = tmpMetrics.get(ctnId);
			if(metricList!=null&&metricList.size()>0){
				ctnMetrics.setTotalMem(FormatUtils.formatDouble(metricList.get(0).getTotalMem(), 2));
			}
			//填充其他信息
			fillCtnInfo(ctnId,ctnMetrics);
			
			if(ctnName.startsWith(EConstants.CTN_TYPE.MESOS)){
				ctnMetrics.setCtnType(EConstants.CTN_TYPE.MESOS);
				String marathonId = "";
				String taskId = "";
				if(null==ctnCache.get(ctnId)){
					marathonId = eDockerClient.inspectAndGetEnv(ctnId, EConstants.MARATHON_APP_ID);
					taskId = eDockerClient.inspectAndGetEnv(ctnId, EConstants.MESOS_TASK_ID);
					ctnCache.put(ctnId, new Pair<String,String>(marathonId,taskId));
				}else{
					Pair<String, String> cachepair = ctnCache.get(ctnId);
					marathonId = cachepair.getFirst();
					taskId = cachepair.getSecond();
				}
				
				ctnMetrics.setMarathonId(marathonId);
				ctnMetrics.setTaskId(taskId);
			}
			metricsList.add(ctnMetrics);
			
		}
		
		//删除死亡容器的回调函数，避免类似于仓库这类容器，重启后ctnId不变无法重新注册callback
		Iterator<String> itor = callBackMap.keySet().iterator();
		while(itor.hasNext()){
			String id = itor.next();
			if(!ctnIds.contains(id)){
				callBackMap.remove(id);
				ctnCache.remove(id);
			}
		}
		ctnIds.clear();
		//删除上一个周期的采集数据
		if (null != ctnList){
			for(Container container:ctnList){
				String containerId = container.getId();
				if(tmpMetrics.get(containerId)!=null){
					tmpMetrics.remove(containerId);
				}
			}
		} 
        return metricsList;
	}

	
	private void initStats(String containerId){
    	//因为callback会自我回调，因此只能初始化一次，初始化过后缓存起来，用来下次检查
		StatsCallback callback = callBackMap.get(containerId);
		if (null == callback){
			LOG.info("Callback of Container[{}] is null,create callback",containerId);
			callback = (StatsCallback)eDockerClient.execStats(containerId,new StatsCallback(containerId));
			callBackMap.put(containerId, callback);
		}
	}
	
    private void fillCtnInfo(String containerId,ContainerMetrics ctnMetrics){
	   List<CtnSourceMetrics> list = tmpMetrics.get(containerId);
	   int size = null==list?0:list.size();
	   if(size>0){
		   	double totalMem = 0d;
		   	double totalCpu = 0d;
		   	double upNetIO = 0d;; //上行带宽
		    double downNetIO = 0d;;//下行带宽
		    double upDiskIO = 0d;; //磁盘IO
		    double downDiskIO = 0d;; //磁盘IO
		   for(CtnSourceMetrics metric :list){
			   totalCpu += metric.getCpuUsage();
			   totalMem += metric.getUsedMem();
			   upNetIO += metric.getUpNetIO();
			   downNetIO += metric.getDownNetIO();
			   upDiskIO += metric.getUpDiskIO();
			   downDiskIO += metric.getDownDiskIO();
		   }
			ctnMetrics.setCpuUsage(FormatUtils.formatDouble(totalCpu * 100 /size, 2));
			ctnMetrics.setUsedMem(FormatUtils.formatDouble(totalMem /TIMES / TIMES /size, 2));
			ctnMetrics.setUpNetIO(FormatUtils.formatDouble(upNetIO /TIMES  /size, 2));
			ctnMetrics.setDownNetIO(FormatUtils.formatDouble(downNetIO /TIMES  / size, 2));
			ctnMetrics.setUpDiskIO(FormatUtils.formatDouble(upDiskIO /TIMES / TIMES /size, 2));
			ctnMetrics.setDownDiskIO(FormatUtils.formatDouble(downDiskIO /TIMES / TIMES /size, 2));
	   }
	
   }
	
	class StatsCallback implements ResultCallback<Statistics>{
		   
		   private String containerId;
		   private long lastUsrCpuUsage = 0;//上一次的用户cpu使用
		   private long lastSysCpuUsage = 0;// 上一次系统使用的cpu
		   
		   public StatsCallback(String containerId) {
			   this.containerId = containerId;
		   }
		 
		   @Override
		   public void onStart(Closeable closeable) {
			   LOG.debug("onStart...");
		   }
		   
		   @Override
		   public void onComplete() {
			   LOG.debug("onComplete....");
		   }   
		   
		   @Override
		   public void onError(Throwable throwable) {
			   LOG.debug("onError...."+throwable.getMessage());
		   }
		   
		   @Override
		   public void close() throws IOException {
			   LOG.debug("close....");
		   }
		   
		   @Override
		   public void onNext(Statistics statistics) {
			   
			   //1.CPU资源使用率计算
			   double cpuUsage = computeCpu(statistics.getCpuStats());
			   
			   //2.内存资源使用率计算
			   Map<String, Object> memMap = statistics.getMemoryStats();
			   long totalMem = Long.parseLong(memMap.get(EConstants.CONTAINER_MEMORY_LIMIT).toString());
			   long usedMem = Long.parseLong(memMap.get(EConstants.CONTAINER_MEMORY_USAGE).toString());
			   
			   //3.网络带宽计算
			   Pair<Long,Long> netPair =  computeNetIO(statistics.getNetworks());
			   
			   //4.磁盘IO计算
			   Pair<Long,Long> diskPair =  computeDiskIO(statistics.getBlkioStats());
			   
			   List<CtnSourceMetrics> metricList = tmpMetrics.get(containerId);
			   
			   CtnSourceMetrics metrics = new CtnSourceMetrics(cpuUsage,
					   totalMem,usedMem,
					   netPair.getFirst(),netPair.getSecond(),
					   diskPair.getFirst(),diskPair.getSecond());
			   
			   if (null == metricList){
				   metricList = new ArrayList<CtnSourceMetrics>();
				   metricList.add(metrics);
				   tmpMetrics.put(containerId,metricList);
			   }else{
				   metricList.add(metrics);
			   }
		   }
		   
		   /**
		    * @Description: 计算磁盘使用率
		    * @param memMap
		    */
		   private Pair<Long,Long> computeDiskIO(Map<String, Object> blkMap) {
			   JSONArray array  = JSON.parseArray(JSON.toJSONString(blkMap.get(EConstants.CONTAINER_BLK_IO)));
			   long upBlkIO = 0 ,downBlkIO = 0;
			   for(int i=0;i<array.size();i++){
				  JSONObject obj = array.getJSONObject(i);
				  String op = obj.getString("op");
				  if("Read".equals(op)){
					  upBlkIO += obj.getLong("value");
				  }
				  if("Write".equals(op)){
					  downBlkIO += obj.getLong("value");
				  }
			   }
			   return new Pair<Long,Long>(upBlkIO,downBlkIO);
		   }

		   /**
		    * @Description: 计算cpu使用率
		    * @param cpuMap
		    */
		   private double computeCpu(Map<String, Object> cpuMap) {
			   JSONObject cpuObj = JSON.parseObject(JSON.toJSONString(cpuMap));
			   JSONObject cpuUsageObj = cpuObj.getJSONObject(EConstants.CONTAINER_CPU_USAGE);
			   long totalUsage = cpuUsageObj.getLong(EConstants.CONTAINER_TOTAL_USAGE);
			   long systemCpuUsage = cpuObj.getLong(EConstants.CONTAINER_SYSTEM_CPU_USAGE);
			   int cpusize = cpuUsageObj.getJSONArray(EConstants.CONTAINER_PERCPU_USAGE).size();
			   // 检查上次的用户和系统的cpu使用是否为0，如果为0则直接赋值后返回0，否则继续运算
			   double rst = 0;
			   if (this.lastSysCpuUsage != 0 && this.lastUsrCpuUsage != 0) {
				   //https://github.com/docker/docker/blob/0d445685b8d628a938790e50517f3fb949b300e0/api/client/stats.go#L199
				   rst = (float) ((((totalUsage - lastUsrCpuUsage) * 100.0 * cpusize) / ((systemCpuUsage - lastSysCpuUsage) * 1.0)));
			   }
			   // 更新记录上次的cpu使用值
			   this.lastSysCpuUsage = systemCpuUsage;
			   this.lastUsrCpuUsage = totalUsage;
			   return rst;
		   }
		   
		   /**
		    * @Description: 计算网络IO使用率
		    */
		   private Pair<Long,Long> computeNetIO(Map<String, Object> netMap) {
			   Iterator<String> itor = netMap.keySet().iterator();
			   long downNetIO = 0;
			   long upNetIO = 0;
			   while(itor.hasNext()){
				   @SuppressWarnings("unchecked")
				   Map<String, Integer> map = (Map<String,Integer>)netMap.get(itor.next());
				   downNetIO = downNetIO + map.get("rx_bytes");
				   upNetIO = upNetIO + map.get("tx_bytes");
			   }
			   
			   return new Pair<Long,Long>(upNetIO ,downNetIO);
		   }
		   
	   }
}
