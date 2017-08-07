package com.ecloud.framework.model;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
import com.ecloud.framework.common.ErrorCodeUtil;

public class EResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public final static String SUCCESS = "00000000";
	public final static String FAIL = "11111111";

	private String respCode = SUCCESS ;
	private String respMsg = "";
	private Object result = "";
	
	public static EResponse build(){
		return new EResponse();
	}
	public static EResponse build(String respCode){
		 return new EResponse(respCode);
	}
	
	public static EResponse build(String respCode,Object result){
		return new EResponse(respCode,result);
	}
	
	private EResponse() {}
	
	private EResponse(String respCode) {
		this.respCode = respCode;
	}
	private EResponse(String respCode,Object result) {
		this.respCode = respCode;
		this.result = result;
	}
	
	public String toJson(){
		return JSONObject.toJSONString(this);
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
		this.respMsg =ErrorCodeUtil.getErrorDesc(respCode);
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

	
}
