//: guru.springfamework.api.v1.controllers.CustomerControllerTest.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.mappers.ICustomerMapper;
import guru.springfamework.api.v1.mappers.ObjectToJsonMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.services.ICustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.Matchers.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CustomerControllerTest {

	@Mock
	private ICustomerService customerService;

	private ICustomerMapper customerMapper;

	private MockMvc mockMvc;
	private Random random;

	private CustomerController customerController;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		this.random = new Random(System.currentTimeMillis());
		this.customerMapper = ICustomerMapper.INSTANCE;

		this.customerController = new CustomerController(this.customerService,
				this.customerMapper);

		this.mockMvc = MockMvcBuilders.standaloneSetup(
				this.customerController).build();
	}

	@Test
	public void getAllCustomers() throws Exception {

		// Given
		Long id_0 = this.random.nextLong();
		Long id_1 = this.random.nextLong();

		Customer customer_0 = new Customer();
		Customer customer_1 = new Customer();

		customer_0.setId(id_0);
		customer_1.setId(id_1);

		List<Customer> customers = Arrays.asList(customer_0, customer_1);

		when(this.customerService.getAllCustomers()).thenReturn(customers);

		// When
		this.mockMvc.perform(get(Mappings.API_V1_CUSTOMERS)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)))
				.andExpect(jsonPath("$.customers[0].customerUrl",
						endsWith(Long.toString(id_0))))
				.andExpect(jsonPath("$.customers[0].customerUrl",
						startsWith(Mappings.API_V1_CUSTOMERS)))
				.andExpect(jsonPath("$.customers[1].customerUrl",
						endsWith(Long.toString(id_1))))
				.andExpect(jsonPath("$.customers[1].customerUrl",
						startsWith(Mappings.API_V1_CUSTOMERS)));
	}

	@Test
	public void getCustomerById() throws Exception {

		// Given
		Long id = this.random.nextLong();
		Customer customer = new Customer();
		customer.setId(id);

		when(this.customerService.getCustomerById(id)).thenReturn(customer);

		// When
		this.mockMvc.perform(get(Mappings.API_V1_CUSTOMERS + "/" + id)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.customerUrl",
						endsWith(Long.toString(id))))
				.andExpect(jsonPath("$.customerUrl",
						startsWith(Mappings.API_V1_CUSTOMERS)));
	}

	@Test
	public void able_To_Handle_Post_Of_New_Customer() throws Exception {

		// Given
		String firstname = "Jobs";
		String lastname = "Steve";
		CustomerDTO input = new CustomerDTO();
		input.setFirstname(firstname);
		input.setLastname(lastname);

		String rawInput = ObjectToJsonMapper.toJson(input);

		Customer inputCustomer = new Customer();
		inputCustomer.setFirstname(firstname);
		inputCustomer.setLastname(lastname);

		Long id = this.random.nextLong();
		Customer savedCustomer = new Customer();
		savedCustomer.setId(id);
		savedCustomer.setFirstname(firstname);
		savedCustomer.setLastname(lastname);

		when(this.customerService.createNewCustomer(inputCustomer))
				.thenReturn(savedCustomer);

		String uriStr = UriComponentsBuilder.newInstance()
				.path(Mappings.API_V1_CUSTOMERS)
				.path("/")
				.path(Long.toString(id))
				.toUriString();

		// When & Then
		mockMvc.perform(post(Mappings.API_V1_CUSTOMERS)
						.contentType(MediaType.APPLICATION_JSON)
						.content(rawInput))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstname", is(firstname)))
				.andExpect(jsonPath("$.lastname", is(lastname)))
				.andExpect(jsonPath("$.customerUrl", is(uriStr)));
	}

}///:~