package com.Sk.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

	
	private Integer categoryID;

	@NotBlank
	@Size(min=4)
	private String categoryTitle;
    @NotBlank
    @Size(max=100)
	private String categoryDescription;
}
