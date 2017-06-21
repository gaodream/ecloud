package com.ecloud.deploy.agent.collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecloud.common.model.Pair;


public class CollectorUtil {
	
	private static final Logger LOG = LoggerFactory.getLogger(CollectorUtil.class);
	
	private static final String CPU_CMD = "cat /proc/stat |grep 'cpu' ";
	private static final String MEM_CMD = "cat /proc/meminfo | grep  -e 'MemTotal' -e  'MemFree' ";
	private static final String NET_CMD = "cat /proc/net/dev";
	
	
	private static  Runtime runtime = Runtime.getRuntime(); 
	//每个容器对应的指标
	
    
	
	/**
	 * 采集主机CPU
	 * @return
	 */
 	public static Pair<Long,Long> doCollectHostCpu() {
 		LOG.info("开始收集cpu使用率");  
 		Process process = null;
 		BufferedReader content = null;
 		long total = 0 ; 
 		long idle = 0 ; 
 		try {
	 		process = runtime.exec(CPU_CMD);
	 		content = new BufferedReader(new InputStreamReader(process.getInputStream()));
	 		String line = null;  
			while((line=content.readLine()) != null){
				line = line.trim();   
				if(line.startsWith("cpu")){  
                    String[] cupInfo = line.split("\\s+");   
                    idle += Long.parseLong(cupInfo[4]);  
                    for(String s : cupInfo){  
                        if(!s.equals("cpu")){  
                        	total += Long.parseLong(s);  
                        }  
                    }     
                    break;  
                }                         
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
        return new Pair<Long,Long>(total,idle);
	}

 	/**
	 * 采集主机MEM
	 * @return
	 */
 	public static Pair<Long,Long> doCollectHostMem() {
 		LOG.info("开始收集mem使用率");  
 		Process process = null;
 		BufferedReader content = null;
 		long total = 0 ; 
 		long free = 0 ; 
 		try {
	 		process = runtime.exec(MEM_CMD);
	 		content = new BufferedReader(new InputStreamReader(process.getInputStream()));
	 		String line = null;  
			while((line=content.readLine()) != null){
				line = line.trim();   
			    String[] memInfo = line.split("\\s+");  
                if(memInfo[0].startsWith("MemTotal")){  
                	total = Long.parseLong(memInfo[1]);  
                }  
                if(memInfo[0].startsWith("MemFree")){  
                	free = Long.parseLong(memInfo[1]);  
                }  
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
        return new Pair<Long,Long>(total,free);
	}
	
 	
 	/**
	 * 采集主机MEM
	 * @return
	 */
 	public static Pair<Long,Long> doCollectHostDisk() {
 		LOG.info("开始收集mem使用率");  
 		Process process = null;
 		BufferedReader content = null;
 		long total = 0 ; 
 		long free = 0 ; 
 		try {
	 		process = runtime.exec(MEM_CMD);
	 		content = new BufferedReader(new InputStreamReader(process.getInputStream()));
	 		String line = null;  
			while((line=content.readLine()) != null){
				line = line.trim();   
			    String[] memInfo = line.split("\\s+");  
                if(memInfo[0].startsWith("MemTotal")){  
                	total = Long.parseLong(memInfo[1]);  
                }  
                if(memInfo[0].startsWith("MemFree")){  
                	free = Long.parseLong(memInfo[1]);  
                }  
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
        return new Pair<Long,Long>(total,free);
	}
 	/**
 	 * 采集主机网络带宽
 	 * /proc/net/dev
 	 * @return
 	 */
 	public static Pair<Long,Long> doCollectHostNetIO() {
 		LOG.info("开始收集netIO使用率");  
 		Process process = null;
 		BufferedReader content = null;
 		long total = 0 ; 
 		long free = 0 ; 
 		try {
 			process = runtime.exec(NET_CMD);
 			content = new BufferedReader(new InputStreamReader(process.getInputStream()));
 			String line = null;  
 			while((line=content.readLine()) != null){
 				line = line.trim();   
 				String[] memInfo = line.split("\\s+");  
 				if(memInfo[0].startsWith("MemTotal")){  
 					total = Long.parseLong(memInfo[1]);  
 				}  
 				if(memInfo[0].startsWith("MemFree")){  
 					free = Long.parseLong(memInfo[1]);  
 				}  
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
 		return new Pair<Long,Long>(total,free);
 	}
}
