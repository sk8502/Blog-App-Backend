package com.Sk.blog.services;

import com.Sk.blog.payloads.CommentsDto;

public interface CommentsService {

	CommentsDto createComment(CommentsDto commentsDto,Integer commentId);
	void deleteComment(Integer commentId);
	
}
