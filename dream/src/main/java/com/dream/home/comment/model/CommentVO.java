package com.dream.home.comment.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.ecloud.framework.model.ValueObject;

@Alias("CommentVO")
public class CommentVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private long rowId;	//主键
	private String articleId;	//文章ID
	private String articleName;	//文章ID
	private String content;	//评论内容
	private long userId;	//用户ID
	private long parentId;	//上级评论（有回复时存在）
	private String deletedFlag;	//
	private long createUserId;	//创建人ID
	private String createTime;	//创建时间
	private String remark;	//备注

	private String categoryCode;//类别名称
	private String categoryName;//类别名称
	
	
	
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public long getRowId(){
		return rowId;
	}
	public void setRowId(long rowId){
		this.rowId = rowId;
	}
	public String getArticleId(){
		return articleId;
	}
	public void setArticleId(String articleId){
		this.articleId = articleId;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getUserId(){
		return userId;
	}
	public void setUserId(long userId){
		this.userId = userId;
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
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
	public String getArticleName() {
		return articleName;
	}
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}
	
	
}