package com.ecloud.deploy.master.listener;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.alibaba.fastjson.JSON;
import com.ecloud.common.constant.EConstants;
import com.ecloud.common.info.AgentNode;
import com.ecloud.common.info.MasterNode;
import com.ecloud.common.zookeeper.PathListener;
import com.ecloud.common.zookeeper.ZKClient;
import com.ecloud.deploy.master.common.MessageCache;

public class ManagerElectionListener extends PathListener{
	
	private static final Logger LOG = LoggerFactory.getLogger(ManagerElectionListener.class);
    
    private String host;
    private int rpcPort;
    
    private MasterNode node;
    

    public ManagerElectionListener(Environment env) {
    	this.host = MessageCache.getHost();
    	this.rpcPort = env.getProperty("ecloud.master.netty.udp.port", Integer.class, 9968);
    	node = new MasterNode(host,rpcPort);
    }
    
	
   @Override
   public synchronized void changeEvent() {
	   try {
		   if(checkMaster()){
			 //启动相关的恢复任务，从zookeeper上恢复本地缓存中的信息【Agents,Observers,tasks】
			   this.recoverFromZookeeper();
		   }else{
			   //清理本地缓存
			   this.cleanCache();
		   }
	   } catch (Exception e) {
		   LOG.error("{}",e.getMessage());
	   }
   }
   
   /**
    * 1.检查当前节点是否在zookeeper上，如果没有则将该节点信息写到zookeeper
    * 2.检查当前节点是否是主节点 
    * @return 当前节点是否是主节点 
    * @throws Exception 
    */
   private boolean checkMaster() throws Exception{
	   // 获取目前最新的leader地址，获取的是zookeeper上序号最小的节点
	   boolean result = false;
	   if (ZKClient.checkExists(EConstants.MARATHON_ZK_PATH)) {
			List<String> managerList = ZKClient.getChildrenPath(EConstants.MASTER_ZK_PATH);
			//排序后的list 默认采用String的比较器
			List<String> sortList = managerList.parallelStream().filter((String manager)->{
				return manager.startsWith(EConstants.MASTER_NAME);
			}).sorted((String first,String second)->{
				return first.compareTo(second);
			}).collect(Collectors.toList());

			boolean isExist = false;
			if (!sortList.isEmpty()) {
				int size = sortList.size();
				for(int i=0;i<size;i++){
					String MasterNode = sortList.get(i);
					String managerData = ZKClient.getData(EConstants.MASTER_ZK_PATH + "/" + MasterNode);
					MasterNode node = JSON.parseObject(managerData, MasterNode.class);
					//说明当前节点在zookeeper中存在
					if(host.equals(node.getHost())&&rpcPort==node.getRpcPort()) {
					  //说明当前节点为主节点
					   LOG.info("Mode: {}",i==0?"ACTIVE":"STANDBY");
					   result = i==0;
					   MessageCache.isMaster = i==0;
					   isExist = true;
					   break;
				     } 
				}
			}
			//如果zookeeper上没有当前节点，需要将当前节点写到zookeeper上
			if(!isExist){
				MessageCache.isMaster = false;
				ZKClient.createAndWrite(EConstants.MASTER_ZK_PATH + "/" + EConstants.MASTER_NAME
						,JSON.toJSONString(node)
						,CreateMode.EPHEMERAL_SEQUENTIAL);
			}
		}
       return result;
   }
  
   /**
	 * Manager变为Active时，从zookeeper中加载恢复数据
	 * 1.恢复ZK中的Agent数据
	 * 2.恢复ZK中的Observer数据
	 * 3.恢复ZK中的Deployment状态的容器数据
	 */
   public void recoverFromZookeeper() {
		try {
			//当为主Master时才加载,恢复ZK中的Agent数据
			if(MessageCache.isMaster){
				MessageCache.cleanWorker();
				List<String> agentList = ZKClient.getChildrenFullPath(EConstants.AGENT_ZK_PATH);
				for(String agentPath : agentList){
					String agentInfo = ZKClient.getData(agentPath);
					AgentNode agent = JSON.parseObject(agentInfo,AgentNode.class);
					agent.setLastUpdTime(System.currentTimeMillis());
					MessageCache.putWorker(agent.getHost(), agent);
				}
			}
		} catch (Exception e) {
			LOG.error("load data form zookeeper error:{}",e.getMessage());
		}
		
	}
   
   	/**
	 * 清理本地内存中的数据
	 */
	public void cleanCache() {
		MessageCache.getAppMap().clear();
		MessageCache.getAppScale().clear();
		MessageCache.getCtnStatusMapping().clear();
	}
}
