package com.dream.home.main.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream.home.article.model.ArticleVO;
import com.dream.home.article.service.ArticleService;
import com.dream.home.category.model.CategoryVO;
import com.dream.home.category.service.CategoryService;
import com.dream.home.comment.model.CommentVO;
import com.dream.home.comment.service.CommentService;
import com.ecloud.framework.model.EResponse;

@RestController
public class MainController {
	
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/")
	public EResponse index(Map<String,Object> map){
		EResponse result = EResponse.build();
		ArticleVO artVO = new ArticleVO();
		map.put("artCount", articleService.doSearchCount(artVO));
		artVO.setToTop("N");
		map.put("artList", articleService.doSearchPage(artVO).getList());
		artVO.setToTop("Y");
		map.put("topList", articleService.doSearchPage(artVO,1,2).getList());
		
		CommentVO commentVO = new CommentVO();
		map.put("commentCount", commentService.doSearchCount(commentVO));
		map.put("cgCount", categoryService.doSearchCount(new CategoryVO()));
		result.setResult(map);
		return result;
	}
	
/*	@GetMapping("/")
	public @ResponseBody Map index(){
		ModelAndView mav = new ModelAndView("index");
		ArticleVO condition = new ArticleVO();
		List<ArticleVO> list = articleService.doSearchListByVO(condition);
		System.out.println(JSON.toJSON(list));
		mav.addObject("articleNum", list);
		mav.addObject("browseNum", list);
		mav.addObject("commentNum", list);
		mav.addObject("userNum", 1);
		mav.addObject("runDays",720);
		return mav;
	}*/
	
	
	
	@GetMapping("/about")
	public String about(){
		return "views/about";
	}
	

}
