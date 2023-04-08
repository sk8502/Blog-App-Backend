package com.Sk.blog.services;

import java.util.List;

import com.Sk.blog.entites.Category;
import com.Sk.blog.payloads.CategoryDto;

public interface CategoryService {

	public CategoryDto createCategory(CategoryDto categoryDto);

	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	void deleteCategory(Integer categoryId);

	public CategoryDto getCategory(Integer categoryId);
	
	public List<CategoryDto> getAllCategory();
	
}
