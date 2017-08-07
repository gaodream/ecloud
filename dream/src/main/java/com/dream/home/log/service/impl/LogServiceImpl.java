package com.dream.home.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecloud.framework.service.impl.BaseServiceImpl;
import com.dream.home.log.dao.LogDAO;
import com.dream.home.log.model.LogVO;
import com.dream.home.log.service.LogService;

@Service("logService")
public class LogServiceImpl extends BaseServiceImpl<LogVO> implements LogService {

	@Autowired
	private LogDAO logDAO;
	
	@Override
	public LogDAO getBaseDAO() {
		return logDAO;
	}
}