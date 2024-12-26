package com.backend_learning.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend_learning.blog.entitiesORModeles.User;
import com.backend_learning.blog.exceptions.ResourceNotFoundException;
import com.backend_learning.blog.exceptions.UserNotFoundException;
import com.backend_learning.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	//loading user in userDetails from database by 
	//UserDetailsService class
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	//to get the email insted of user name
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = this.userRepo.findByEmail(username).orElseThrow(()-> new UserNotFoundException("User", "email", "username"));
		return user;
	}

}
