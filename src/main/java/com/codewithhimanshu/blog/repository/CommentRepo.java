package com.codewithhimanshu.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithhimanshu.blog.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{
	
	

}
