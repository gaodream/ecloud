package com.dream.home.category.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecloud.framework.service.impl.BaseServiceImpl;
import com.dream.home.category.dao.CategoryDAO;
import com.dream.home.category.model.CategoryVO;
import com.dream.home.category.service.CategoryService;

@Service("categoryService")
public class CategoryServiceImpl extends BaseServiceImpl<CategoryVO> implements CategoryService {

	@Autowired
	private CategoryDAO categoryDAO;

	@Override
	public CategoryDAO getBaseDAO() {
		return categoryDAO;
	}
}