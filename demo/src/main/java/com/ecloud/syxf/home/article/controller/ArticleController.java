package com.ecloud.syxf.home.article.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ecloud.syxf.home.article.model.ArticleVO;
import com.ecloud.syxf.home.article.service.ArticleService;
import com.ecloud.framework.controller.BaseRestController;

@RestController
@RequestMapping("article")
public class ArticleController  extends BaseRestController<ArticleVO>{

	@Autowired
	private ArticleService articleService;

	@Override
	public ArticleService getBaseService() {
		return this.articleService;
	}

}
