package com.backend_learning.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend_learning.blog.entitiesORModeles.User;

//JpaRepository is used to create sql codes
//we can also use CURDRepository 
//but it does not have inBulit pagination and sorting
public interface UserRepo extends JpaRepository<User, Integer>{
	//By using Optional class we can check if the user is present 
	//in the database or not by using .isPresent() .ifPresent() etc...
	Optional<User> findByEmail(String email);
}
