package com.backend_learning.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend_learning.blog.entitiesORModeles.Category;
import com.backend_learning.blog.exceptions.ResourceNotFoundException;
import com.backend_learning.blog.payloads.CategoryDto;
import com.backend_learning.blog.repositories.CategoryRepo;
import com.backend_learning.blog.services.CategoryService;

@Service
public class CategoryServiceImple implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = this.categoryRepo.save(cat);
		//sending data of Category class to CategoryDto
		return this.modelMapper.map(addedCat, CategoryDto.class);  
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("Category", "CategoryId", categoryId));
		cat.setCategoryTitle(categoryDto.getCategoryTitle());
		cat.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category updatedcat = this.categoryRepo.save(cat);
		
		return this.modelMapper.map(updatedcat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category cat=this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryDto", categoryId));
		this.categoryRepo.delete(cat);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category", "CategoryDto", categoryId));
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
    public List<CategoryDto> getCategories() {
        // Fetch all categories by using map
        List<Category> categories = this.categoryRepo.findAll();
        // Convert list of entities to list of DTOs
        List<CategoryDto> catDtos = categories.stream()
        .map(cat -> this.modelMapper.map(cat, CategoryDto.class))
        .collect(Collectors.toList());
        
        return catDtos; 
    }
}
