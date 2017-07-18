package com.dream.home.link.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.ecloud.framework.model.ValueObject;

@Alias("LinkVO")
public class LinkVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private long rowId;	//
	private String linkName;	//
	private String linkUrl;	//

	public long getRowId(){
		return rowId;
	}
	public void setRowId(long rowId){
		this.rowId = rowId;
	}
	public String getLinkName(){
		return linkName;
	}
	public void setLinkName(String linkName){
		this.linkName = linkName;
	}
	public String getLinkUrl(){
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl){
		this.linkUrl = linkUrl;
	}
}