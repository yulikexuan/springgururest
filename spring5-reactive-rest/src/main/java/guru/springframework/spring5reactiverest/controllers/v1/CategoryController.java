//: guru.springframework.spring5reactiverest.controllers.v1.CategoryController.java


package guru.springframework.spring5reactiverest.controllers.v1;


import guru.springframework.spring5reactiverest.domain.model.Category;
import guru.springframework.spring5reactiverest.domain.repositories.ICategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
	Flux<Category> list() {
		return this.categoryRepository.findAll();
	}

	@GetMapping("/api/v1/categories/{id}")
	Mono<Category> getById(@PathVariable String id) {
		return this.categoryRepository.findById(id);
	}

	@PostMapping("/api/v1/categories")
	@ResponseStatus(HttpStatus.CREATED)
	Mono<Void> create(@RequestBody Publisher<Category> categoryStream) {
		/*
		 * Flux::then(): Return a {@code Mono<Void>} that completes when this
		 * Flux completes.
		 * This will actively ignore the sequence and only replay completion or
		 * error signals
		 */
		return this.categoryRepository.saveAll(categoryStream).then();
	}

}///:~