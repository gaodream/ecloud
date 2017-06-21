package com.ecloud.deploy.agent.collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
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
import com.alibaba.fastjson.JSONObject;
import com.ecloud.common.constant.EConstants;
import com.ecloud.common.info.AgentMetrics;
import com.ecloud.common.info.ContainerMetrics;
import com.ecloud.deploy.agent.common.AgentConfig;
import com.ecloud.deploy.agent.common.EDockerClient;
import com.github.dockerjava.api.model.Container;

public class AgentCollector2 {
	
	private static final Logger LOG = LoggerFactory.getLogger(AgentCollector2.class);
	
	private static Sigar sigar = new Sigar();
	
	private EDockerClient eDockerClient;
	
	//每个容器对应的指标
    private static Map<String,CtnSourceMetrics> metrics = new ConcurrentHashMap<String,CtnSourceMetrics>();
	
	private String netName = null;
	private double lastDownNetIO;
	private double lastUpNetIO;
	
	private String host;
	private int collectPeriod;

	public AgentCollector2(AgentConfig config,EDockerClient eDockerClient) {
		this.eDockerClient = eDockerClient;
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
		metrics.setTotalCpu(totalCpu);// CPU总量
		metrics.setUsedCpu(totalCpu - idleCpu);// 当前总量
		metrics.setFreeCpu(idleCpu);// 空闲总量
		
		//2.MEM部分
		Mem mem = sigar.getMem();  
		metrics.setTotalMem(mem.getTotal() / 1024L); // 内存总量
		metrics.setFreeMem(mem.getFree() / 1024L);// 当前内存剩余量
		metrics.setUsedMem(mem.getTotal() - mem.getFree()  / 1024L ); // 当前内存使用量
		
        Swap swap = sigar.getSwap();
        metrics.setTotalSwapMem(swap.getTotal() / 1024L);// 交换区总量
        metrics.setFreeSwapMem(swap.getFree() / 1024L);// 当前交换区剩余量
        metrics.setUsedSwapMem(swap.getTotal() - swap.getFree() / 1024L); // 当前交换区使用量
        
        //3.磁盘
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
        metrics.setTotalDisk(totalDisk);
        metrics.setUsedDisk(usedDisk);
        //4.磁盘IO部分
      //  sigar.getFileSystemUsage("").get
		//5.NETIO部分
        double downNetIO = 0;
        double upNetIO = 0;
        if(netName==null){
        	try{
        	String ifNames[] = sigar.getNetInterfaceList();
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
        	}catch(Exception e){
        		LOG.error("Exception:"+e.getMessage());
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
        	metrics.setDownNetIO((downNetIO - lastDownNetIO)/collectPeriod);
        	metrics.setUpNetIO((upNetIO -lastUpNetIO)/collectPeriod);
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
				//callBackMap.clear();
        	}
            LOG.warn("when exec list cmd exception : {}", e);
		}
		if(ctnList.isEmpty()){
 			return null; 
 		}
		
		List<ContainerMetrics> metricsList = new ArrayList<ContainerMetrics>();
		StringBuffer buffer = new StringBuffer();
		ctnList.parallelStream().forEach(ctn -> {
			buffer.append(" " + ctn.getId());
		});
		loadContainerMetrics(buffer.toString());
		for(Container container : ctnList){
			ContainerMetrics ctnMetrics = new ContainerMetrics();
			String ctnId = container.getId();
			String ctnName = container.getNames()[0].substring(1);
			String marathonId = eDockerClient.inspectAndGetEnv(ctnId, EConstants.MARATHON_APP_ID);
			ctnMetrics.setCtnId(ctnId);
			ctnMetrics.setCtnName(ctnName);
			CtnSourceMetrics source = metrics.get(ctnId);
			ctnMetrics.setCpuUsage(source.getCpuUsage());
			ctnMetrics.setTotalMem(source.getTotalMem());
			ctnMetrics.setUsedMem(source.getUsedMem());
			
			if(ctnName.startsWith(EConstants.CTN_TYPE.MESOS)){
				ctnMetrics.setCtnType(EConstants.CTN_TYPE.MESOS);
				ctnMetrics.setMarathonId(marathonId);
			}else{
				ctnMetrics.setCtnType(EConstants.CTN_TYPE.DOCKER);
			}
			metricsList.add(ctnMetrics);
		}
		metrics.clear();
        return metricsList;
	}

	
	public AgentMetrics doCollect(){
		LOG.debug("Collector begin to collect....");
		AgentMetrics agentMetrics = null;
		//1.主机采集
		try {
			agentMetrics = doCollectHost();
			LOG.error("agentMetrics : {} . " ,JSONObject.toJSONString(agentMetrics));
		} catch (SigarException e) {
			LOG.error("HOST METRICS COLLECT ERROR : {} . " ,e.getMessage());
		}
		//2.容器采集
		if(agentMetrics==null){
			agentMetrics = new AgentMetrics();
		}
		agentMetrics.setContainerList(doCollectContainer());
		LOG.debug("agentMetrics : {}",JSON.toJSONString(agentMetrics));
		return agentMetrics;
	}
	
