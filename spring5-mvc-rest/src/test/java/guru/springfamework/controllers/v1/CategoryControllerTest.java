//: guru.springfamework.controllers.v1.CategoryControllerTest.java


package guru.springfamework.controllers.v1;


import com.jayway.jsonpath.JsonPath;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.services.ICategoryService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class CategoryControllerTest {

	public static final String NAME = "Jim";

	@Mock
	private ICategoryService categoryService;

	/*
	 * @InjectMocks creates an instance of the class and injects the mocks that
	 * are created with the @Mock (or @Spy) annotations into this instance
	 */
	@InjectMocks
	private CategoryController categoryController;

	private Random random;

	private MockMvc mockMvc;

	private String name_0;
	private String name_1;

	private Long id_0;
	private Long id_1;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		this.random = new Random(System.currentTimeMillis());
		this.mockMvc = MockMvcBuilders.standaloneSetup(
				this.categoryController).build();

		this.name_0 = UUID.randomUUID().toString();
		this.name_1 = UUID.randomUUID().toString();

		this.id_0 = this.random.nextLong();
		this.id_1 = this.random.nextLong();
	}

	@Test
	public void has_Endpoint_For_All_Categories() throws Exception {

		// Given
		CategoryDTO categoryDTO_0 = new CategoryDTO();
		CategoryDTO categoryDTO_1 = new CategoryDTO();

		categoryDTO_0.setId(this.id_0);
		categoryDTO_0.setName(this.name_0);

		categoryDTO_1.setId(this.id_1);
		categoryDTO_1.setName(this.name_1);

		List<CategoryDTO> categoryDTOS = Arrays.asList(categoryDTO_0,
				categoryDTO_1);

		when(this.categoryService.getAllCategories()).thenReturn(categoryDTOS);

		// When
		this.mockMvc.perform(get(CategoryController.Mappings.API_V1_CATEGORIES)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.categories", hasSize(2)))
				.andExpect(jsonPath("$.categories[*].name",
						containsInAnyOrder(this.name_0, this.name_1)))
				.andExpect(jsonPath("$.categories[*].id",
						containsInAnyOrder(this.id_0, this.id_1)));

	}// End of has_Endpoint_For_All_Categories()

	@Test
	public void has_Endpoint_For_Category_By_Name() throws Exception {
		
		// Given
        String name = UUID.randomUUID().toString();
        String uri = CategoryController.Mappings.API_V1_CATEGORIES + name;
		Long id = this.random.nextLong();
        CategoryDTO dto = new CategoryDTO();
        dto.setName(name);
        dto.setId(id);

        when(this.categoryService.getCategoryByName(name)).thenReturn(dto);

		// When
		this.mockMvc.perform(get(uri)
				        .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(name)))
				.andExpect(jsonPath("$.id", is(id)));

	}// has_Endpoint_For_Category_By_Name()

}///:~