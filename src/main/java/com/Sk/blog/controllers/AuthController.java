package com.Sk.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sk.blog.exceptions.ApiException;
import com.Sk.blog.payloads.JwtAuthRequest;
import com.Sk.blog.payloads.JwtAuthResponce;
import com.Sk.blog.payloads.UserDTO;
import com.Sk.blog.security.JwtTokenHelper;
import com.Sk.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
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
	public ResponseEntity<JwtAuthResponce> createToken(@RequestBody JwtAuthRequest request) throws Exception {

		this.authenticate(request.getEmail(), request.getPassword());
		UserDetails userDetail = userDetailsService.loadUserByUsername(request.getEmail());

		String token = this.jwtTokenHelper.generateToken(userDetail);
		JwtAuthResponce responces = new JwtAuthResponce();
		responces.setToken(token);
		return new ResponseEntity<JwtAuthResponce>(responces,HttpStatus.OK);

	}

	private void authenticate(String email, String passowrd) throws Exception {

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, passowrd);
		try {
			this.authenticationManager.authenticate(authentication);
		} catch (BadCredentialsException e) {
			System.out.println("invalid details");
			throw new ApiException("invalid user name or password");
		}
		
	}
	
		//register new user api
		@PostMapping("/register")
		public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDto){
			
			UserDTO registeredUser=this.userService.registerNewUser(userDto);
			return new ResponseEntity<UserDTO>(registeredUser,HttpStatus.CREATED);
		}
		

	}


