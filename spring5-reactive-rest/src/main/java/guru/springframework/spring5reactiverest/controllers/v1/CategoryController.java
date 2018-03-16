//: guru.springframework.spring5reactiverest.controllers.v1.CategoryController.java


package guru.springframework.spring5reactiverest.controllers.v1;


import guru.springframework.spring5reactiverest.domain.model.Category;
import guru.springframework.spring5reactiverest.domain.repositories.ICategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Slf4j
@RestController
public class CategoryController {

	private final ICategoryRepository categoryRepository;

	@Autowired
	public CategoryController(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/api/v1/categories")
	public Flux<Category> list() {
		return this.categoryRepository.findAll();
	}

	@GetMapping("/api/v1/categories/{id}")
	public Mono<Category> getById(@PathVariable String id) {
		return this.categoryRepository.findById(id);
	}

}///:~