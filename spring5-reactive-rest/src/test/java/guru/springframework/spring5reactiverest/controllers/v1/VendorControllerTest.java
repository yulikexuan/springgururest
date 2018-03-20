//: guru.springframework.spring5reactiverest.controllers.v1.VendorControllerTest.java


package guru.springframework.spring5reactiverest.controllers.v1;


import guru.springframework.spring5reactiverest.domain.model.Vendor;
import guru.springframework.spring5reactiverest.domain.repositories.IVendorRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


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

	@Test
	public void able_To_Create_New_Vendors() throws Exception {

		// Given
		Mono<Vendor> toBeSaved = Mono.just(Vendor.builder()
				.firstName("Mike")
				.lastName("Lee")
				.build());

		given(this.vendorRepository.saveAll(any(Publisher.class)))
				.willReturn(Flux.just(Vendor.builder().build()));

		// When and Then
		this.webTestClient.post()
				.uri("/api/v1/vendors")
				.body(toBeSaved, Vendor.class)
				.exchange()
				.expectStatus()
				.isCreated();
	}

	@Test
	public void able_To_Update_An_Existing_Vendor() throws Exception {

		// Given
		given(this.vendorRepository.save(any(Vendor.class)))
				.willReturn(Mono.just(Vendor.builder().build()));

		Vendor vendor = Vendor.builder()
				.firstName("Mike")
				.lastName("Lee").build();
		Mono<Vendor> toBeUpdated = Mono.just(vendor);

		// When & Then
		this.webTestClient.put()
				.uri("/api/v1/vendors/lsafdjsla")
				.body(toBeUpdated, Vendor.class)
				.exchange()
				.expectStatus()
				.isOk();
	}

	@Test
	public void able_To_Patch_An_Existing_Vendor() throws Exception {

		// Given
		String firstname = UUID.randomUUID().toString();
		String lastname = UUID.randomUUID().toString();
		String id = UUID.randomUUID().toString();

		Vendor existing = Vendor.builder().build();
		given(this.vendorRepository.findById(id))
				.willReturn(Mono.just(existing));

		given(this.vendorRepository.save(existing))
				.willReturn(Mono.just(Vendor.builder().build()));

		Vendor vendor = Vendor.builder()
				.firstName(firstname)
				.lastName(lastname)
				.build();
		Mono<Vendor> toBeUpdated = Mono.just(vendor);

		// When
		this.webTestClient.patch()
				.uri("/api/v1/vendors/" + id)
				.body(toBeUpdated, Vendor.class)
				.exchange()
				.expectStatus()
				.isOk();

		// Then
		assertThat(existing.getFirstName(), is(firstname));
		assertThat(existing.getLastName(), is(lastname));
	}

}///:~