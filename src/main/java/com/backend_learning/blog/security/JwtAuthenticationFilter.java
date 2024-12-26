package com.backend_learning.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Get token from the Authorization header
        String requestToken = request.getHeader("Authorization");
        
        //Bearer 2352523sdgsg
        String username = null;
        String token = null;

        // Log the received token for debugging
        System.out.println("Token received: " + requestToken);

        // Check if Authorization header is present and starts with "Bearer"
        if (requestToken != null && requestToken.startsWith("Bearer")) {
            token = requestToken.substring(7); // Extract token part

            try {
            	//above username is assigned here
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token: " + e.getMessage());
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired: " + e.getMessage());
            } catch (MalformedJwtException e) {
                System.out.println("Invalid JWT token: " + e.getMessage());
            } catch (SignatureException e) {
                System.out.println("JWT token signature is invalid: " + e.getMessage());
            }
        } else {
                System.out.println("JWT token does not begin with Bearer");
            }

        // 2. Once we get the token, validate it
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Validate token
            if (this.jwtTokenHelper.validateToken(token, userDetails)) {
                // Token is valid, create authentication object
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println("JWT token successfully validated and user authenticated: " + username);
            } else {
                System.out.println("JWT token validation failed");
            }
        }else {
        	System.out.println("username is null or context is not null");
        }

        // 3. Continue with the filter chain
        filterChain.doFilter(request, response);
    }
}
