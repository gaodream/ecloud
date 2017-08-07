package com.dream.home.comment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dream.home.article.model.ArticleVO;
import com.dream.home.comment.model.CommentVO;
import com.dream.home.comment.service.CommentService;
import com.ecloud.framework.controller.BaseRestController;
import com.ecloud.framework.model.EResponse;

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
	public EResponse gotoEdit(ArticleVO articleVO){
		EResponse result = EResponse.build();
		result.setResult(commentService.doSearchPage(new CommentVO()));
		return result;
	}
	
	@GetMapping(value ="/queryList/{id}")
	public EResponse doFind(@PathVariable String id){
		EResponse response = EResponse.build();
		CommentVO vo = new CommentVO();
		vo.setArticleId(id);
		try {
			response.setResult(commentService.doSearchListByVO(vo));
		}catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return response;
	}
	

}
