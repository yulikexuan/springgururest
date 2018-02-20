//: guru.springfamework.services.CategoryServiceTest.java


package guru.springfamework.services;


import guru.springfamework.api.v1.mapper.ICategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.ICategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class CategoryServiceTest {

	static final Long ID = 1L;
	static final String NAME = "Jimmy";

	@Mock
	private ICategoryRepository categoryRepository;

	private CategoryService categoryService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.categoryService = new CategoryService(ICategoryMapper.INSTANCE,
				this.categoryRepository);
	}

	@Test
	public void getAllCategories() {

		// Given
		List<Category> categories = Arrays.asList(new Category(),
				new Category(), new Category());

		when(this.categoryRepository.findAll()).thenReturn(categories);

		// When
		List<CategoryDTO> categoryDTOS = this.categoryService.getAllCategories();

		// Then
		assertThat(categoryDTOS.size(), is(categories.size()));
	}

	@Test
	public void getCategoryByName() {

		// Given
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);

		when(this.categoryRepository.findByName(NAME)).thenReturn(category);

		// When
		CategoryDTO categoryDTO = this.categoryService.getCategoryByName(NAME);

		// Then
		assertThat(categoryDTO.getId(), is(ID));
		assertThat(categoryDTO.getName(), is(NAME));
	}

}///:~