package com.Sk.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Sk.blog.entites.Category;
import com.Sk.blog.entites.Post;
import com.Sk.blog.entites.User;
import com.Sk.blog.exceptions.ResourceNotFoundException;
import com.Sk.blog.payloads.CategoryDto;
import com.Sk.blog.payloads.PostDto;
import com.Sk.blog.payloads.PostResponce;
import com.Sk.blog.repositories.CategoryRepo;
import com.Sk.blog.repositories.PostRepo;
import com.Sk.blog.repositories.UserRepo;
import com.Sk.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setCategory(cat);
		post.setUser(user);
		Post post1 = this.postRepo.save(post);

		return this.modelMapper.map(post1, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepo.save(post);
		return this.modelMapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponce getAllPost(Integer pageNum, Integer pageSize, String sortBy,String sortDir) {

		Sort sort=(sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort=Sort.by(sortBy).ascending();
//		}
//		else {
//			sort=Sort.by(sortBy).descending();
//		}
		Pageable p = PageRequest.of(pageNum, pageSize,sort);
		Page<Post> posts = this.postRepo.findAll(p);
		List<Post> allPost = posts.getContent();
		List<PostDto> posst = allPost.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		PostResponce postResponce = new PostResponce();
		postResponce.setContent(posst);
		postResponce.setPageNum(posts.getNumber());
		postResponce.setPageSize(posts.getSize());
		postResponce.setTotalElement(posts.getTotalElements());
		postResponce.setTotalPage(posts.getTotalPages());
		postResponce.setLastPage(posts.isLast());
		return postResponce;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post posts = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));

		return this.modelMapper.map(posts, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer postId) {
		Category category = this.categoryRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", postId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postdto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postdto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer postId) {
		User user = this.userRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", postId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

	@Override
	public List<PostDto> searchPost(String keywords) {
		List<Post> posts=this.postRepo.findByTitleContaining(keywords);
		List<PostDto> postDtos=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

}
