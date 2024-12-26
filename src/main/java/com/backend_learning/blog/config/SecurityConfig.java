package com.backend_learning.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.backend_learning.blog.security.CustomUserDetailService;
import com.backend_learning.blog.security.JwtAuthenticationEntryPoint;
import com.backend_learning.blog.security.JwtAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableWebMvc //for using swagger
//Enable method security with @PreAuthorize
//role based auth
@EnableMethodSecurity(prePostEnabled = true) 
public class SecurityConfig{
	
	//for using swagger 
	//http://localhost:9090/swagger-ui/index.html
	public static final String[] PUBLIC_URLS = {
			"/api/auth/**",
			"/v3/api-docs",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"webjars/**"
	};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired 
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable()) // Updated to lambda style
	        .authorizeHttpRequests(auth -> auth
	                .requestMatchers(HttpMethod.GET).permitAll() // Permit all GET requests
	                .requestMatchers(PUBLIC_URLS).permitAll() // Allow public access to specified URLs
	                .anyRequest().authenticated() // All other requests must be authenticated
	            )
	        //below we can use hasRole etc... 
	        // Allow public access to authentication endpoints
	        
//	        .requestMatchers("/api/auth/login").permitAll()
	        
	        //tow allow 2 or more api in /api/auth
//	        .requestMatchers("/api/auth/**").permitAll()
	        //to allow public access to all the get request

	        //for using swagger
//	        .requestMatchers("/v3/api-docs").permitAll()
	        //for using swagger with all apis


	     // Handle authorization failures (403 Forbidden)
	        .exceptionHandling(exceptionHandling -> exceptionHandling
	                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // Handle unauthorized access
	                .accessDeniedHandler((request, response, accessDeniedException) -> {
	                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	                    response.setContentType("application/json");
	                    response.getWriter().write("{\"error\": \"Access Denied: You do not have the necessary permissions.\"}");
	                })
	            )
            
//	        .httpBasic();
            
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

            return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = 
	        http.getSharedObject(AuthenticationManagerBuilder.class);
	    authenticationManagerBuilder
	        .userDetailsService(customUserDetailService)// Custom user service for user retrieval
	        .passwordEncoder(passwordEncoder()); //below function is used
	    return authenticationManagerBuilder.build();
	}

	@Bean
	//to BCryptPasswordEncoder password to original from database
	//PasswordEncript means to set password to hash form
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();// BCrypt encoder for password hashing
	}
}
