package com.codewithhimanshu.blog.service;

import com.codewithhimanshu.blog.payloads.*;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	
	void deleteComment(Integer commentId);
	

}
