package com.backend_learning.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.backend_learning.blog.entitiesORModeles.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//for not exposing the entities directly 
//by using dto we can hide some data for entity and customized the dto
//DTO - Data Transfer object
//to use getter and setters of this file to another file 
//we need lambok jar file

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	//this are Bean validation to use validation use @Valid in userController
	@NotEmpty
	@Size(min = 4,message="Username must be min of 4 characters !!")
	private String name;
	
	@Email(message="Email address is not vaild" )
	private String email;
	
	//to prevent password to show after creating in postman
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotEmpty
	@Size(min=3, max=10, message="Password must be min of 3 chars and max of chars !!")
	private String password;
	
	@NotBlank
	private String about;
	
	//for getting role when user is registered
	private Set<RoleDto> roles = new HashSet<>();
}
