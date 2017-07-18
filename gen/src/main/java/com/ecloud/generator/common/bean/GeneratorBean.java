package com.ecloud.generator.common.bean;

import java.io.Serializable;
import java.util.List;

public class GeneratorBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String projectName = "";//"ecloud"; //
	private String modelName = "";//program"; //模块名称(小写)
	private String tableName = "";//"sm_program"; //表名称 --需要改
	private String basePath = "";//"com.ecloud.ecloud.common";
	private String prefix = "";//"smp"; //前缀 备用--可以为空
	private String genPath = "";//"D:/gen";//生成路径
	
	private List<Cloumn> cloumnList;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getGenPath() {
		return genPath;
	}

	public void setGenPath(String genPath) {
		this.genPath = genPath;
	}

	public List<Cloumn> getCloumnList() {
		return cloumnList;
	}

	public void setCloumnList(List<Cloumn> cloumnList) {
		this.cloumnList = cloumnList;
	}
	
	
	
	

}
