//: guru.springfamework.controllers.v1.CategoryController.java


package guru.springfamework.controllers.v1;


import guru.springfamework.services.ICategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v1/categories/")
public class CategoryController {

	private final ICategoryService categoryService;

	public CategoryController(ICategoryService categoryService) {
		this.categoryService = categoryService;
	}



}///:~