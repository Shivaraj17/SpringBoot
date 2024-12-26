package com.backend_learning.blog.services;

import java.util.List;

import com.backend_learning.blog.payloads.UserDto;

public interface UserService {
	//in interface by default UserDto createUser(UserDto user);
	//will be public UserDto createUser(UserDto user); it apples to all
	
	
	//for registration
	UserDto registerNewUser(UserDto user);
	
	//CURD Operations
	//createUser is used to create table from sql
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Integer userId);
	
	UserDto getUserById(Integer userId);
	
	List<UserDto> getAllUsers();
	
	//here we have use void because we do'nt need the deleted value 
	//in database
	void deleteUser(Integer userId); 
}
