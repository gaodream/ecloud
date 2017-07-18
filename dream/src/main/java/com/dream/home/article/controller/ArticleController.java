package com.dream.home.article.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dream.home.article.model.ArticleVO;
import com.dream.home.article.service.ArticleService;
import com.dream.home.category.service.CategoryService;
import com.ecloud.framework.common.EResponse;
import com.ecloud.framework.controller.BaseRestController;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/article/")
public class ArticleController  extends BaseRestController<ArticleVO>{

	@Autowired
	private ArticleService articleService;

	@Override
	public ArticleService getBaseService() {
		return this.articleService;
	}
	
	@GetMapping("{cgCode}")
	public ModelAndView gotoCategory(@PathVariable String cgCode){
		ModelAndView mav = new ModelAndView();
		mav.addObject(cgCode,"nav-this");
		if("about".equals(cgCode.trim())){
			mav.setViewName("views/about");
		}else{
			ArticleVO artVO = new ArticleVO();
			artVO.setCategoryCode(cgCode);
			PageInfo<ArticleVO> page = articleService.doSearchPage(artVO);
			mav.addObject("page", page);
			mav.addObject("cgCode", cgCode);
			mav.setViewName("views/article");
		}
		return mav;
	}
	
	
	@PostMapping("list")
	public ModelAndView doSearchPage(ArticleVO artVO){
		ModelAndView mav = new ModelAndView();
		String cgCode = artVO.getCategoryCode();
		mav.addObject(cgCode,"nav-this");
		mav.addObject("cgCode", cgCode);
		PageInfo<ArticleVO> page = articleService.doSearchPage(artVO);
		mav.addObject("page", page);
		mav.setViewName("views/article");
		return mav;
	}
	
	@GetMapping("detail/{id}")
	public ModelAndView detail(@PathVariable int id){
		ModelAndView mav = new ModelAndView("views/detail");
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("rowId", id);
		//更新浏览次数
		articleService.doUpdateByMap(param);
		mav.addObject("article", articleService.doFindById(id));
		return mav;
	}
	/*************************	后台部分	****************************/
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("gotoEdit")
	public ModelAndView gotoEdit(){
		ModelAndView mav = new ModelAndView("manager/articleEdit");
		mav.addObject("clist", categoryService.doSearchListByVO(null));
		return mav;
	}
	@GetMapping("publish")
	public String publish(){
		return "manager/articleEdit";
	}
	
	 @PostMapping("add")
	 public @ResponseBody EResponse add(ArticleVO articleVO){
		 EResponse result = EResponse.build("000000");
		 try{
			 articleService.doInsertByVO(articleVO);
		 }catch(Exception e){
			 e.printStackTrace();
			 result.setRespCode("200001");
		 }
		 return result;
	 }
	
	 /**
	  * 
	  * @param status A:全部文章;P：发布文章D:草稿
	  * @return
	  */
	 @GetMapping("/list/{status}")
	 public ModelAndView articleList(@PathVariable String status){
		 ModelAndView mav = new ModelAndView("manager/articleList");
		 ArticleVO condition = new ArticleVO();
		 if(!StringUtils.equals(status.trim(), "A")){
			 condition.setStatus(status);
		 }
		 List<ArticleVO> list = articleService.doSearchListByVO(condition);
		 mav.addObject("artlist", list);
		 return mav;
	 }
	
}
