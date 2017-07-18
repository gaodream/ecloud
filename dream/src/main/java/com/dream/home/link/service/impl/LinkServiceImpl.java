package com.dream.home.link.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecloud.framework.service.impl.BaseServiceImpl;
import com.dream.home.link.dao.LinkDAO;
import com.dream.home.link.model.LinkVO;
import com.dream.home.link.service.LinkService;

@Service("linkService")
public class LinkServiceImpl extends BaseServiceImpl<LinkVO> implements LinkService {

	@Autowired
	private LinkDAO linkDAO;

	@Override
	public LinkDAO getBaseDAO() {
		return linkDAO;
	}
}