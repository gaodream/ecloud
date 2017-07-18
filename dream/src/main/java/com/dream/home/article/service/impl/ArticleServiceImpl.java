package com.dream.home.article.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecloud.framework.service.impl.BaseServiceImpl;
import com.dream.home.article.dao.ArticleDAO;
import com.dream.home.article.model.ArticleVO;
import com.dream.home.article.service.ArticleService;

@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<ArticleVO> implements ArticleService {

	@Autowired
	private ArticleDAO articleDAO;

	@Override
	public ArticleDAO getBaseDAO() {
		return articleDAO;
	}

	@Override
	public List<ArticleVO> doSearchTop(ArticleVO vo) {
		return articleDAO.doSearchTop(vo);
	}
	
	@Override
	public List<ArticleVO> doSearchHots() {
		return articleDAO.doSearchHots();
	}
}