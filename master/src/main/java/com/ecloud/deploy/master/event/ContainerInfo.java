package com.ecloud.deploy.master.event;

import java.io.Serializable;

public class ContainerInfo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String host;
	private String taskId;
	private String version;
	private String timestamp;
	private String taskStatus;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	

	
	
}
