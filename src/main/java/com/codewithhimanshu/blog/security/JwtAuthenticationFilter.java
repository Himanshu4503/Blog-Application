package com.codewithhimanshu.blog.security;

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

	    // 1. get token
	    String requestToken = request.getHeader("Authorization");

	    // Debugging log (You may want to remove this in production)
	    System.out.println(requestToken);

	    String username = null;
	    String token = null;

	    // Ensure requestToken is not null before checking its contents
	    if (requestToken != null && requestToken.startsWith("Bearer ")) {
	        // Extract token from the Authorization header
	        token = requestToken.substring(7);

	        try {
	            username = this.jwtTokenHelper.getUsernameFromToken(token);
	        } catch (IllegalArgumentException e) {
	            System.out.println("unable to get Jwt token");
	        } catch (ExpiredJwtException e) {
	            System.out.println("Jwt token has expired");
	        } catch (MalformedJwtException e) {
	            System.out.println("invalid jwt");
	        }
	    } else {
	        System.out.println("Jwt token does not begin with Bearer or is null");
	    }

	    // Once we get the token, now we validate
	    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

	        if (this.jwtTokenHelper.validateToken(token, userDetails)) {
	            // Authentication is valid, set security context
	            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
	                    userDetails, null, userDetails.getAuthorities());
	            usernamePasswordAuthenticationToken
	                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

	            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

	        } else {
	            System.out.println("Invalid jwt Token");
	        }
	    } else {
	        System.out.println("Username is null or context is not null");
	    }

	    filterChain.doFilter(request, response);
	}
}