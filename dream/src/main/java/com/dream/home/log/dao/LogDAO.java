package com.dream.home.log.dao;

import com.ecloud.framework.dao.BaseDAO;

import org.apache.ibatis.annotations.Mapper;

import com.dream.home.log.model.LogVO;


@Mapper
public interface LogDAO extends BaseDAO<LogVO>{
}