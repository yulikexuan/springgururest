//: guru.springframework.spring5reactiverest.controllers.CategoryControllerTest.java


package guru.springframework.spring5reactiverest.controllers;


import guru.springframework.spring5reactiverest.domain.model.Category;
import guru.springframework.spring5reactiverest.domain.repositories.ICategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


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
		this.webTestClient.get().uri("/api/v1/categories/")
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

}///:~