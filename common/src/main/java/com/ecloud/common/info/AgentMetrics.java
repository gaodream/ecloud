package com.ecloud.common.info;

import java.util.List;


public class AgentMetrics extends RequestMessage{

	private static final long serialVersionUID = 1L;
	
	private String host;
	private String hostName;

	// cpu使用率百分比
	private double totalCpu;
	private double usedCpu;
	private double freeCpu;
	
	//内存使用单位M
	private double totalMem;//内存总量
	private double usedMem;//当前内存使用量
	private double freeMem;//当前内存剩余量
	
	private double totalSwapMem;//交换区总量
	private double usedSwapMem;//当前交换区使用量
	private double freeSwapMem;//当前交换区剩余量
	
	//disk使用单位G
	private double totalDisk;
	private double usedDisk;
	
	//disk IO
	private double downDiskIO;
	private double upDiskIO;
	
	//带宽 K
	private double downNetIO;
	private double upNetIO;
	
	private double loadAvg;
	private long collectTime;

	
	private List<ContainerMetrics> containerList;

	
	public AgentMetrics() {
		this.collectTime = System.currentTimeMillis();
	}

	public double getDownDiskIO() {
		return downDiskIO;
	}

	public void setDownDiskIO(double downDiskIO) {
		this.downDiskIO = downDiskIO;
	}

	public double getUpDiskIO() {
		return upDiskIO;
	}

	public void setUpDiskIO(double upDiskIO) {
		this.upDiskIO = upDiskIO;
	}


	public double getFreeCpu() {
		return freeCpu;
	}

	public void setFreeCpu(double freeCpu) {
		this.freeCpu = freeCpu;
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

	public double getTotalCpu() {
		return totalCpu;
	}

	public void setTotalCpu(double totalCpu) {
		this.totalCpu = totalCpu;
	}

	public double getUsedCpu() {
		return usedCpu;
	}

	public void setUsedCpu(double usedCpu) {
		this.usedCpu = usedCpu;
	}

	public double getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(double totalMem) {
		this.totalMem = totalMem;
	}

	public double getUsedMem() {
		return usedMem;
	}

	public void setUsedMem(double usedMem) {
		this.usedMem = usedMem;
	}

	public double getFreeMem() {
		return freeMem;
	}

	public void setFreeMem(double freeMem) {
		this.freeMem = freeMem;
	}

	public double getTotalSwapMem() {
		return totalSwapMem;
	}

	public void setTotalSwapMem(double totalSwapMem) {
		this.totalSwapMem = totalSwapMem;
	}

	public double getUsedSwapMem() {
		return usedSwapMem;
	}

	public void setUsedSwapMem(double usedSwapMem) {
		this.usedSwapMem = usedSwapMem;
	}

	public double getFreeSwapMem() {
		return freeSwapMem;
	}

	public void setFreeSwapMem(double freeSwapMem) {
		this.freeSwapMem = freeSwapMem;
	}

	public double getTotalDisk() {
		return totalDisk;
	}

	public void setTotalDisk(double totalDisk) {
		this.totalDisk = totalDisk;
	}

	public double getUsedDisk() {
		return usedDisk;
	}

	public void setUsedDisk(double usedDisk) {
		this.usedDisk = usedDisk;
	}

	
	public double getDownNetIO() {
		return downNetIO;
	}

	public void setDownNetIO(double downNetIO) {
		this.downNetIO = downNetIO;
	}

	public double getUpNetIO() {
		return upNetIO;
	}

	public void setUpNetIO(double upNetIO) {
		this.upNetIO = upNetIO;
	}

	public double getLoadAvg() {
		return loadAvg;
	}

	public void setLoadAvg(double loadAvg) {
		this.loadAvg = loadAvg;
	}


	public List<ContainerMetrics> getContainerList() {
		return containerList;
	}

	public void setContainerList(List<ContainerMetrics> containerList) {
		this.containerList = containerList;
	}

	public long getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(long collectTime) {
		this.collectTime = collectTime;
	}
	

}
