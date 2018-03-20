//: guru.springframework.spring5reactiverest.controllers.v1.CategoryControllerTest.java


package guru.springframework.spring5reactiverest.controllers.v1;


import guru.springframework.spring5reactiverest.domain.model.Category;
import guru.springframework.spring5reactiverest.domain.repositories.ICategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;


public class CategoryControllerTest {

	private WebTestClient webTestClient;

	@Mock
	private ICategoryRepository categoryRepository;

	private CategoryController categoryController;

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		this.categoryController = new CategoryController(
				this.categoryRepository);

		this.webTestClient = WebTestClient.bindToController(
				this.categoryController)
				.build();
	}

	@Test
	public void able_To_Get_A_List_Of_Categories() throws Exception {

		// Given
		given(this.categoryRepository.findAll()).willReturn(
				Flux.just(Category.builder().description("CAT-1").build(),
						Category.builder().description("CAT-2").build()));

		// When & Then
		this.webTestClient.get().uri("/api/v1/categories")
				.exchange()
				.expectBodyList(Category.class)
				.hasSize(2);
	}

	@Test
	public void able_To_Get_The_Category_By_Id() throws Exception {

		// Given
		given(this.categoryRepository.findById("someid"))
				.willReturn(Mono.just(
						Category.builder()
								.description("CAT-3")
								.build()));
		// When & Then
		this.webTestClient.get().uri("/api/v1/categories/someid")
				.exchange()
				.expectBody(Category.class);
	}

	@Test
	public void able_To_Create_New_Categories() throws Exception {

		// Given
		given(this.categoryRepository.saveAll(any(Publisher.class)))
				.willReturn(Flux.just(Category.builder().build()));

		Mono<Category> toBeSaved = Mono.just(
				Category.builder().description("Some Cat").build());

		// When & Then
		this.webTestClient.post()
				.uri("/api/v1/categories")
				.body(toBeSaved, Category.class)
				.exchange()
				.expectStatus()
				.isCreated();
	}

	@Test
	public void able_To_Update_A_Existing_Category() throws Exception {

		// Given
		given(this.categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> toBeUpdated = Mono.just(
				Category.builder().description("some cat").build());

		// When & Then
		this.webTestClient.put()
				.uri("/api/v1/categories/rtyreyre")
				.body(toBeUpdated, Category.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void able_To_Update_Part_Of_A_Existing_Category() throws Exception {

		// Given
		String id = UUID.randomUUID().toString();

		Category existingCategory = Category.builder()
				.description("desc")
				.build();

		given(this.categoryRepository.findById(id))
				.willReturn(Mono.just(existingCategory));

		given(this.categoryRepository.save(any(Category.class)))
				.willReturn(Mono.just(Category.builder().build()));

		Mono<Category> toBeUpdated = Mono.just(
				Category.builder().description("newdesc").build());

		// When & Then
		this.webTestClient.patch()
				.uri("/api/v1/categories/" + id)
				.body(toBeUpdated, Category.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

}///:~