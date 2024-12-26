package com.backend_learning.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend_learning.blog.config.AppConstants;
import com.backend_learning.blog.entitiesORModeles.Role;
import com.backend_learning.blog.entitiesORModeles.User;
import com.backend_learning.blog.exceptions.ResourceNotFoundException;
import com.backend_learning.blog.payloads.UserDto;
import com.backend_learning.blog.repositories.RoleRepo;
import com.backend_learning.blog.repositories.UserRepo;
import com.backend_learning.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	//here in repositories we have used <User> which is entity 
	//we have to use UserDto insted of user so we are going to 
	//use modelMapper or we can create method to send object to 
	//the userDto 
	
	
	//UserRepo is not class it is interface created by use
	//@Autowired can be used to create object of only class
	//but it is creating the object of interface also
	//by using Proxy class to see how see in test
	
	//className = jdk.proxy2.$Proxy113
	//packageName = jdk.proxy2
	@Autowired
	private  UserRepo userRepo;
	// Constructor-based Dependency Injection
	//is used when we use interface we can link any class to interface
	//it is also used for testing peruse
	
	//below is Represention of  Autowired
	
	//public UserServiceImpl(UserRepo userRepo) {
    //this.userRepo = userRepo;
    //}
	//above is used in this form
	//UserRepo userRepo = new UserRepoImpl(); 
	//UserServiceImpl userService = new UserServiceImpl(userRepo);
	
	
	@Autowired
	//because we are building modelMapper BlogAppApisApplication class
	//The same ModelMapper bean can be reused across multiple components,
	//rather than creating a new instance every time.
	//ModelMapper modelMapper = new ModelMapper(); // Create ModelMapper manually
    //YourService yourService = new YourService();
    //yourService.setModelMapper(modelMapper); // Inject using setter
	
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		//this reffers to UserServiceImpl
		User user = this.dtoToUser(userDto); 
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		UserDto userDto1 = this.userToDto(updatedUser);
		
		return userDto1;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()->new ResourceNotFoundException("user","id",userId));
//why the return type is not list because
//findById is used for fetching a single record (one user),
//so no list is necessary. It returns an Optional<User>,
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		//take all saved users in user class
		List<User> users=this.userRepo.findAll();
//List<User> users it is used because of below 
//		List<User> users = [User(id=1, name="Alice", email="alice@example.com"), 
//		                    User(id=2, name="Bob", email="bob@example.com"),
//		                    User(id=3, name="Charlie", email="charlie@example.com")]

// another list is used to save modify data  to the new list not to old list
		List<UserDto> userDtos = users.stream().map((user)->this.userToDto(user)).collect(Collectors.toList());
//		List<UserDto> userDtos = [UserDto(id=1, name="Alice", email="alice@example.com"), 
//        UserDto(id=2, name="Bob", email="bob@example.com"),
//        UserDto(id=3, name="Charlie", email="charlie@example.com")]	
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("user","id",userId));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		//in map we have to pass the resource which should be convert
		//2nd argument we the class literals which should be convert to 
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	//without using modelMapper
	//		User user = new User();
	//		
	//		user.setId(userDto.getId());
	//		user.setName(userDto.getName());
	//		user.setEmail(userDto.getEmail());
	//		user.setAbout(userDto.getAbout());
	//		user.setPassword(userDto.getPassword());
	//		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
//		UserDto userDto = new UserDto();
//		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		//encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//to set roles
		//.get() will be used to avoid the optional class
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, UserDto.class);
	}
}
