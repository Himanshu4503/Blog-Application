package com.codewithhimanshu.blog.service;

import java.util.List;

import com.codewithhimanshu.blog.payloads.UserDto;

public interface UserService {
	
	UserDto registerNewUser(UserDto user);

	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto userDto, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);
}
