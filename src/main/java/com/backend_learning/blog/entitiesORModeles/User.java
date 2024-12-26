package com.backend_learning.blog.entitiesORModeles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//the table name will be as class name to change the table name
@Table(name = "users")

@NoArgsConstructor

//representation of @NoArgsConstructor
//public Book() {
//    // Default constructor
//}

//@AllArgsConstructor

//representation of @AllArgsConstructor
//public Book(String title, String author, int pages) {
//    this.title = title;
//    this.author = author;
//    this.pages = pages;
//}

//@ToString 

//it is used to print the setted data 
//public String toString() {
//    return "User{" +
//            "id=" + id +
//            ", name='" + name + '\'' +
//            ", email='" + email + '\'' +
//            ", password='" + password + '\'' +
//            ", about='" + about + '\'' +
//            '}';
//}

//@Data contains @NoArgsConstructor,@AllArgsConstructor and @ToString
//we can use @Data without using @NoArgsConstructor,@AllArgsConstructor and @ToString

@Getter

//public int getId() {
//    return id;
//}

@Setter

//public void setId(int id) {
//this.id = id;
//}

public class User implements UserDetails{
	//implements UserDetails from springSecurity 
	//to authorised the userDetails and use the methods of it
	
	@Id // making id as primary key

	// GenerationType.AUTO to autoIncrement id while inserting
	// GenerationType.AUTO is having some issues
	// so use GenerationType.IDENTITY

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	// to change the column name and providing schema
	@Column(name = "user_name", nullable = false, length = 100)
	private String name;

	private String email;

	private String password;

	private String about;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", 
	    joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"), 
	    inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>(); //ManyToMany
	
	//methods of UserDetails from springSecurity
	@Override
	//here role represents Role of Entity class
	//SimpleGrantedAuthority is used to set role from database 
	//which is admin,user etc... 
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authories = this.roles.stream()
		.map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authories;
	}

	@Override
	//to get user name form dataBase by email in database
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
