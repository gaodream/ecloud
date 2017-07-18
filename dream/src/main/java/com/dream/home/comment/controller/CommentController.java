package com.dream.home.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.dream.home.article.model.ArticleVO;
import com.dream.home.comment.model.CommentVO;
import com.dream.home.comment.service.CommentService;
import com.ecloud.framework.controller.BaseRestController;

@RestController
@RequestMapping("/comment/")
public class CommentController  extends BaseRestController<CommentVO>{

	@Autowired
	private CommentService commentService;

	@Override
	public CommentService getBaseService() {
		return this.commentService;
	}
	
	
	@GetMapping("list")
	public ModelAndView gotoEdit(ArticleVO articleVO){
		ModelAndView mav = new ModelAndView("manager/commentList");
		mav.addObject("page", commentService.doSearchPage(new CommentVO()));
		return mav;
	}

}
