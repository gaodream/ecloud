package com.dream.home.log.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.ecloud.framework.model.ValueObject;

@Alias("LogVO")
public class LogVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private long rowId;	//主键
	private String requestUrl;	//请求的URL
	private String requestIp;	//请求的IP
	private String methodName;	//方法名称
	private String methodArgs;	//方法参数
	private String requestTime;	//请求时间

	public long getRowId(){
		return rowId;
	}
	public void setRowId(long rowId){
		this.rowId = rowId;
	}
	public String getRequestUrl(){
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl){
		this.requestUrl = requestUrl;
	}
	public String getRequestIp(){
		return requestIp;
	}
	public void setRequestIp(String requestIp){
		this.requestIp = requestIp;
	}
	public String getMethodName(){
		return methodName;
	}
	public void setMethodName(String methodName){
		this.methodName = methodName;
	}
	public String getMethodArgs(){
		return methodArgs;
	}
	public void setMethodArgs(String methodArgs){
		this.methodArgs = methodArgs;
	}
	public String getRequestTime(){
		return requestTime;
	}
	public void setRequestTime(String requestTime){
		this.requestTime = requestTime;
	}
}