package com.backend_learning.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus; // Correct import for HttpStatus
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // Add this import
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend_learning.blog.exceptions.ApiException;
import com.backend_learning.blog.exceptions.UserNotFoundException;
import com.backend_learning.blog.payloads.JwtAuthRequest;
import com.backend_learning.blog.payloads.UserDto;
import com.backend_learning.blog.security.JwtAuthResponse;
import com.backend_learning.blog.security.JwtTokenHelper;
import com.backend_learning.blog.services.UserService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    
    @Autowired
    private JwtTokenHelper jwtTokenHelper; // Helper class to generate token
    
    @Autowired
    private UserDetailsService userDetailsService; // Service to load user details
    
    @Autowired
    private AuthenticationManager authenticationManager; // Authentication manager to authenticate the user
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest request // Request payload containing username and password
            ){
        
        // Step 1: Authenticate the user with the provided credentials
        this.authenticate(request.getUsername(), request.getPassword());
        
        // Step 2: Load user details by the username (email in this case)
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
        
        // Step 3: Generate JWT token using the user details
        String token = this.jwtTokenHelper.generateToken(userDetails); // Pass userDetails instead of null
        
        // Step 4: Create JWT response and send it as a response entity
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        
        return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK); // Correct status code
    }

    private void authenticate(String username, String password) {
        // Step 1: Create an authentication token with the provided username (email) and password
        UsernamePasswordAuthenticationToken authenticationToken = 
                new UsernamePasswordAuthenticationToken(username, password);
       
        try {
            // Step 2: Authenticate the token using the authentication manager
            this.authenticationManager.authenticate(authenticationToken);
        }catch(BadCredentialsException e){
        	System.out.println("Invalid Details !!");
//        	throw new UserNotFoundException("User",username, password);
        	throw new ApiException("Invaild username or password !!");
        }
    }
    //register new user api
    
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
    	UserDto registeredUser = this.userService.registerNewUser(userDto);
    	return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
    }
}
