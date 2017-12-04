package com.ecloud.syxf.home.article.service.impl;

import com.ecloud.framework.service.impl.BaseServiceImpl;
import com.ecloud.syxf.home.article.dao.ArticleDAO;
import com.ecloud.syxf.home.article.model.ArticleVO;
import com.ecloud.syxf.home.article.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("articleService")
public class ArticleServiceImpl extends BaseServiceImpl<ArticleVO> implements ArticleService {

	@Autowired
	private ArticleDAO articleDAO;

	@Override
	public ArticleDAO getBaseDAO() {
		return articleDAO;
	}
}