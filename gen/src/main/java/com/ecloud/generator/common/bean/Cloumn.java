package com.ecloud.generator.common.bean;

import java.io.Serializable;

public class Cloumn implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tableCataLog;
	private String tableSchema;
	private String tableName;
	private String columnName;
	private String orderPosition;
	private String columnDefault;
	private String isNullable;
	private String dataType;
	private String maxLength;
	private String numeric_precision;
	private String numeric_scale;
	private String comment;
	private String beanName;
	private String beanComment;
	private String beanType;
	
	public String getBeanType() {
		return beanType;
	}
	public void setBeanType(String beanType) {
		this.beanType = beanType;
	}
	public String getNumeric_precision() {
		return numeric_precision;
	}
	public void setNumeric_precision(String numeric_precision) {
		this.numeric_precision = numeric_precision;
	}
	public String getNumeric_scale() {
		return numeric_scale;
	}
	public void setNumeric_scale(String numeric_scale) {
		this.numeric_scale = numeric_scale;
	}
	public String getTableCataLog() {
		return tableCataLog;
	}
	public void setTableCataLog(String tableCataLog) {
		this.tableCataLog = tableCataLog;
	}
	public String getTableSchema() {
		return tableSchema;
	}
	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getOrderPosition() {
		return orderPosition;
	}
	public void setOrderPosition(String orderPosition) {
		this.orderPosition = orderPosition;
	}
	
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getColumnDefault() {
		return columnDefault;
	}
	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}
	public String getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(String maxLength) {
		this.maxLength = maxLength;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public String getBeanComment() {
		return beanComment;
	}
	public void setBeanComment(String beanComment) {
		this.beanComment = beanComment;
	}
	
	
	
}
