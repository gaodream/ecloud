package com.dream.home.log.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dream.home.log.model.LogVO;
import com.dream.home.log.service.LogService;
import com.ecloud.framework.controller.BaseRestController;

@RestController
@RequestMapping("log")
public class LogController  extends BaseRestController<LogVO>{

	@Autowired
	private LogService logService;

	@Override
	public LogService getBaseService() {
		return this.logService;
	}

}
