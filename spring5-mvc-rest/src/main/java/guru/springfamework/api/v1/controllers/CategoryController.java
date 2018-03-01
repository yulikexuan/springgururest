//: guru.springfamework.api.v1.controllers.CategoryController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.domain.services.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping(Mappings.API_V1_CATEGORIES)
public class CategoryController {

	private final ICategoryService categoryService;

	public CategoryController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	public ResponseEntity<CategoryListDTO> getAllCategories() {

		List<CategoryDTO> categories = this.categoryService.getAllCategories();

		CategoryListDTO categoryListDTO = new CategoryListDTO(categories);

		return new ResponseEntity<>(categoryListDTO, HttpStatus.OK);
	}

	@GetMapping("/{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(
			@PathVariable String name) {

		CategoryDTO category = this.categoryService.getCategoryByName(name);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

}///:~