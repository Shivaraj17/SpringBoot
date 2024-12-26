package com.backend_learning.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend_learning.blog.config.AppConstants;
import com.backend_learning.blog.entitiesORModeles.Role;
import com.backend_learning.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//to create role wile starting app in database
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	//the @Bean can be declared using @Configuration 
	//@SpringBootApplication inbuilt has 
	//@Configuration,@EnableAutoConfiguration,@ComponentScan 
	//so we can declared the bean in main class it self 
	
	//to create separate class
	//	@Configuration
	//	public class AppConfig {
	//
	//	    @Bean
	//	    public ModelMapper modelMapper() {
	//	        return new ModelMapper();
	//	    }
	//	}
	
	//if springboot needs the class by bean it will use automatically
	@Bean //to create object of ModelMapper
	//here bean is used because as we said above @SpringBootApplication 
	//can only create bean we have to use in other class so 
	//by using @Autowired we can use in other class also
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	//method of CommendLineRunner for encode password in terminal for abc
	@Override
	public void run(String... args) throws Exception {
		System.out.println(this.passwordEncoder.encode("abc"));
		
		try {
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");
			
			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");
			
			List<Role> roles = List.of(role,role1);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
