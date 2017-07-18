package com.dream.home.article.service;

import com.ecloud.framework.service.BaseService;

import java.util.List;

import com.dream.home.article.model.ArticleVO;

public interface ArticleService  extends BaseService<ArticleVO>{
	
	public List<ArticleVO> doSearchTop(ArticleVO vo);
	
	List<ArticleVO> doSearchHots();
}