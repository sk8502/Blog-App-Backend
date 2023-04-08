package com.Sk.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sk.blog.payloads.ApiResponce;
import com.Sk.blog.payloads.UserDTO;
import com.Sk.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// creates user
	@PostMapping("/")
	public ResponseEntity<UserDTO> createuser(@Valid @RequestBody UserDTO userDTO) {
		UserDTO createuserDTO = this.userService.createUser(userDTO);
		return new ResponseEntity<>(createuserDTO, HttpStatus.CREATED);

	}

	// update user
	@PutMapping("/{userid}")
	public ResponseEntity<UserDTO> updateduser(@Valid @RequestBody UserDTO userDTO,
			@PathVariable("userid") Integer userid) {
		UserDTO updateuser = this.userService.updateUser(userDTO, userid);
		return ResponseEntity.ok(updateuser);
	}

	//admin only
	// delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userid}")
	public ResponseEntity<?> deletuser(@PathVariable("userid") Integer uid) {

		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponce>(new ApiResponce("user deleted sucessfuklly", true), HttpStatus.CREATED);
	}

	// get all user
	@GetMapping("/")
	public ResponseEntity<List<UserDTO>> alluser() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	// get single user
	@GetMapping("/{userid}")
	public ResponseEntity<UserDTO> alluser(@PathVariable Integer userid) {
		return ResponseEntity.ok(this.userService.getUserById(userid));
	}

}
