package com.Sk.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sk.blog.entites.Category;
import com.Sk.blog.payloads.ApiResponce;
import com.Sk.blog.payloads.CategoryDto;
import com.Sk.blog.services.CategoryService;
import com.Sk.blog.services.impl.CategoryServiceImpl;

@RestController
@RequestMapping("api/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	// create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}

	// update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer catId) {
		CategoryDto createCategory = this.categoryService.updateCategory(categoryDto,catId);
		return new ResponseEntity<CategoryDto>(createCategory, HttpStatus.CREATED);

	}
	
	// delete

	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponce> deleteCategory(@PathVariable Integer catId) {
		 this.categoryService.deleteCategory(catId);
		return new ResponseEntity<ApiResponce>(new  ApiResponce("category is deleted",true), HttpStatus.OK);

	}
	
	// get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId) {
		CategoryDto categoryDto= this.categoryService.getCategory(catId);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);

	}
	
	
	//getall
		@GetMapping("/getall")
		public ResponseEntity<List<CategoryDto>> getAllCategory() {
			List<CategoryDto> categories= this.categoryService.getAllCategory();
			return ResponseEntity.ok(categories);

		}
	
}
