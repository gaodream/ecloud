package com.ecloud.common.info;

import java.io.Serializable;

public class ContainerMetrics implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String ctnId;
	private String ctnName;
	private String ctnType;
	private String appId;
	private String serviceId;
	
	private String subservcieType;
	private String taskId;
	private String upgradeId;
	private String marathonId;
	private double cpuUsage;
	private double usedMem;
	private double totalMem;
	private double upNetIO; //网络上行IO
	private double downNetIO;//网络下行IO
	private double upDiskIO; //磁盘上行IO
	private double downDiskIO; //磁盘下行IO
	private long insertTime;
	
	public ContainerMetrics() {	}
	
	public ContainerMetrics(String ctnType) {
		this.ctnType = ctnType;
	}
	
	
	public String getUpgradeId() {
		return upgradeId;
	}

	public void setUpgradeId(String upgradeId) {
		this.upgradeId = upgradeId;
	}

	public String getCtnId() {
		return ctnId;
	}
	public void setCtnId(String ctnId) {
		this.ctnId = ctnId;
	}
	public String getCtnName() {
		return ctnName;
	}
	public void setCtnName(String ctnName) {
		this.ctnName = ctnName;
	}
	public String getCtnType() {
		return ctnType;
	}
	public void setCtnType(String ctnType) {
		this.ctnType = ctnType;
	}
	public String getAppId() {
		return appId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getSubservcieType() {
		return subservcieType;
	}
	public void setSubservcieType(String subservcieType) {
		this.subservcieType = subservcieType;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getMarathonId() {
		return marathonId;
	}
	public void setMarathonId(String marathonId) {
		this.marathonId = marathonId;
		this.appId =  marathonId.split("/")[2].split("-")[0];
		this.serviceId = marathonId.split("/")[2].split("-")[1];
		this.subservcieType = marathonId.split("/")[3];
		this.upgradeId = marathonId.split("/")[3].split("-")[1];
	}

	
	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
	}

	public double getUsedMem() {
		return usedMem;
	}

	public void setUsedMem(double usedMem) {
		this.usedMem = usedMem;
	}
	public long getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}


	public double getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(double totalMem) {
		this.totalMem = totalMem;
	}
	
	public void setAppId(String appId) {
		this.appId = appId;
	}

	public double getUpNetIO() {
		return upNetIO;
	}

	public void setUpNetIO(double upNetIO) {
		this.upNetIO = upNetIO;
	}

	public double getDownNetIO() {
		return downNetIO;
	}

	public void setDownNetIO(double downNetIO) {
		this.downNetIO = downNetIO;
	}

	public double getUpDiskIO() {
		return upDiskIO;
	}

	public void setUpDiskIO(double upDiskIO) {
		this.upDiskIO = upDiskIO;
	}

	public double getDownDiskIO() {
		return downDiskIO;
	}

	public void setDownDiskIO(double downDiskIO) {
		this.downDiskIO = downDiskIO;
	}
	
}
