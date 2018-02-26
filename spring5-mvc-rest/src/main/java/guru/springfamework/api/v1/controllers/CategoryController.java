//: guru.springfamework.api.v1.controllers.CategoryController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.mappers.ICategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.domain.model.Category;
import guru.springfamework.domain.services.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(Mappings.API_V1_CATEGORIES)
public class CategoryController {

	private final ICategoryService categoryService;
	private final ICategoryMapper categoryMapper;

	public CategoryController(ICategoryService categoryService,
	                          ICategoryMapper categoryMapper) {
		this.categoryService = categoryService;
		this.categoryMapper = categoryMapper;
	}

	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories() {

		List<Category> categories = this.categoryService.getAllCategories();

		List<CategoryDTO> categoryDTOs = categories.stream()
				.map(this.categoryMapper::toCategoryDTO)
				.collect(Collectors.toList());

		CategoryListDTO categoryListDTO = new CategoryListDTO(categoryDTOs);

		return new ResponseEntity<>(categoryListDTO, HttpStatus.OK);
	}

	@GetMapping("/{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(
			@PathVariable String name) {

		Category category = this.categoryService.getCategoryByName(name);
		CategoryDTO dto = this.categoryMapper.toCategoryDTO(category);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}///:~