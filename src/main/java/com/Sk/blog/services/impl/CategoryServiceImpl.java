package com.Sk.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sk.blog.entites.Category;
import com.Sk.blog.exceptions.ResourceNotFoundException;
import com.Sk.blog.payloads.CategoryDto;
import com.Sk.blog.repositories.CategoryRepo;
import com.Sk.blog.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.mapper.map(categoryDto, Category.class);
		Category addcat = this.categoryRepo.save(cat);

		return this.mapper.map(addcat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
		cat.setCategoryDescription(categoryDto.getCategoryTitle());
		cat.setCategoryTitle(categoryDto.getCategoryTitle());

		Category updatecat = this.categoryRepo.save(cat);
		return this.mapper.map(updatecat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "category id", categoryId));
		
		return this.mapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allCategory=this.categoryRepo.findAll();
		List<CategoryDto> catdto =allCategory.stream().map((cat)-> this.mapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return catdto;
	}
	
	

}
