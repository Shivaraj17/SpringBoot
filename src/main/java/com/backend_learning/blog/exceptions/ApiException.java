package com.backend_learning.blog.exceptions;

public class ApiException extends RuntimeException {

	//parameterised constructor
	public ApiException(String message) {
		super(message);
		
	}
	//default constructor
	public ApiException() {
		super();
	}
	
	
}
