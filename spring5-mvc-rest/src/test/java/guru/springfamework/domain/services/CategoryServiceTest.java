//: guru.springfamework.domain.services.CategoryServiceTest.java


package guru.springfamework.domain.services;


import guru.springfamework.domain.model.Category;
import guru.springfamework.domain.repositories.ICategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


public class CategoryServiceTest {

	static final Long ID = 1L;
	static final String NAME = "Jimmy";

	@Mock
	private ICategoryRepository categoryRepository;

	@InjectMocks
	private CategoryService categoryService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllCategories() {

		// Given
		List<Category> categories = Arrays.asList(new Category(),
				new Category(), new Category());

		when(this.categoryRepository.findAll()).thenReturn(categories);

		// When
		List<Category> result = this.categoryService.getAllCategories();

		// Then
		assertThat(result.size(), is(3));
		assertThat(result.get(0), is(categories.get(0)));
		assertThat(result.get(1), is(categories.get(1)));
		assertThat(result.get(2), is(categories.get(2)));
	}

	@Test
	public void getCategoryByName() {

		// Given
		Category category = new Category();
		category.setId(ID);
		category.setName(NAME);

		when(this.categoryRepository.findByName(NAME)).thenReturn(category);

		// When
		Category result = this.categoryService.getCategoryByName(NAME);

		// Then
		assertThat(result.getId(), is(ID));
		assertThat(result.getName(), is(NAME));
		assertThat(result, is(category));
	}

}///:~