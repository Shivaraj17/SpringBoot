package com.backend_learning.blog.entitiesORModeles;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY) 
//the roles will be one or two so id creation is not required
//we will set id mannually
	private int id;
	
	private String name;
	
}
