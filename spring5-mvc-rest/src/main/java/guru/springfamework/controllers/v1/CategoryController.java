//: guru.springfamework.controllers.v1.CategoryController.java


package guru.springfamework.controllers.v1;


import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CategoryListDTO;
import guru.springfamework.services.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping(CategoryController.Mappings.API_V1_CATEGORIES)
public class CategoryController {

	public static final class Mappings {

		private Mappings() {}

		public static final String API_V1_CATEGORIES = "/api/v1/categories/";

	}

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

	@GetMapping("{name}")
	public ResponseEntity<CategoryDTO> getCategoryByName(
			@PathVariable String name) {

		CategoryDTO dto = this.categoryService.getCategoryByName(name);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}///:~