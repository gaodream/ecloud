package com.ecloud.deploy.common.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("ContainerMetricBean")
public class ContainerMetricBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private long id;	//
	private long appId;	//应用ID
	private String marathonId;	//marathonID
	private String taskId;	//任务ID
	private String ctnId;	//容器ID
	private double avgCpu;	//平均使用率
	private double maxCpu;	//最大使用率
	private double minCpu;	//最小使用率
	private double limitCpu;	//总共CPU
	private double avgMem;	//内存平均使用
	private double maxMem;	//内存最大使用
	private double minMem;	//内存最小使用
	private double limitMem;	//内存总量
	private double avgDisk;	//已用磁盘
	private double maxDisk;	//已用磁盘
	private double minDisk;	//总共磁盘
	private double limitDisk;	//总共磁盘
	private double netDownIO;	//网络上行
	private double netUpIO;	//网络下行
	private long collectTime;	//采集时间
	private long insertTime;	//插入时间
	
	
	public String getMarathonId() {
		return marathonId;
	}
	public void setMarathonId(String marathonId) {
		this.marathonId = marathonId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCtnId() {
		return ctnId;
	}
	public void setCtnId(String ctnId) {
		this.ctnId = ctnId;
	}
	public double getAvgCpu() {
		return avgCpu;
	}
	public void setAvgCpu(double avgCpu) {
		this.avgCpu = avgCpu;
	}
	public double getMaxCpu() {
		return maxCpu;
	}
	public void setMaxCpu(double maxCpu) {
		this.maxCpu = maxCpu;
	}
	public double getMinCpu() {
		return minCpu;
	}
	public void setMinCpu(double minCpu) {
		this.minCpu = minCpu;
	}
	public double getLimitCpu() {
		return limitCpu;
	}
	public void setLimitCpu(double limitCpu) {
		this.limitCpu = limitCpu;
	}
	public double getAvgMem() {
		return avgMem;
	}
	public void setAvgMem(double avgMem) {
		this.avgMem = avgMem;
	}
	public double getMaxMem() {
		return maxMem;
	}
	public void setMaxMem(double maxMem) {
		this.maxMem = maxMem;
	}
	public double getMinMem() {
		return minMem;
	}
	public void setMinMem(double minMem) {
		this.minMem = minMem;
	}
	public double getLimitMem() {
		return limitMem;
	}
	public void setLimitMem(double limitMem) {
		this.limitMem = limitMem;
	}
	
	
	public double getAvgDisk() {
		return avgDisk;
	}
	public void setAvgDisk(double avgDisk) {
		this.avgDisk = avgDisk;
	}
	public double getMaxDisk() {
		return maxDisk;
	}
	public void setMaxDisk(double maxDisk) {
		this.maxDisk = maxDisk;
	}
	public double getMinDisk() {
		return minDisk;
	}
	public void setMinDisk(double minDisk) {
		this.minDisk = minDisk;
	}
	public double getLimitDisk() {
		return limitDisk;
	}
	public void setLimitDisk(double limitDisk) {
		this.limitDisk = limitDisk;
	}
	
	
	public double getNetDownIO() {
		return netDownIO;
	}
	public void setNetDownIO(double netDownIO) {
		this.netDownIO = netDownIO;
	}
	public double getNetUpIO() {
		return netUpIO;
	}
	public void setNetUpIO(double netUpIO) {
		this.netUpIO = netUpIO;
	}
	public long getCollectTime() {
		return collectTime;
	}
	public void setCollectTime(long collectTime) {
		this.collectTime = collectTime;
	}
	public long getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}

	
	
}