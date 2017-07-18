package com.ecloud.framework.service;

import java.util.List;
import java.util.Map;

import com.ecloud.framework.model.ValueObject;
import com.github.pagehelper.PageInfo;


public interface BaseService <T extends ValueObject> {
	
	public int doSearchCount(T VO);
	
	public int doInsertByVO(T vo);
	
	public int doBatchInsertByList(List<T> list);
	
	public int doInsertByMap(Map<String,Object> param);
	
	public int doDeleteById(long id);
	
	public int doDeleteByVO(T vo);
	
	public int doDeleteByMap(Map<String,Object> param);
	
	public int doUpdateByVO(T vo);
	
	public int doBatchUpdateByList(List<T> list);
	
	public int doUpdateByMap(Map<String,Object> param);

	public T doFindById(long id);
	
	public T doFindByVO(T bean);

	public List<T> doSearchListByVO(T vo);
	
	public List<T> doSearchListByMap(Map<String, Object> map);
	
	
	public PageInfo<T> doSearchPage(T vo) ;
	
	public PageInfo<T> doSearchPage(T vo,int pageNum, int pageSize) ;

	public PageInfo<T> doSearchPage(T vo,String orderStr,int pageNum, int pageSize);

	public PageInfo<T> doSearchPage(Map<String, Object> map,int pageNum, int pageSize);
	
	public PageInfo<T> doSearchPage(Map<String, Object> map, String orderStr,int pageNum, int pageSize);


}
