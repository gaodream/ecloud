package com.ecloud.common.info;

public class AgentNode extends RequestMessage{
	
	private static final long serialVersionUID = 1L;
	
	public String id;
	public String  host;
	public String  hostName;
	public Integer port;
	public Integer cores;
	public Integer memory;
	private Long lastUpdTime;
	
	public AgentNode(){}
	
	public AgentNode(String host, Integer port, Integer cores, Integer memory) {
		super();
		this.host = host;
		this.port = port;
		this.cores = cores;
		this.memory = memory;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

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

	public Long getLastUpdTime() {
		return lastUpdTime;
	}

	public void setLastUpdTime(Long lastUpdTime) {
		this.lastUpdTime = lastUpdTime;
	}
	

}
