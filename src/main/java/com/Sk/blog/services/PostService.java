package com.Sk.blog.services;

import java.util.List;

import com.Sk.blog.entites.Post;
import com.Sk.blog.payloads.PostDto;
import com.Sk.blog.payloads.PostResponce;

public interface PostService {

	
	//create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);
	
	//update   
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//get all  post
	PostResponce getAllPost(Integer pageNum,Integer pageSize,String sortBy,String sortDir);
	
	//get single post 
	PostDto getPostById(Integer postId);
	
	//get all post by category
	List<PostDto> getPostsByCategory(Integer postId);
	
	//get all post by user
	List<PostDto> getPostByUser(Integer postId);
	
	//search post
	List<PostDto> searchPost(String keywords);

	
}
