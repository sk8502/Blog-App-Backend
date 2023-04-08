package com.Sk.blog.services;

import java.util.List;

import com.Sk.blog.entites.User;
import com.Sk.blog.payloads.UserDTO;

public interface UserService {

	
	UserDTO registerNewUser(UserDTO user);
	
	UserDTO createUser(UserDTO userDTO);
	
	UserDTO updateUser(UserDTO user, Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);
	
}