    private void loadContainerMetrics(String ctnIds){
    	Process process = null;
		BufferedReader content = null;
		Runtime runtime = Runtime.getRuntime();
		try {
			//docker stats --no-stream=true `docker ps | awk '{print $1}' |tail -n +2` |tail -n +2
			process = runtime.exec("docker stats --no-stream=true " + ctnIds);
			content = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;  
			while((line=content.readLine()) != null){
					StringTokenizer tokenizer = new StringTokenizer(line.trim());
					String ctnId = tokenizer.nextToken();
					String cpuUsage =tokenizer.nextToken();
					String memUsed = tokenizer.nextToken();
					String unit = tokenizer.nextToken();//GB|MB|KB
					memUsed = trans(memUsed,unit);
					tokenizer.nextToken();// /
					
					String memTotal = tokenizer.nextToken();
					unit = tokenizer.nextToken();// GB|MB|KB
					memTotal = trans(memTotal,unit);
					
					String memUsage = tokenizer.nextToken();//
					String iNetIO = tokenizer.nextToken();//
					unit = tokenizer.nextToken();// GB|MB|KB
					iNetIO = trans(iNetIO,unit);
					
					tokenizer.nextToken(); // /
					String oNetIO = tokenizer.nextToken();// 
					unit = tokenizer.nextToken();// GB|MB|KB
					oNetIO = trans(oNetIO,unit);
					
					String iDiskIO = tokenizer.nextToken();// 
					unit = tokenizer.nextToken();// GB|MB|KB
					iDiskIO = trans(iDiskIO,unit);
					
					tokenizer.nextToken(); // /
					String oDiskIO = tokenizer.nextToken();// 
					unit = tokenizer.nextToken();// GB|MB|KB
					oDiskIO = trans(oDiskIO,unit);
					
					System.out.print("ctnId:"+ctnId);
					System.out.print("\tcpuUsage:"+cpuUsage);
					System.out.print("\tmemUsed:"+memUsed);
					System.out.print("\tmemTotal:"+memTotal);
					System.out.print("\tmemUsage:"+memUsage);
					System.out.print("\tiNetIO:"+iNetIO);
					System.out.print("\toNetIO:"+oNetIO);
					System.out.print("\tiDiskIO:"+iDiskIO);
					System.out.print("\toDiskIO:"+oDiskIO);
					System.out.println();
					metrics.put(ctnId, new CtnSourceMetrics(Double.parseDouble(cpuUsage),
							Double.parseDouble(memTotal),Double.parseDouble(memUsed),
							Double.parseDouble(iNetIO),Double.parseDouble(oNetIO),
							Double.parseDouble(iDiskIO),Double.parseDouble(oDiskIO)));
			}
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}finally {
			try {
				if(content!=null) content.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
			if(process!=null)  process.destroy();
		}
    }
	
	private  String trans(String origin,String unit){
		if("GB".equalsIgnoreCase(unit.trim())){
			return String.valueOf(Double.parseDouble(origin)*1024) ;
		}else if("KB".equalsIgnoreCase(unit.trim())){
			return String.valueOf(Double.parseDouble(origin)/1024) ;
		}else if("B".equalsIgnoreCase(unit.trim())){
			return String.valueOf(Double.parseDouble(origin)/1024/1024) ;
		}else{
			return origin;
		}
	}
}
