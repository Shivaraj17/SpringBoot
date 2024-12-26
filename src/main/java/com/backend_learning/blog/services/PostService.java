package com.backend_learning.blog.services;

import java.util.List;

import com.backend_learning.blog.entitiesORModeles.Post;
import com.backend_learning.blog.payloads.PostDto;
import com.backend_learning.blog.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);
	
	PostDto updatePost(PostDto postDto, Integer postId);
	
	void deletePost(Integer postId);
//	before pageable
//	List<PostDto>getAllPost();

//after pageable

	//before pagination
//	List<PostDto> getAllPost(Integer pageNumber, Integer pageSize);
	
	//after pagination
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getPostByCategory(Integer categoryId);
	
	List<PostDto> getPostByUser(Integer userId);
	
	List<PostDto> searchPosts(String keyword);
}
