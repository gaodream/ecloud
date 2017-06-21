package com.ecloud.common.info;

public class MasterNode extends RequestMessage{
	
	private static final long serialVersionUID = 1L;
	
	public String id;
	public String  host;
	public Integer port;
	public Integer cores;
	public Integer memory;
	private Integer rpcPort;

	public MasterNode(String host,Integer rpcPort) {
		this.host = host;
		this.rpcPort = rpcPort;
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

	public Integer getRpcPort() {
		return rpcPort;
	}

	public void setRpcPort(Integer rpcPort) {
		this.rpcPort = rpcPort;
	}

}
