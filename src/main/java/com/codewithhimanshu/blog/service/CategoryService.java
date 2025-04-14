package com.codewithhimanshu.blog.service;

import java.util.List;

import com.codewithhimanshu.blog.payloads.CategoryDto;


public interface CategoryService {
	
	// create 
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	// update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	// delete
	public void deleteCategory(Integer categoryId);
	
	//get
	CategoryDto getCategory(Integer categoryId);
	
	// get ALL
	List<CategoryDto> getCategories();
	

}
