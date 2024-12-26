package com.backend_learning.blog.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String username; //we are taking email as username from db
	
	private String password;

}
