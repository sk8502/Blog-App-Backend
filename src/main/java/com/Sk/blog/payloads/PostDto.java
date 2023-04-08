package com.Sk.blog.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.stereotype.Service;

import com.Sk.blog.entites.Category;
import com.Sk.blog.entites.Comments;
import com.Sk.blog.entites.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	
	private Integer postid;
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	
	private CategoryDto category;
	
	private UserDTO user;
	
	private List<CommentsDto> comments=new ArrayList<>();
}
