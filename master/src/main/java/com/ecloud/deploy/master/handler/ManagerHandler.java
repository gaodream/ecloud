package com.ecloud.deploy.master.handler;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.alibaba.fastjson.JSON;
import com.ecloud.common.constant.EConstants;
import com.ecloud.common.info.AgentMetrics;
import com.ecloud.common.info.AgentNode;
import com.ecloud.common.info.MasterNode;
import com.ecloud.common.rpc.ThreadFactoryUtil;
import com.ecloud.common.rpc.udp.NettyEndpoint;
import com.ecloud.common.zookeeper.ZKClient;
import com.ecloud.deploy.master.common.MessageCache;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class ManagerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Logger LOG = LoggerFactory.getLogger(ManagerHandler.class);
    
    private static ScheduledExecutorService schedulePool = null;
    private NettyEndpoint adapter;
    private String host;
    private MasterNode node;
    
    private int rpcPort;
    private int cleanNodePeriod;
    private int liveTime;
    
    public ManagerHandler(Environment env,NettyEndpoint adapter) {
    	System.out.println("<<<<<<<<<<<<<<<<< ManagerHandler >>>>>>>>>>>");
    	this.adapter = adapter;
    	this.host = MessageCache.getHost();
    	this.rpcPort = env.getProperty("ecloud.master.netty.udp.port", Integer.class, 9968);
    	this.cleanNodePeriod = env.getProperty("ecloud.master.agent.clean.period", Integer.class, 60);
    	this.liveTime = env.getProperty("ecloud.master.agent.live.time", Integer.class, 120);
    	node = new MasterNode(host,rpcPort);
    	initialize();
	}

	private void initialize() {
    	
        //1.初始化线程池
        schedulePool = Executors.newScheduledThreadPool(5,ThreadFactoryUtil.create("Master-%d"));
        
        //2.连接ZK,注册节点
        try {
        	LOG.info("connect zookeeper and add listner ...");
 			if (ZKClient.isConnected()) {
 				ZKClient.createAndWrite(EConstants.MASTER_ZK_PATH,JSON.toJSONString(node),
 						CreateMode.EPHEMERAL_SEQUENTIAL);
 			}else{
 				throw new Exception("master register failed .");
 			}
        } catch (Exception e) {
        	LOG.error("Zookeeper connect error : {} ",e.getMessage());
        }
        
    
    }
	
	public void startHandler(){
		//ResourcePool.putWorker("192.168.1.5", new AgentNode(9,"192.168.1.5", 9969, 4, 4));
        schedulePool.scheduleAtFixedRate(()->{
        	listenAgentAndObserver();
        }, 0, cleanNodePeriod, TimeUnit.SECONDS);
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		// 使用非缓冲区的buffer拷贝，然后转化为byte数组
        final byte[] data = Unpooled.copiedBuffer(msg.content()).array();
        final InetSocketAddress origin = msg.sender();
        byte[] assembleData = adapter.assemblePacket(data, origin);
        /* 本质上此处可以使用类名来区分业务，但是鉴于原有代码，现不改造 */
        if (assembleData != null) {
        	String content = new String(assembleData, "UTF-8");
        	int msgType = JSON.parseObject(content).getInteger("msgType");
        	LOG.info("receive message [type:{}] from {}.",msgType,origin);
        	//System.err.println("receive message [type:"+msgType+"] from " + origin);
        	switch (msgType) {
    		case EConstants.MsgType.RPC_AM_REGISTER://Agent注册
    		case EConstants.MsgType.RPC_HEARTBEAT://组件心跳
    			handleRegisterOrHeartBeat(content,origin);
    			break;
    		case EConstants.MsgType.RPC_AM_METRICS://Agent容器指标上报
    			handleCtnMetrics(content,origin);
    			break;
    		default:
    			break;
    		}
        }
		
	}
	
	
	/*********************************** 事件处理区 start  *************************************/
	/**
	 * 处理Agent注册消息和心跳为相同过程
	 * @param content
	 * @param origin
	 */
	private void handleRegisterOrHeartBeat(String content,InetSocketAddress origin){
		AgentNode worker = JSON.parseObject(content, AgentNode.class);
		if(null!=worker){
			//如果不包含说明是第一次注册，需要向Agent告知已收到注册信息
			if(!MessageCache.containsWorker(worker.getHost())){
				//告知注册节点
				adapter.sendData(JSON.toJSONString(node).getBytes(), origin);
				String path = EConstants.AGENT_ZK_PATH + node.getHost();
				try {
					//向注册节点回应并同步到zookeeper
					ZKClient.createAndWrite(path, JSON.toJSONString(node));
				} catch (Exception e) {
					LOG.error("sync agent node to zk error:{}",e.getMessage());
				}
			}
			worker.setLastUpdTime(System.currentTimeMillis());
			MessageCache.putWorker(worker.getHost(), worker);
		}
	}
	
	
	/**
	 * 处理Agent上报的容器指标信息
	 * @param content
	 * @param origin
	 */
	private void handleCtnMetrics(String content,InetSocketAddress origin){
		AgentMetrics agentMetrics = JSON.parseObject(content, AgentMetrics.class);
		LOG.debug("receive kpi msg from {}, msg:{}", origin, agentMetrics);

		/*AgentKpiHandler agentKpiHandler = AgentKpiHandler.getInstance();
		if (agentKpiHandler == null) {
			LOG.error("fail to get AgentKpiHandler");
			return;
		}

		RedisHostKpiBean redisHostKpiBean = agentKpiHandler.getHostKpiFromMsg(agentMetrics);
		List<RedisContainerMetrics> redisContainerMetricsList = agentKpiHandler.getCtnKpiFromMsg(agentMetrics);
		agentKpiHandler.storeKpiToRedis(redisHostKpiBean, redisContainerMetricsList);*/
	}
	

	/*********************************** 事件处理区 end  *************************************/
	
	/**
	 *   Manage内存中的数据存留到一点时间后需要定时清理
	 *   1.Agent数据定时清理
	 *   2.Observer数据
	 */
	public void listenAgentAndObserver(){
		
	  //Agent实现
	  Iterator<Entry<String, AgentNode>> itor = MessageCache.getAddressToAgent().entrySet().iterator();
	  while(itor.hasNext()){
		  Entry<String, AgentNode> entry = itor.next();
		  long lastUpdTime = entry.getValue().getLastUpdTime();
		  long idle = (System.currentTimeMillis()-lastUpdTime)/1000;
		  //空闲时间大于存活时间，说明已经死亡
		  if(idle>liveTime){
			 AgentNode worker = entry.getValue();
			 InetSocketAddress destination = new InetSocketAddress(worker.getHost(), worker.getPort());
			 //移除之前，主Manager将再次通知相应的Agent，并告知主Manager的地址，让agent和主manager保持心跳
			 adapter.sendData(JSON.toJSONString(node).getBytes(),destination);
			 //重发机制已经保证了会重试，如果后续还需要增加重发的次数进行多次尝试，后期优化
			 MessageCache.removeWorker(entry.getKey());
		  }
	  }
	  
	}
    
	
    // 关闭manager控制器
    public void stop() {
    	if(null!=schedulePool) schedulePool.shutdown();
    	if(ZKClient.isConnected())  ZKClient.close();
    	if(adapter!=null) adapter.stop();
    	MessageCache.cleanWorker();
    }
    
}
