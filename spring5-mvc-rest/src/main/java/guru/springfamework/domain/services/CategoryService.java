//: guru.springfamework.domain.services.CategoryService.java


package guru.springfamework.domain.services;


import guru.springfamework.domain.model.Category;
import guru.springfamework.domain.repositories.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryService implements ICategoryService {

	private final ICategoryRepository categoryRepository;

	public CategoryService(ICategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = this.categoryRepository.findAll();
		return categories;
	}

	@Override
	public Category getCategoryByName(String name) {
		return this.categoryRepository.findByName(name);
	}

}///:~