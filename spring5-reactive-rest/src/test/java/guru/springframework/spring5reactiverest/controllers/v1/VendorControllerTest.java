//: guru.springframework.spring5reactiverest.controllers.v1.VendorControllerTest.java


package guru.springframework.spring5reactiverest.controllers.v1;


import guru.springframework.spring5reactiverest.domain.model.Vendor;
import guru.springframework.spring5reactiverest.domain.repositories.IVendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;


public class VendorControllerTest {

	private WebTestClient webTestClient;

	@Mock
	private IVendorRepository vendorRepository;

	private VendorController vendorController;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.vendorController = new VendorController(this.vendorRepository);
		this.webTestClient = WebTestClient.bindToController(
				this.vendorController).build();
	}

	@Test
	public void list() throws Exception {

		// Given
		given(this.vendorRepository.findAll())
				.willReturn(Flux.just(
						Vendor.builder()
								.firstName("Mike")
								.lastName("Lee")
								.build(),
						Vendor.builder()
								.firstName("John")
								.lastName("Bill")
								.build()));

		// When & Then
		this.webTestClient.get()
				.uri("/api/v1/vendors")
				.exchange()
				.expectBodyList(Vendor.class)
				.hasSize(2);

	}

	@Test
	public void getById() throws Exception {

		// Given
		given(this.vendorRepository.findById("someId"))
				.willReturn(Mono.just(Vendor.builder()
				.firstName("Gates")
				.lastName("Bill")
				.build()));

		// When & Then
		this.webTestClient.get()
				.uri("/api/v1/vendors/someId")
				.exchange()
				.expectBody(Vendor.class);
	}

}///:~