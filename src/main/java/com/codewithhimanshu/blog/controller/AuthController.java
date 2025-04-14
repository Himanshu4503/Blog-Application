package com.codewithhimanshu.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithhimanshu.blog.exceptions.ApiException;
import com.codewithhimanshu.blog.payloads.JwtAuthRequest;
import com.codewithhimanshu.blog.payloads.JwtAuthResponse;
import com.codewithhimanshu.blog.payloads.UserDto;
import com.codewithhimanshu.blog.security.JwtTokenHelper;
import com.codewithhimanshu.blog.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request)
	{
		System.out.println("Login attempt for username: " + request.getUsername());

		try {
			UserDetails userDetailsCheck = this.userDetailsService.loadUserByUsername(request.getUsername());
			this.authenticate(request.getUsername(), request.getPassword());

			String token = this.jwtTokenHelper.generateToken(userDetailsCheck);
			System.out.println("Token generated successfully");

			JwtAuthResponse response = new JwtAuthResponse();
			response.setToken(token);
			return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Authentication error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(new JwtAuthResponse(),
					HttpStatus.UNAUTHORIZED);
		}
	}

	private void authenticate(String username, String password) throws Exception
	{
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			throw new ApiException("Invalid username or password !!");
		}
	}
	
	// register new user api
	
	@PostMapping("register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		UserDto registeredUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registeredUser,HttpStatus.CREATED);
	}
}