package com.codewithhimanshu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithhimanshu.blog.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
	

}
