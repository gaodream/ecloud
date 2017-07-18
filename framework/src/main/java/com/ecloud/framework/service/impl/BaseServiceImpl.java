package com.ecloud.framework.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ecloud.framework.dao.BaseDAO;
import com.ecloud.framework.model.ValueObject;
import com.ecloud.framework.service.BaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public abstract class BaseServiceImpl<T extends ValueObject>  implements BaseService<T>{

	
	public abstract BaseDAO<T> getBaseDAO();
	
	@Override
	public int doSearchCount(T vo){
		return getBaseDAO().doSearchCount(vo);
		
	}
	@Override
	public int doInsertByVO(T vo){
		return getBaseDAO().doInsertByVO(vo);
		
	}
	@Override
	public int doBatchInsertByList(List<T> list){
		return getBaseDAO().doBatchInsertByList(list);
		
	}
	
	@Override
	public int doInsertByMap(Map<String,Object> param){
		return getBaseDAO().doInsertByMap(param);
		
	}
	
	@Override
	public int doDeleteById(long id){
		return getBaseDAO().doDeleteById(id);
	}
	
	@Override
	public int doDeleteByVO(T VO){
		return getBaseDAO().doDeleteByVO(VO);
	}
	
	@Override
	public int doDeleteByMap(Map<String,Object> param){
		return getBaseDAO().doDeleteByMap(param);
	}
	
	@Override
	public int doUpdateByVO(T VO){
		return getBaseDAO().doUpdateByVO(VO);
	}
	
	@Override
	public int doBatchUpdateByList(List<T> list){
		return getBaseDAO().doBatchUpdateByList(list);
	}
	
	@Override
	public int doUpdateByMap(Map<String,Object> param){
		return getBaseDAO().doUpdateByMap(param);
	}

	@Override
	public T doFindById(long id){
		return getBaseDAO().doFindById(id);
	}
	
	@Override
	public T doFindByVO(T VO){
		return getBaseDAO().doFindByVO(VO);
	}

	@Override
	public List<T> doSearchListByVO(T vo){
		return getBaseDAO().doSearchListByVO(vo);
	}
	
	@Override
	public List<T> doSearchListByMap(Map<String, Object> map){
		return getBaseDAO().doSearchListByMap( map);
	}
	
	@Override
	public PageInfo<T> doSearchPage(T vo) {
		String orderStr = "";
		if(StringUtils.isNotEmpty(vo.getOrderName())){
			orderStr = vo.getOrderName() +" " +vo.getOrderRule();
		}
		return this.doSearchPage(vo, orderStr, vo.getPageNum(), vo.getPageSize());
	}
	
	@Override
	public PageInfo<T> doSearchPage(T vo,int pageNum, int pageSize) {
		String orderStr = "";
		if(StringUtils.isNotEmpty(vo.getOrderName())){
			orderStr = vo.getOrderName() +" " +vo.getOrderRule();
		}
		return this.doSearchPage(vo, orderStr, pageNum, pageSize);
	}
	
	
	@Override
	public PageInfo<T> doSearchPage(T vo,String orderStr,int pageNum, int pageSize) {
		if(!"".equals(orderStr.trim())){
			PageHelper.startPage(pageNum, pageSize,orderStr);
		}else{
			PageHelper.startPage(pageNum, pageSize);
		}
		List<T> list = this.getBaseDAO().doSearchListByVO(vo);
		PageInfo<T> page = new PageInfo<T>(list);
		return page;
	}
	
	
	@Override
	public PageInfo<T> doSearchPage(Map<String, Object> map,int pageNum, int pageSize)  {
		return this.doSearchPage(map, "", pageNum, pageSize);
	}
	
	@Override
	public PageInfo<T> doSearchPage(Map<String, Object> map,String orderStr,int pageNum, int pageSize) {
		if(!"".equals(orderStr.trim())){
			PageHelper.startPage(pageNum, pageSize,orderStr);
		}else{
			PageHelper.startPage(pageNum, pageSize);
		}
		List<T> list = this.getBaseDAO().doSearchListByMap(map);
		PageInfo<T> page = new PageInfo<T>(list);
		return page;
	}


}
