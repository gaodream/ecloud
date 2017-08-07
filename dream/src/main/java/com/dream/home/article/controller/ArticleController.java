package com.dream.home.article.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream.home.article.model.ArticleVO;
import com.dream.home.article.service.ArticleService;
import com.dream.home.category.service.CategoryService;
import com.dream.home.comment.model.CommentVO;
import com.dream.home.comment.service.CommentService;
import com.dream.home.link.model.LinkVO;
import com.dream.home.link.service.LinkService;
import com.dream.home.log.model.LogVO;
import com.dream.home.log.service.LogService;
import com.ecloud.framework.controller.BaseRestController;
import com.ecloud.framework.model.EResponse;

@RestController
@RequestMapping("/article/")
public class ArticleController  extends BaseRestController<ArticleVO>{

	@Autowired
	private ArticleService articleService;
	@Autowired
	private LinkService linkService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private LogService logService;

	@Override
	public ArticleService getBaseService() {
		return this.articleService;
	}
	
	@GetMapping("{cgCode}")
	public EResponse gotoCategory(@PathVariable String cgCode){
		EResponse result = EResponse.build();
		ArticleVO artVO = new ArticleVO();
		artVO.setCategoryCode(cgCode);
		result.setResult(articleService.doSearchPage(artVO));
		return result;
	}
	
	
	@PostMapping("list")
	public EResponse doSearchPage(@RequestBody ArticleVO artVO){
		EResponse result = EResponse.build();
		result.setResult(articleService.doSearchPage(artVO));
		return result;
	}
	
	@GetMapping("detail/{id}")
	public EResponse detail(@PathVariable int id){
		EResponse result = EResponse.build();
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("rowId", id);
		//更新浏览次数
		articleService.doUpdateByMap(param);
		result.setResult(articleService.doFindById(id));
		return result;
	}
	
	/**
	 * 博客统计
	 */
	@GetMapping("count")
	public EResponse count(){
		EResponse result = EResponse.build();
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("hots", articleService.doSearchHots());
		data.put("links", linkService.doSearchListByVO(new LinkVO()));
		data.put("comments", commentService.doSearchPage(new CommentVO(), 1, 10));
		data.put("browseCounts", logService.doSearchCount(new LogVO()));
		data.put("articleCounts", articleService.doSearchCount(new ArticleVO()));
		
		result.setResult(data);
		return result;
	}
	/*************************	后台部分	****************************/
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("gotoEdit")
	public EResponse gotoEdit(){
		EResponse result = EResponse.build();
		result.setResult(categoryService.doSearchListByVO(null));
		return result;
	}
	
	@GetMapping("publish")
	public String publish(){
		return "manager/articleEdit";
	}
	
	 @PostMapping("add")
	 public EResponse add(ArticleVO articleVO){
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
	 public EResponse articleList(@PathVariable String status){
		 EResponse result = EResponse.build("000000");
		 ArticleVO condition = new ArticleVO();
		 if(!StringUtils.equals(status.trim(), "A")){
			 condition.setStatus(status);
		 }
		 result.setResult(articleService.doSearchListByVO(condition));
		 return result;
	 }
	
}
