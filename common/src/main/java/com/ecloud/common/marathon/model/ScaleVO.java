package com.ecloud.common.marathon.model;

import java.io.Serializable;
import java.util.List;

public class ScaleVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String marathonAppId;
	private Integer minCtnNum;//最小容器数
	private Integer scalePeriod;//扩缩容周期
	private Integer status;//自动扩缩容需要在特定的状态下
	private List<AutoScaleVO> autoList;//规则

	public ScaleVO(){}
	
	public ScaleVO(String marathonAppId,Integer status,Integer minCtnNum, Integer scalePeriod,List<AutoScaleVO> autoList) {
		this.marathonAppId = marathonAppId;
		this.status = status;
		this.minCtnNum = minCtnNum;
		this.scalePeriod = scalePeriod;
		this.autoList = autoList;
	}
	
	public String getMarathonAppId() {
		return marathonAppId;
	}

	public void setMarathonAppId(String marathonAppId) {
		this.marathonAppId = marathonAppId;
	}

	public Integer getMinCtnNum() {
		return minCtnNum;
	}

	public void setMinCtnNum(Integer minCtnNum) {
		this.minCtnNum = minCtnNum;
	}

	public Integer getScalePeriod() {
		return scalePeriod;
	}

	public void setScalePeriod(Integer scalePeriod) {
		this.scalePeriod = scalePeriod;
	}

	public List<AutoScaleVO> getAutoList() {
		return autoList;
	}

	public void setAutoList(List<AutoScaleVO> autoList) {
		this.autoList = autoList;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
