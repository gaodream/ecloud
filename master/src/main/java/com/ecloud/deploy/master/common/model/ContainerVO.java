package com.ecloud.deploy.master.common.model;

import java.io.Serializable;

import com.ecloud.framework.model.ValueObject;

public class ContainerVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String appId;	//关联到容器集群或者task组
	private String serviceId;	//服务id
	private String subServiceType;	//子服务类型
	private String marathonAppId;	//marathon的appid
	private String upgradeId;	//升级id
	private String taskId;	//taskId
	private String state;	//状态，1表示活跃容器信息，2表示容器统计信息，3表示死亡容器信息
	private String fragmentId;	//taskId或者docker容器id
	private String fragmentName;	//task名称或者docker容器名称
	private String appType;	//应用类型
	private String extraInfo;	//目前如果是docker容器，存放端口映射信息
	private String createTime;	//创建时间
	private String imageName;	//
	private String serviceState;	//服务状态，描述的是容器内部的具体应用的状态，主要和Command类型的健康检查结合使用，如果没有配置健康检查，该字段值为null；如果配置了健康检查，0-健康，1-异常，2-未知

	public String getAppId(){
		return appId;
	}
	public void setAppId(String appId){
		this.appId = appId;
	}
	public String getServiceId(){
		return serviceId;
	}
	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}
	public String getSubServiceType(){
		return subServiceType;
	}
	public void setSubServiceType(String subServiceType){
		this.subServiceType = subServiceType;
	}
	public String getMarathonAppId(){
		return marathonAppId;
	}
	public void setMarathonAppId(String marathonAppId){
		this.marathonAppId = marathonAppId;
	}
	public String getUpgradeId(){
		return upgradeId;
	}
	public void setUpgradeId(String upgradeId){
		this.upgradeId = upgradeId;
	}
	public String getTaskId(){
		return taskId;
	}
	public void setTaskId(String taskId){
		this.taskId = taskId;
	}
	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state = state;
	}
	public String getFragmentId(){
		return fragmentId;
	}
	public void setFragmentId(String fragmentId){
		this.fragmentId = fragmentId;
	}
	public String getFragmentName(){
		return fragmentName;
	}
	public void setFragmentName(String fragmentName){
		this.fragmentName = fragmentName;
	}
	public String getAppType(){
		return appType;
	}
	public void setAppType(String appType){
		this.appType = appType;
	}
	public String getExtraInfo(){
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo){
		this.extraInfo = extraInfo;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getImageName(){
		return imageName;
	}
	public void setImageName(String imageName){
		this.imageName = imageName;
	}
	public String getServiceState(){
		return serviceState;
	}
	public void setServiceState(String serviceState){
		this.serviceState = serviceState;
	}
}