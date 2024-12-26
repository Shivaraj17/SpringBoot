package com.backend_learning.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend_learning.blog.entitiesORModeles.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
