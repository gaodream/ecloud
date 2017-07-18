package com.ecloud.deploy.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 存储当前运行的服务信息
 * @author gaogao
 *
 */
public class AppInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String appId;
	private Integer lInstance; //之前容器数
	private Integer cInstance; //当前容器数
	private String lVersion; //之前服务版本号
	private String cVersion;//当前服务版本号
	private volatile String status = "NONE";
	private String timestamp;
	private Map<String,ContainerInfo> ctns;//该服务下的容器信息
	
	
	public AppInfo(String appId) {
		super();
		this.appId = appId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	
	
	public Integer getlInstance() {
		return lInstance;
	}
	public void setlInstance(Integer lInstance) {
		this.lInstance = lInstance;
	}
	public Integer getcInstance() {
		return cInstance;
	}
	public void setcInstance(Integer cInstance) {
		this.cInstance = cInstance;
	}
	public String getlVersion() {
		return lVersion;
	}
	public void setlVersion(String lVersion) {
		this.lVersion = lVersion;
	}
	public String getcVersion() {
		return cVersion;
	}
	public void setcVersion(String cVersion) {
		this.cVersion = cVersion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void addCtns(ContainerInfo ctn) {
		if(ctns==null) 
			ctns = new ConcurrentHashMap<String,ContainerInfo>();
		ctns.put(ctn.getTaskId(), ctn);
	}
	
	public Map<String, ContainerInfo> getCtns() {
		return ctns;
	}
	public void setCtns(Map<String, ContainerInfo> ctns) {
		this.ctns = ctns;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
}
