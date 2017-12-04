package com.ecloud.syxf.home.article.model;


import java.io.Serializable;
import com.ecloud.framework.model.ValueObject;

public class ArticleVO extends ValueObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private long rowId;	//主键
	private String articleName;	//文章名称
	private String categoryCode;	//所属分类
	private String tags;	//标签
	private String showImage;	//缩略图显示地址
	private String content;	//文章内容
	private Integer shareTimes;	//分享次数
	private Integer loveTimes;	//喜欢次数
	private Integer browseTimes;	//浏览次数
	private String author;	//作者
	private String status;	//P：发布状态；D:草稿状态
	private String toTop;	//置顶
	private String publishTime;	//发布时间
	private String deletedFlag;	//删除标志
	private long createUserId;	//创建人ID
	private String createTime;	//创建时间
	private long lastUpdId;	//最后操作人
	private String lastUpdTime;	//最后操作时间
	private String remark;	//备注

	public long getRowId(){
		return rowId;
	}
	public void setRowId(long rowId){
		this.rowId = rowId;
	}
	public String getArticleName(){
		return articleName;
	}
	public void setArticleName(String articleName){
		this.articleName = articleName;
	}
	public String getCategoryCode(){
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode){
		this.categoryCode = categoryCode;
	}
	public String getTags(){
		return tags;
	}
	public void setTags(String tags){
		this.tags = tags;
	}
	public String getShowImage(){
		return showImage;
	}
	public void setShowImage(String showImage){
		this.showImage = showImage;
	}
	public String getContent(){
		return content;
	}
	public void setContent(String content){
		this.content = content;
	}
	public Integer getShareTimes(){
		return shareTimes;
	}
	public void setShareTimes(Integer shareTimes){
		this.shareTimes = shareTimes;
	}
	public Integer getLoveTimes(){
		return loveTimes;
	}
	public void setLoveTimes(Integer loveTimes){
		this.loveTimes = loveTimes;
	}
	public Integer getBrowseTimes(){
		return browseTimes;
	}
	public void setBrowseTimes(Integer browseTimes){
		this.browseTimes = browseTimes;
	}
	public String getAuthor(){
		return author;
	}
	public void setAuthor(String author){
		this.author = author;
	}
	public String getStatus(){
		return status;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public String getToTop(){
		return toTop;
	}
	public void setToTop(String toTop){
		this.toTop = toTop;
	}
	public String getPublishTime(){
		return publishTime;
	}
	public void setPublishTime(String publishTime){
		this.publishTime = publishTime;
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
	public long getLastUpdId(){
		return lastUpdId;
	}
	public void setLastUpdId(long lastUpdId){
		this.lastUpdId = lastUpdId;
	}
	public String getLastUpdTime(){
		return lastUpdTime;
	}
	public void setLastUpdTime(String lastUpdTime){
		this.lastUpdTime = lastUpdTime;
	}
	public String getRemark(){
		return remark;
	}
	public void setRemark(String remark){
		this.remark = remark;
	}
}