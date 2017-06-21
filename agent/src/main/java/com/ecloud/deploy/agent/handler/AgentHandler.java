package com.ecloud.deploy.agent.handler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ecloud.common.constant.EConstants;
import com.ecloud.common.info.AgentMetrics;
import com.ecloud.common.info.AgentNode;
import com.ecloud.common.info.HeartBeat;
import com.ecloud.common.info.MasterNode;
import com.ecloud.common.rpc.ThreadFactoryUtil;
import com.ecloud.common.rpc.udp.NettyEndpoint;
import com.ecloud.common.zookeeper.ZKClient;
import com.ecloud.deploy.agent.collector.AgentCollector;
import com.ecloud.deploy.agent.common.AgentConfig;
import com.ecloud.deploy.agent.common.EDockerClient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

public class AgentHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public  final static Logger LOG = LoggerFactory.getLogger(AgentHandler.class);
    
    private AgentConfig config;//agent配置
    
    private static ScheduledExecutorService schedulePool = null;
    private ScheduledFuture<?> registerFuture ;
    private AtomicBoolean registered = new AtomicBoolean(false); // 更新标志
    private ReentrantLock lock = new ReentrantLock();  //更新锁
    
	private String host;
	private int port;
	
	private List<MasterNode> masters = new ArrayList<MasterNode>();
	private InetSocketAddress activeMaster;

	private NettyEndpoint endpoint;
	private EDockerClient dockerClient;
	private AgentCollector collector;
	private AgentNode agentNode; 
	
    public AgentHandler(NettyEndpoint endpoint) {
        LOG.debug("******************Agent init **********************");
        this.config = AgentConfig.getInstance();
		this.endpoint = endpoint;
        this.host = config.getString(AgentConfig.CURRENT_HOST_IP);
        this.port = config.getInteger("ecloud_agent_rpc_port",9969);
        schedulePool = Executors.newScheduledThreadPool(5,ThreadFactoryUtil.create("Agent-%d"));
        
        dockerClient = EDockerClient.getInstance(
        		config.getInteger("ecloud_agent_docker_maxPerRouteConnections", 100),
        		config.getInteger("ecloud_agent_docker_maxTotalConnections", 1000),
        		config.getString("ecloud_agent_docker_server", "unix:///var/run/docker.sock"));
        
        collector = new AgentCollector(config,dockerClient);
        
        agentNode  = new AgentNode(host,port,
    			config.getInteger(AgentConfig.CURRENT_HOST_CORES,0),
    			config.getInteger(AgentConfig.CURRENT_HOST_MEMORY,0)
    			);
        
    }
    
    public void start(){
    	endpoint.start();
    	registToMaster();
    }
    
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
		// 使用非缓冲区的buffer拷贝，然后转化为byte数组
        final byte[] data = Unpooled.copiedBuffer(msg.content()).array();
        final InetSocketAddress origin = msg.sender();
        byte[] assembleData = endpoint.assemblePacket(data, origin);
        /* 本质上此处可以使用类名来区分业务，但是鉴于原有代码，现不改造 */
        if (assembleData != null) {
        	String content = new String(assembleData, "UTF-8");
        	int msgType = JSON.parseObject(content).getInteger("msgType");
        	LOG.info("receive message [type:{}] from {} .",msgType,origin);
        	System.err.println("receive message [type:"+msgType+"] from " + origin);
        	//不同的类型做不同的处理
        	switch (msgType) {
	        	case EConstants.MsgType.RPC_REG_RESP:
	    			handleRegisterResponse(new String(assembleData, "UTF-8"));
	    			break;
	    		default:
	    			LOG.info("Agent do not know MsgType[{}].",msgType);
	    			break;
    		}
        }
		
	}
	
    //注册给Master
    private void registToMaster() {
    	LOG.info("begin to register with {} and {}",host, port);
        //1.发送心跳信息
        registerFuture = schedulePool.scheduleAtFixedRate(() ->  {
 			if(!registered.get()){
 				LOG.info("agent retry register Manager ...");
 				if(masters.isEmpty()){
 		    		masters = getMasterFromZK();
 		    		System.err.println(JSON.toJSONString(masters));
 		    	}
 				for(MasterNode master : masters){
 		    		try {
 		    			LOG.info(master+"="+JSON.toJSONString(agentNode));
 		    			endpoint.sendData(JSON.toJSONString(agentNode).getBytes(), 
 								new InetSocketAddress(master.getHost(),master.getRpcPort()));
 					} catch (Exception e) {
 						LOG.error("Send register info error: {}",e.getMessage());
 					}
 		    	}
 			}
          },0,config.getInteger("ecloud_agent_register_period", 30), TimeUnit.SECONDS);
    }
    
    /**********************************Master的事件处理 start******************************************/
    
    /**
     * 1.注册收到Master的响应详细后
     * 2.Master发生切换会再次响应Agent，本质就是为了让Agent拿到最新的Master地址
     */
    private void handleRegisterResponse(String content) throws Exception {
    	MasterNode master = JSON.parseObject(content, MasterNode.class);
    	//1.记录Master携带过来的IP地址信息
    	LOG.info("begin to handleRegisterResponse with {} and {}",master.getHost(), config.getString("ecloud_agent_rpc_port"));
    	if(null!=registerFuture&&!registerFuture.isCancelled()){
			registerFuture.cancel(true);
		}
    	config.addProperty("ecloud_active_master", master.getHost()+":"+master.getRpcPort());
    	activeMaster = new InetSocketAddress(master.getHost(), master.getRpcPort());
    	if(!registered.get()){
    		//说明是初次注册的响应
    		registered.set(true);
    		//启动采集器的执行器:开始启动心跳、上报指标，上报容器状态
    		launchExecutor();
    	}
    }
    
    //启动执行器
    private void launchExecutor(){
    	//1.发送心跳
    	LOG.debug("1.Begin to send HeartBeat to Master ......");
    	schedulePool.scheduleWithFixedDelay(() ->  {
	    		try {
	    			sendMsg(new HeartBeat(EConstants.COMP_TYPE.AGENT,host, port), true);
	    		} catch (Exception e) {
	    			LOG.error("HeartBeat Error.");
	    		}
    	},0,config.getInteger("ecloud_agent_heartbeat_period",30) , TimeUnit.SECONDS);
    	//2.上报主机和容器指标
    	LOG.debug("2.Begin to report metrics to Master ......");
    	schedulePool.scheduleAtFixedRate(() ->  {
    		try {
    			AgentMetrics metrics = collector.doCollect();
    			if(null!=metrics){
    				sendMsg(metrics, true);
    			}
    		} catch (Exception e) {
    			LOG.error("Send Metrics Error.");
    		}
    	},0, config.getInteger("ecloud_agent_collect_period",30), TimeUnit.SECONDS);
    	
    	/*//3.上报容器状态
    	LOG.debug("3.Begin to report container status to Master ......");
    	schedulePool.scheduleAtFixedRate(() ->  {
    		try {
    			List<ContainerStatusBean> ctnList = messageManager.getContainerList();
    			if(!ctnList.isEmpty()){
    				sendMsg(new ContainerStatusMsg(ctnList), true);
    			}
    		} catch (Exception e) {
    			LOG.error("Send container status Error.{}",e.getMessage());
    		}
    	},0,config.getInteger("ecloud_agent_ctnstatus_period,30), TimeUnit.SECONDS);*/
    }
    

    /**********************************Master的事件处理 end******************************************/

    /**
     * 发送消息
     * @param msg        消息
     * @param repeatFlag 重发标识
     * @throws Exception
     */
    private void sendMsg(Object msg, boolean repeatFlag)  {
    	LOG.info("send data:{}", JSON.toJSONString(msg));
        try {
            lock.lock();
            endpoint.sendData(JSON.toJSONString(msg).getBytes(), activeMaster);
        } catch(Exception e){
        	LOG.error("send data error:{}", JSON.toJSONString(msg));
        }finally {
            lock.unlock();
        }
    }

    public void stop() {
    	try{
	    	if(null!=endpoint) endpoint.stop();
	    	if(null!=schedulePool)  schedulePool.shutdown();
    	}catch(Exception e){
    		LOG.error("stop error:{}", e.getMessage());
    	}
    }
    
    
    private List<MasterNode> getMasterFromZK(){
        List<MasterNode> masters = new ArrayList<MasterNode>();
		try {
			boolean result = ZKClient.connect(config.getString("ecloud.agent.zookeeper.connection.urls"),
					config.getInteger("ecloud.agent.zookeeper.connection.timeout",30000));
			if(result){
				List<String> list = ZKClient.getChildrenFullPath(EConstants.MASTER_ZK_PATH);
				for(String path : list){
					MasterNode node = JSON.parseObject(ZKClient.getData(path),MasterNode.class);
					masters.add(node);
				}
			}
		} catch (Exception e) {
			LOG.error("First to get Master error :{}",e.getMessage());
			System.out.println(e.getMessage());
			return masters;
		}finally {
			ZKClient.close();
		}
        return masters;
	}

    public static void main(String[] args) {
    	schedulePool = Executors.newScheduledThreadPool(5,ThreadFactoryUtil.create("Agent-%d"));
    	schedulePool.scheduleAtFixedRate(() ->  {
	    	try {
				boolean result = ZKClient.connect("192.168.6.110:2181,192.168.6.111:2181,192.168.6.112:2181",30000);
				if(result){
					List<String> list = ZKClient.getChildrenFullPath(EConstants.MASTER_ZK_PATH);
					for(String path : list){
						MasterNode node = JSON.parseObject(ZKClient.getData(path),MasterNode.class);
						System.out.println(JSON.toJSONString(node));
					}
				}
			} catch (Exception e) {
				LOG.error("First to get Master error :{}",e.getMessage());
				System.out.println(e.getMessage());
			}finally {
				ZKClient.close();
			}
    	},0,5, TimeUnit.SECONDS);
    	
	}
}
