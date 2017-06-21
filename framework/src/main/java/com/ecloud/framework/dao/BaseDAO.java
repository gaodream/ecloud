package com.ecloud.framework.dao;

import java.util.List;
import java.util.Map;

import com.ecloud.framework.model.ValueObject;

public interface BaseDAO<T extends  ValueObject> {
	
	public int doSearchCount(T VO);
	
	public int doInsertByVO(T VO);
	
	public int doBatchInsertByList(List<T> list);
	
	public int doInsertByMap(Map<String,Object> param);
	
	public int doDeleteById(long id);
	
	public int doDeleteByVO(T VO);
	
	public int doDeleteByMap(Map<String,Object> param);
	
	public int doUpdateByVO(T VO);
	
	public int doBatchUpdateByList(List<T> list);
	
	public int doUpdateByMap(Map<String,Object> param);
	
	public T doFindById(long id);
	
	public T doFindByVO(T VO);
	
	public List<T> doSearchListByVO(T VO);
	
	public List<T> doSearchListByMap(Map<String,Object> param);
	

}
