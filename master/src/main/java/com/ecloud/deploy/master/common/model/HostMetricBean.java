package com.ecloud.deploy.master.common.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("HostMetricBean")
public class HostMetricBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private long hhkId;	//
	private String hhkHostIp;	//服务ID
	private String hhkHostName;	//任务ID
	private double hhkAvgCpu;	//平均使用率
	private double hhkMaxCpu;	//最大使用率
	private double hhkMinCpu;	//最小使用率
	private double hhkAvgMem;	//内存平均使用
	private double hhkMaxMem;	//内存最大使用
	private double hhkMinMem;	//内存最小使用
	private double hhkTotalMem;	//内存总量
	private double hhkAvgLoad;	//平均负载
	private double hhkMaxLoad;	//最大负载
	private double hhkMinLoad;	//最小负载
	private double hhkUsedDisk;	//已用磁盘
	private double hhkTotalDisk;	//总共磁盘
	private double hhkNetDownIo;	//网络上行
	private double hhkNetUpIo;	//网络下行
	private String hhkCollectTime;	//采集时间
	private String hhkInsertTime;	//插入时间

	public long getHhkId(){
		return hhkId;
	}
	public void setHhkId(long hhkId){
		this.hhkId = hhkId;
	}
	public String getHhkHostIp(){
		return hhkHostIp;
	}
	public void setHhkHostIp(String hhkHostIp){
		this.hhkHostIp = hhkHostIp;
	}
	public String getHhkHostName(){
		return hhkHostName;
	}
	public void setHhkHostName(String hhkHostName){
		this.hhkHostName = hhkHostName;
	}
	public double getHhkAvgCpu(){
		return hhkAvgCpu;
	}
	public void setHhkAvgCpu(double hhkAvgCpu){
		this.hhkAvgCpu = hhkAvgCpu;
	}
	public double getHhkMaxCpu(){
		return hhkMaxCpu;
	}
	public void setHhkMaxCpu(double hhkMaxCpu){
		this.hhkMaxCpu = hhkMaxCpu;
	}
	public double getHhkMinCpu(){
		return hhkMinCpu;
	}
	public void setHhkMinCpu(double hhkMinCpu){
		this.hhkMinCpu = hhkMinCpu;
	}
	public double getHhkAvgMem(){
		return hhkAvgMem;
	}
	public void setHhkAvgMem(double hhkAvgMem){
		this.hhkAvgMem = hhkAvgMem;
	}
	public double getHhkMaxMem(){
		return hhkMaxMem;
	}
	public void setHhkMaxMem(double hhkMaxMem){
		this.hhkMaxMem = hhkMaxMem;
	}
	public double getHhkMinMem(){
		return hhkMinMem;
	}
	public void setHhkMinMem(double hhkMinMem){
		this.hhkMinMem = hhkMinMem;
	}
	public double getHhkTotalMem(){
		return hhkTotalMem;
	}
	public void setHhkTotalMem(double hhkTotalMem){
		this.hhkTotalMem = hhkTotalMem;
	}
	public double getHhkAvgLoad(){
		return hhkAvgLoad;
	}
	public void setHhkAvgLoad(double hhkAvgLoad){
		this.hhkAvgLoad = hhkAvgLoad;
	}
	public double getHhkMaxLoad(){
		return hhkMaxLoad;
	}
	public void setHhkMaxLoad(double hhkMaxLoad){
		this.hhkMaxLoad = hhkMaxLoad;
	}
	public double getHhkMinLoad(){
		return hhkMinLoad;
	}
	public void setHhkMinLoad(double hhkMinLoad){
		this.hhkMinLoad = hhkMinLoad;
	}
	public double getHhkUsedDisk(){
		return hhkUsedDisk;
	}
	public void setHhkUsedDisk(double hhkUsedDisk){
		this.hhkUsedDisk = hhkUsedDisk;
	}
	public double getHhkTotalDisk(){
		return hhkTotalDisk;
	}
	public void setHhkTotalDisk(double hhkTotalDisk){
		this.hhkTotalDisk = hhkTotalDisk;
	}
	public double getHhkNetDownIo(){
		return hhkNetDownIo;
	}
	public void setHhkNetDownIo(double hhkNetDownIo){
		this.hhkNetDownIo = hhkNetDownIo;
	}
	public double getHhkNetUpIo(){
		return hhkNetUpIo;
	}
	public void setHhkNetUpIo(double hhkNetUpIo){
		this.hhkNetUpIo = hhkNetUpIo;
	}
	public String getHhkCollectTime(){
		return hhkCollectTime;
	}
	public void setHhkCollectTime(String hhkCollectTime){
		this.hhkCollectTime = hhkCollectTime;
	}
	public String getHhkInsertTime(){
		return hhkInsertTime;
	}
	public void setHhkInsertTime(String hhkInsertTime){
		this.hhkInsertTime = hhkInsertTime;
	}
}