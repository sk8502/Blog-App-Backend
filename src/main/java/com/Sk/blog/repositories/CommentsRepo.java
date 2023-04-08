package com.Sk.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sk.blog.entites.Comments;

public interface CommentsRepo extends JpaRepository<Comments, Integer> {

}
