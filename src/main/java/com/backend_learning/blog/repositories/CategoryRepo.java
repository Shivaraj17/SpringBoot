package com.backend_learning.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend_learning.blog.entitiesORModeles.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	
}
