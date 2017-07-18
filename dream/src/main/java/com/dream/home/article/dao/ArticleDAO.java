package com.dream.home.article.dao;

import com.ecloud.framework.dao.BaseDAO;

import java.util.List;

import com.dream.home.article.model.ArticleVO;

public interface ArticleDAO extends BaseDAO<ArticleVO>{

	List<ArticleVO> doSearchTop(ArticleVO vo);
	
	List<ArticleVO> doSearchHots();
}