package com.codewithhimanshu.blog.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiException extends RuntimeException{

	public ApiException() {
		super();
		
	}

	public ApiException(String message) {
		super(message);
		
	}
	
	
	

}
