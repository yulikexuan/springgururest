//: guru.springfamework.services.CategoryService.java


package guru.springfamework.services;


import guru.springfamework.api.v1.mapper.ICategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService implements ICategoryService {

	private final ICategoryMapper categoryMapper;
	private final ICategoryRepository categoryRepository;

	public CategoryService(ICategoryMapper categoryMapper,
	                       ICategoryRepository categoryRepository) {

		this.categoryMapper = categoryMapper;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {

		List<Category> categories = this.categoryRepository.findAll();

		List<CategoryDTO> categoryDTOs = categories.stream()
				.map(this.categoryMapper::toCategoryDTO)
				.collect(Collectors.toList());

		return categoryDTOs;
	}

	@Override
	public CategoryDTO getCategoryByName(String name) {

		Category category = this.categoryRepository.findByName(name);
		CategoryDTO categoryDTO = this.categoryMapper.toCategoryDTO(category);

		return categoryDTO;
	}

}///:~