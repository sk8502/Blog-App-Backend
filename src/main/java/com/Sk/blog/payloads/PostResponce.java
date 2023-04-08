package com.Sk.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponce {

	
	private List<PostDto> content;
	
	private int pageNum;
	private int pageSize;
	private Long totalElement;
	private int totalPage;
	private Boolean lastPage; 
}
