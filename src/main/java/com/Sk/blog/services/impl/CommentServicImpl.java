package com.Sk.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sk.blog.entites.Comments;
import com.Sk.blog.entites.Post;
import com.Sk.blog.exceptions.ResourceNotFoundException;
import com.Sk.blog.payloads.CommentsDto;
import com.Sk.blog.repositories.CommentsRepo;
import com.Sk.blog.repositories.PostRepo;
import com.Sk.blog.services.CommentsService;

@Service
public class CommentServicImpl implements CommentsService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentsRepo commentsRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentsDto createComment(CommentsDto commentsDto, Integer postid) {
		Post post=this.postRepo.findById(postid).orElseThrow(() -> new ResourceNotFoundException("post", "post id", postid));
		Comments comments=this.modelMapper.map(commentsDto, Comments.class);
		comments.setPost(post);
		Comments saveComment=this.commentsRepo.save(comments);
		
		
		return this.modelMapper.map(saveComment, CommentsDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comments comments=this.commentsRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("comment", "comment id", commentId));
		 this.commentsRepo.delete(comments);
	}

}
