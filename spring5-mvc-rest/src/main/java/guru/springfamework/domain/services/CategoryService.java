//: guru.springfamework.domain.services.CategoryService.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.mappers.ICategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.model.Category;
import guru.springfamework.domain.repositories.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService implements ICategoryService {

	private final ICategoryRepository categoryRepository;
	private final ICategoryMapper categoryMapper;

	public CategoryService(ICategoryRepository categoryRepository,
	                       ICategoryMapper categoryMapper) {
		this.categoryRepository = categoryRepository;
		this.categoryMapper = categoryMapper;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = this.categoryRepository.findAll();
		return categories.stream()
				.map(this.categoryMapper::toCategoryDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {

		Category category = this.categoryRepository.findByName(name);

		if (category == null) {
			throw new ResourceNotFoundException(name +
					" category was not found.");
		}

		return this.categoryMapper.toCategoryDTO(category);
	}

}///:~