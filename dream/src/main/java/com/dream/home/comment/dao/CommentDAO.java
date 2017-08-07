package com.dream.home.comment.dao;

import org.apache.ibatis.annotations.Mapper;

import com.dream.home.comment.model.CommentVO;
import com.ecloud.framework.dao.BaseDAO;

@Mapper
public interface CommentDAO extends BaseDAO<CommentVO>{

}