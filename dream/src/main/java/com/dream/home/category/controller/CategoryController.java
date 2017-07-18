package com.dream.home.category.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dream.home.category.model.CategoryVO;
import com.dream.home.category.service.CategoryService;
import com.ecloud.framework.controller.BaseRestController;

@RestController
@RequestMapping("category")
public class CategoryController  extends BaseRestController<CategoryVO>{

	@Autowired
	private CategoryService categoryService;

	@Override
	public CategoryService getBaseService() {
		return this.categoryService;
	}

}
