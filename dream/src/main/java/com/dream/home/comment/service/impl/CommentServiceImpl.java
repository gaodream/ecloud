package com.dream.home.comment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dream.home.comment.dao.CommentDAO;
import com.dream.home.comment.model.CommentVO;
import com.dream.home.comment.service.CommentService;
import com.ecloud.framework.service.impl.BaseServiceImpl;

@Service("commentService")
public class CommentServiceImpl extends BaseServiceImpl<CommentVO> implements CommentService {

	@Autowired
	private CommentDAO commentDAO;

	@Override
	public CommentDAO getBaseDAO() {
		return commentDAO;
	}

}