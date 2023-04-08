package com.Sk.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sk.blog.payloads.ApiResponce;
import com.Sk.blog.payloads.CommentsDto;
import com.Sk.blog.services.CommentsService;

@RestController
@RequestMapping("/api/")
public class CommentController {
	
	
	@Autowired
	private CommentsService commentsService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentsDto> createComment(@RequestBody CommentsDto commentsDto,@PathVariable Integer postId){
		CommentsDto createComment=this.commentsService.createComment(commentsDto, postId);
		return new ResponseEntity<CommentsDto>(createComment,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponce> deleteComment(@PathVariable Integer commentId){
		this.commentsService.deleteComment(commentId);
		return new ResponseEntity<ApiResponce>(new ApiResponce("comment deleted",true),HttpStatus.OK);
		
	}
	
	

}
