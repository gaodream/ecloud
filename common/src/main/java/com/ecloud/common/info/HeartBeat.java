package com.ecloud.common.info;

public class HeartBeat {
	
	private String type;
    private String host;
    private Integer port;
    
    
    public HeartBeat() { }
    /**
     * 
     * @param type 组件类型
     * @param host 主机
     * @param port 端口：主要指rpc通信端口
     */
	public HeartBeat(String type,String host, Integer port) {
		this.type = type;
		this.host = host;
		this.port = port;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
    
}
