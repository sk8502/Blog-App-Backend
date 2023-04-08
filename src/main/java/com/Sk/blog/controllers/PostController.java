package com.Sk.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Sk.blog.config.AppConstant;
import com.Sk.blog.entites.Post;
import com.Sk.blog.payloads.ApiResponce;
import com.Sk.blog.payloads.PostDto;
import com.Sk.blog.payloads.PostResponce;
import com.Sk.blog.services.FileService;
import com.Sk.blog.services.PostService;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	
	@PostMapping("user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);

	}

//update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> UpdatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	// get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {

		List<PostDto> posts = this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	// get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {

		List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);

	}

	// get All post
	@GetMapping("/getAllpost")
	public ResponseEntity<PostResponce> getAllPost(
			@RequestParam(value = "pageNum", defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR, required = false) String sortDir) {

		PostResponce posts = this.postService.getAllPost(pageNum, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponce>(posts, HttpStatus.OK);

	}

	// get one post
	@GetMapping("/getPost/{postId}")
	public ResponseEntity<PostDto> getPost(@PathVariable Integer postId) {

		PostDto posts = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(posts, HttpStatus.OK);

	}

	// deleted post
	@DeleteMapping("/posts/{postId}")
	public ApiResponce deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);

		return new ApiResponce("post is sucessfull deleted", true);

	}

	// search

	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keyword) {
		List<PostDto> result = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(result, HttpStatus.OK);

	}

	// post image upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostIamge(@RequestParam ("image")MultipartFile image,
			                                              @PathVariable Integer postId) throws IOException{
		PostDto postDto=this.postService.getPostById(postId);
	String fileName	=this.fileService.uploadimage(path, image);
	
	postDto.setImageName(fileName);
	PostDto updatePost=this.postService.updatePost(postDto, postId);
	return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	
	}
	
	@GetMapping(value="post/image/{imageName}",produces =MediaType.IMAGE_JPEG_VALUE )
	public void downloadImage(@PathVariable("imageName")String imageName,
			HttpServletResponse response
			) throws IOException {
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	

}
