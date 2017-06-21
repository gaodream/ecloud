package com.ecloud.framework.service.impl;

import java.util.List;
import java.util.Map;

import com.ecloud.framework.dao.BaseDAO;
import com.ecloud.framework.model.ValueObject;
import com.ecloud.framework.service.BaseService;

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
	



}
