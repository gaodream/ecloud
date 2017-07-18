package com.dream.home.category.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.ecloud.framework.model.ValueObject;

@Alias("CategoryVO")
public class CategoryVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private long rowId;	//主键
	private String categoryCode;	//分类编码
	private String categoryName;	//分类名称
	private long parentId;	//父级分类ID
	private String deletedFlag;	//删除标志
	private long createUserId;	//创建人ID
	private String createTime;	//创建时间
	private long lastOperatorId;	//最后操作人
	private String lastOperateTime;	//最后操作时间
	private String remark;	//备注

	public long getRowId(){
		return rowId;
	}
	public void setRowId(long rowId){
		this.rowId = rowId;
	}
	public String getCategoryCode(){
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode){
		this.categoryCode = categoryCode;
	}
	public String getCategoryName(){
		return categoryName;
	}
	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}
	public long getParentId(){
		return parentId;
	}
	public void setParentId(long parentId){
		this.parentId = parentId;
	}
	public String getDeletedFlag(){
		return deletedFlag;
	}
	public void setDeletedFlag(String deletedFlag){
		this.deletedFlag = deletedFlag;
	}
	public long getCreateUserId(){
		return createUserId;
	}
	public void setCreateUserId(long createUserId){
		this.createUserId = createUserId;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public long getLastOperatorId(){
		return lastOperatorId;
	}
	public void setLastOperatorId(long lastOperatorId){
		this.lastOperatorId = lastOperatorId;
	}
	public String getLastOperateTime(){
		return lastOperateTime;
	}
	public void setLastOperateTime(String lastOperateTime){
		this.lastOperateTime = lastOperateTime;
	}
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
}