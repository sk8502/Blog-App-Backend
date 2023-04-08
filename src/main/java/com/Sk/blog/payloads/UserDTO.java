package com.Sk.blog.payloads;



import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.Sk.blog.entites.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	
	private int id;
	@NotEmpty
	@Size(min=4,message="user name msut be gater then 4 char")
	private String name;
	@Email(message="your email adress is not valid")
	private String email;
	@NotEmpty
	@Size(min=3,max=8,message="password msut be min of 3 and max 8 char")
	private String password;
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();

}
