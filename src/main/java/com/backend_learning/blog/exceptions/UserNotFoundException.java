package com.backend_learning.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException{
	private String User;
	private String Password;
	private String UserName;
	
	public UserNotFoundException(String user, String password, String userName) {
		super(String.format("%s not found with %s : %s", user, password, userName));
		this.User = user;
		this.Password = password;
		this.UserName = userName;
	}
}
