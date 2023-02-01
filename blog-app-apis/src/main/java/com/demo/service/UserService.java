package com.demo.service;

import java.util.List;



import com.demo.payloads.UserDto;

public interface UserService {

	//create 
	UserDto createUser(UserDto userDto);
	
	//update 
	UserDto updateUser(UserDto user, Integer userId);
	
	
	//delete
	UserDto getUserById(Integer userId);
	
	
	//get
	List<UserDto> getAllUsers();
	
	
	//get all
	void deleteUser(Integer userId);

	
}
