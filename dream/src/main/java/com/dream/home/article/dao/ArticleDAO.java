package com.dream.home.article.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dream.home.article.model.ArticleVO;
import com.ecloud.framework.dao.BaseDAO;

@Mapper
public interface ArticleDAO extends BaseDAO<ArticleVO>{

	List<ArticleVO> doSearchTop(ArticleVO vo);
	
	List<ArticleVO> doSearchHots();
}