package com.backend_learning.blog.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend_learning.blog.payloads.ApiResponse;
import com.backend_learning.blog.payloads.UserDto;
import com.backend_learning.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")

//CURD operations

public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")//to create data use post
	
	//ResponseEntity is used to send response to server
	//@Valid is used to validate the given fields from userDto
	//It looks for a controller method that matches:
	//HTTP method (POST).
	//Request path (/api/users).
	//It finds the createUser method because of the @PostMapping annotation.
	//Spring automatically calls the method and passes the parsed request 
	//body (UserDto) to it.
	//example in react
//	fetch("http://localhost:8080/api/users", {
//	    method: "POST",
//	    headers: { "Content-Type": "application/json" },
//	    body: JSON.stringify({
//	        name: "John Doe",
//	        email: "johndoe@example.com",
//	        password: "password123"
//	    })
//	}).then(response => response.json())
//	  .then(data => console.log(data));
//@RequestBody does below code, first @requestBody and @vaild 
//takes value from client and sets below code
//UserDto userDto = new UserDto();
//	userDto.setName("John Doe");
//	userDto.setEmail("johndoe@example.com");
//	userDto.setPassword("password123");
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	 @PutMapping("/{userId}")//path uri variable
	 public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid){
		 UserDto updatedUser = this.userService.updateUser(userDto, uid);
		 return ResponseEntity.ok(updatedUser);
	 }
	 
	 // Admin role restriction for deleting users
	 @PreAuthorize("hasRole('ADMIN')")
	 @DeleteMapping("/{userId}")
	 public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		 this.userService.deleteUser(uid);
		 //here we can also make key value pair message is key and user deleted scussfully is value
		 //return new ResponseEntity(Map.of("message","User deleted Successfully"), HttpStatus.OK);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Scussfully",true),HttpStatus.OK);
	 }
	 
	  @GetMapping("/")
	     public ResponseEntity<List<UserDto>> getAllUsers() {
	         List<UserDto> users = this.userService.getAllUsers();
	         return ResponseEntity.ok(users);
	      }
	 
	 @GetMapping("/{userId}")
	 //here return type is not list because it give only one value
	    public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) {
	        UserDto user = this.userService.getUserById(userId);
	        return ResponseEntity.ok(user);
	 }
}
 