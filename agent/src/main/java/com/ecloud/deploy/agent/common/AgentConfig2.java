package com.ecloud.deploy.agent.common;

/**
 * @author gaogao
 * 静态文件支持热加载
 */
public class AgentConfig2 {
	
	
    private String host;
    private String hostName;
    private int udpPort;//agent开启udp绑定的端口9969
    private String activeMaster; //从zk中获取
    private String masters; //从zk中获取
    private int masterPort;//从zk中获取 UDP端口
    private String dockerServer;//访问的docker服务程序的url:dockerServerUrl=unix:///var/run/docker.sock
    private int collectPeriod;//采集周期，单位为秒15
    private int heartBeatPeriod;//心跳的周期，单位为秒15
    private int containerStatusPeriod;//#容器状态上报周期，单位为秒
    private int maxPerRouteConnections;//#发送消息直到接收相应的超时时间，单位为秒
    private int maxTotalConnections;//#发送消息直到接收相应的超时时间，单位为秒
  
    private int timeOut = 20;//  发送消息直到接收相应的超时时间，单位为秒
    private String storePath;//容器信息持久化的本地目录 /home/ecloud/agent/store
    
    private String agentConfPath; //启动时输入
    
    private int tryRegisterPeriod;
    
    private Integer cores;
    private Integer memory;    

    
	public Integer getCores() {
		return cores;
	}

	public void setCores(Integer cores) {
		this.cores = cores;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public String getActiveMaster() {
		return activeMaster;
	}

	public void setActiveMaster(String activeMaster) {
		this.activeMaster = activeMaster;
	}

	public String getMasters() {
		return masters;
	}

	public void setMasters(String masters) {
		this.masters = masters;
	}

	public int getTryRegisterPeriod() {
		return tryRegisterPeriod;
	}

	public void setTryRegisterPeriod(int tryRegisterPeriod) {
		this.tryRegisterPeriod = tryRegisterPeriod;
	}

	public int getContainerStatusPeriod() {
		return containerStatusPeriod;
	}

	public void setContainerStatusPeriod(int containerStatusPeriod) {
		this.containerStatusPeriod = containerStatusPeriod;
	}

	public String getAgentConfPath() {
		return agentConfPath;
	}

	public void setAgentConfPath(String agentConfPath) {
		this.agentConfPath = agentConfPath;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public int getUdpPort() {
		return udpPort;
	}

	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}

	

	public int getMasterPort() {
		return masterPort;
	}

	public void setMasterPort(int masterPort) {
		this.masterPort = masterPort;
	}

	public String getDockerServer() {
		return dockerServer;
	}

	public void setDockerServer(String dockerServer) {
		this.dockerServer = dockerServer;
	}

	public int getCollectPeriod() {
		return collectPeriod;
	}

	public void setCollectPeriod(int collectPeriod) {
		this.collectPeriod = collectPeriod;
	}

	public int getHeartBeatPeriod() {
		return heartBeatPeriod;
	}

	public void setHeartBeatPeriod(int heartBeatPeriod) {
		this.heartBeatPeriod = heartBeatPeriod;
	}

	public int getMaxPerRouteConnections() {
		return maxPerRouteConnections;
	}

	public void setMaxPerRouteConnections(int maxPerRouteConnections) {
		this.maxPerRouteConnections = maxPerRouteConnections;
	}

	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getStorePath() {
		return storePath;
	}

	public void setStorePath(String storePath) {
		this.storePath = storePath;
	}

    
}
