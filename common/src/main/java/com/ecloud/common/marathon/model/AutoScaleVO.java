package com.ecloud.common.marathon.model;

import java.io.Serializable;


public class AutoScaleVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String policyName;
	private String policyAliasName;
	private String scaleFlag;
	private String paramName;
	private String paramAliasName;
	private float paramValue;
	private String operation;
	
	
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyAliasName() {
		return policyAliasName;
	}
	public void setPolicyAliasName(String policyAliasName) {
		this.policyAliasName = policyAliasName;
	}
	public String getScaleFlag() {
		return scaleFlag;
	}
	public void setScaleFlag(String scaleFlag) {
		this.scaleFlag = scaleFlag;
	}
	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getParamAliasName() {
		return paramAliasName;
	}
	public void setParamAliasName(String paramAliasName) {
		this.paramAliasName = paramAliasName;
	}
	public float getParamValue() {
		return paramValue;
	}
	public void setParamValue(float paramValue) {
		this.paramValue = paramValue;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	
	
}