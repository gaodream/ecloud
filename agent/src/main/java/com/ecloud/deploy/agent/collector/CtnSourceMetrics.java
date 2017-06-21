package com.ecloud.deploy.agent.collector;

import java.io.Serializable;

public class CtnSourceMetrics implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private double cpuUsage; //CPU使用率
	private double totalMem; //内存总量
	private double usedMem;//内存使用量
	private double memUsage;//内存使用率
	private double upNetIO; //上行带宽
	private double downNetIO;//下行带宽
	private double upDiskIO; //磁盘IO
	private double downDiskIO; //磁盘IO
	
	
	
	public CtnSourceMetrics() {
		super();
	}
	
	public CtnSourceMetrics(double cpuUsage, 
			double totalMem, double usedMem,  
			double upNetIO,double downNetIO, 
			double upDiskIO,double downDiskIO) {
		this.cpuUsage = cpuUsage;
		this.totalMem = totalMem;
		this.usedMem = usedMem;
		this.upNetIO = upNetIO;
		this.downNetIO = downNetIO;
		this.upDiskIO = upDiskIO;
		this.downDiskIO = downDiskIO;
	}

	public double getCpuUsage() {
		return cpuUsage;
	}

	public void setCpuUsage(double cpuUsage) {
		this.cpuUsage = cpuUsage;
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

	public double getMemUsage() {
		return memUsage;
	}

	public void setMemUsage(double memUsage) {
		this.memUsage = memUsage;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
	
}
