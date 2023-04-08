package com.Sk.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sk.blog.entites.Category;
import com.Sk.blog.entites.Post;
import com.Sk.blog.entites.User;

public interface PostRepo  extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	List<Post> findByTitleContaining(String title);
}
