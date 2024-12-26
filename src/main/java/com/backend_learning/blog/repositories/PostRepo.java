package com.backend_learning.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend_learning.blog.entitiesORModeles.Category;
import com.backend_learning.blog.entitiesORModeles.Post;
import com.backend_learning.blog.entitiesORModeles.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findByUser (User user);
	List<Post> findByCategory (Category category);
	
	//for implementing searching by Title
	List<Post> findByTitleContaining(String title);
	
	//for searching using content
//	List<Post> findByContentContaining (String content);
}
