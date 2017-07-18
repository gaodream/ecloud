package com.ecloud.framework.model;

import java.io.Serializable;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;


public abstract class ValueObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private int pageSize = 10;// ==pageSize
	private int pageNum = 1;
	private String orderName;
	private String orderRule;


	
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderRule() {
		return orderRule;
	}

	public void setOrderRule(String orderRule) {
		this.orderRule = orderRule;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Map<String, Object> getCustomCondition() {
		return null;
	}

	public String toJson(){
		return JSONObject.toJSONString(this);
	}

}
