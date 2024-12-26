package com.backend_learning.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.backend_learning.blog.entitiesORModeles.Category;
import com.backend_learning.blog.entitiesORModeles.Comment;
import com.backend_learning.blog.entitiesORModeles.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private  CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> comments = new HashSet<>();
}
