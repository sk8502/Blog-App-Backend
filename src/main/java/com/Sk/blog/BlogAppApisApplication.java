package com.Sk.blog;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.internal.build.AllowSysOut;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Sk.blog.config.AppConstant;
import com.Sk.blog.entites.Role;
import com.Sk.blog.repositories.RoleRepo;





@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepo repo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("abc"));
		
		try {
			Role role=new Role();
			role.setId(AppConstant.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1=new Role();
			role1.setId(AppConstant.NORMAL_USER);
			role1.setName("NORMAL_USER");
		
		List<Role> roles=new ArrayList<>();
			roles.add(role);
			roles.add(role1);
		List<Role> result	=this.repo.saveAll(roles);
		
		result.forEach(r->{
			System.out.println(r.getName());
		});
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
