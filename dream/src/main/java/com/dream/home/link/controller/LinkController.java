package com.dream.home.link.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dream.home.link.model.LinkVO;
import com.dream.home.link.service.LinkService;
import com.ecloud.framework.controller.BaseRestController;

@RestController
@RequestMapping("link")
public class LinkController  extends BaseRestController<LinkVO>{

	@Autowired
	private LinkService linkService;

	@Override
	public LinkService getBaseService() {
		return this.linkService;
	}

}
