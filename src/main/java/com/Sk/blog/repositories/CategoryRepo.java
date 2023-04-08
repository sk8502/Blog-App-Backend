package com.Sk.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Sk.blog.entites.Category;


public interface CategoryRepo extends JpaRepository<Category, Integer> {

	
}
