package com.Sk.blog.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Sk.blog.config.AppConstant;
import com.Sk.blog.entites.Role;
import com.Sk.blog.entites.User;
import com.Sk.blog.exceptions.ResourceNotFoundException;
import com.Sk.blog.payloads.UserDTO;
import com.Sk.blog.repositories.RoleRepo;
import com.Sk.blog.repositories.UserRepo;
import com.Sk.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo repo;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		User user = this.dtoToUser(userDTO);
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());
		User updateuser = this.userRepo.save(user);
		UserDTO userdto = this.userToDto(updateuser);

		return userdto;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));

		return this.userToDto(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDTO> userdto = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userdto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDTO userDto) {
		User user = this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user. setName(userDto.getName());
//		user. setEmail(userDto.getEmail());
//		user. setAbout(userDto.getAbout());
//		user. setPassword(userDto.getPassword());
		return user;
	}

	public UserDTO userToDto(User user) {
		UserDTO userDto = this.modelMapper.map(user, UserDTO.class);
		return userDto;
	}

	@Override
	public UserDTO registerNewUser(UserDTO userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		// encode password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = this.repo.findById(AppConstant.NORMAL_USER).get();
		user.getRoles().add(role);
		User newuser = this.userRepo.save(user);
		return this.modelMapper.map(newuser, UserDTO.class);
	}

}
