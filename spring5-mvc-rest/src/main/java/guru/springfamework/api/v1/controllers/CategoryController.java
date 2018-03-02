//: guru.springfamework.api.v1.controllers.CategoryController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.domain.services.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(Mappings.API_V1_CATEGORIES)
public class CategoryController {

	private final ICategoryService categoryService;

	public CategoryController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CategoryListDTO getAllCategories() {

		List<CategoryDTO> categories = this.categoryService.getAllCategories();

		CategoryListDTO categoryListDTO = new CategoryListDTO(categories);

		return categoryListDTO;
	}

	@GetMapping("/{name}")
	@ResponseStatus(HttpStatus.OK)
	public CategoryDTO getCategoryByName(
			@PathVariable String name) {

		CategoryDTO category = this.categoryService.getCategoryByName(name);
		return category;
	}

}///:~