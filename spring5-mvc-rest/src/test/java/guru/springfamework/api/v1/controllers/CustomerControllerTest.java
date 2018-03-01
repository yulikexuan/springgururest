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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
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

	@InjectMocks
	private CustomerController customerController;
	private Long id;
	private Long id_1;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		this.random = new Random(System.currentTimeMillis());

		this.mockMvc = MockMvcBuilders.standaloneSetup(
				this.customerController).build();

		this.customerMapper = ICustomerMapper.INSTANCE;

		this.id = this.random.nextLong();
		this.id_1 = this.random.nextLong();
	}

	@Test
	public void getAllCustomers() throws Exception {


		// Given
		CustomerDTO customerDTO_0 = CustomerDTO.CustomerDTOBuilder
				.getInstance()
				.createCustomerDTO();
		CustomerDTO customerDTO_1 = CustomerDTO.CustomerDTOBuilder
				.getInstance()
				.createCustomerDTO();

		List<CustomerDTO> customers = Arrays.asList(customerDTO_0,
				customerDTO_1);

		when(this.customerService.getAllCustomers()).thenReturn(customers);

		// When
		this.mockMvc.perform(get(Mappings.API_V1_CUSTOMERS)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}

	@Test
	public void getCustomerById() throws Exception {

		// Given
		Customer customer = new Customer();
		customer.setId(this.id);

		CustomerDTO customerDTO = this.customerMapper.toCustomerDTO(customer);

		when(this.customerService.getCustomerById(this.id)).thenReturn(
				customerDTO);

		// When
		this.mockMvc.perform(get(
				Mappings.API_V1_CUSTOMERS + "/" + id)
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
		Customer customer = new Customer();
		customer.setFirstname(firstname);
		customer.setLastname(lastname);

		CustomerDTO input = this.customerMapper.toCustomerDTO(customer);
		String rawInput = ObjectToJsonMapper.toJson(input);

		customer.setId(this.id);

		CustomerDTO savedCustomer = this.customerMapper.toCustomerDTO(customer);

		when(this.customerService.createNewCustomer(input))
				.thenReturn(savedCustomer);

		String uriStr = getUriStr(id);

		// When & Then
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.post(Mappings.API_V1_CUSTOMERS)
						.contentType(MediaType.APPLICATION_JSON)
						.content(rawInput);

		ResultActions resultActions = this.mockMvc.perform(requestBuilder);

		resultActions.andExpect(status().isCreated())
				.andExpect(jsonPath("$.firstname", is(firstname)))
				.andExpect(jsonPath("$.lastname", is(lastname)))
				.andExpect(jsonPath("$.customerUrl", is(uriStr)));

	}// able_To_Handle_Post_Of_New_Customer()

	@Test
	public void able_To_Handle_Put_Of_Existing_Customer() throws Exception {

		// Given
		String uriStr = getUriStr(this.id);

		String firstname = "Jobs";
		String lastname = "Steve";
		Customer customer = new Customer();
		customer.setFirstname(firstname);
		customer.setLastname(lastname);
		CustomerDTO input = this.customerMapper.toCustomerDTO(customer);

		String rawInput = ObjectToJsonMapper.toJson(input);

		customer.setId(this.id);
		CustomerDTO after = this.customerMapper.toCustomerDTO(customer);

		when(this.customerService.updateCustomer(this.id, input))
				.thenReturn(after);

		// When & Then
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.put(uriStr)
				.contentType(MediaType.APPLICATION_JSON)
				.content(rawInput);

		ResultActions resultActions = this.mockMvc.perform(requestBuilder);

		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", is(firstname)))
				.andExpect(jsonPath("$.lastname", is(lastname)))
				.andExpect(jsonPath("$.customerUrl", is(uriStr)));

	}// able_To_Handle_Put_Of_Existing_Customer()

	@Test
	public void able_To_Handle_Patch_Of_Existing_Customer() throws Exception {

		// Given
		String uriStr = this.getUriStr(this.id);
		String firstname = "Mike";
		String lastname = "Lee";

		Customer customer = new Customer();
		customer.setFirstname(firstname);

		CustomerDTO input = this.customerMapper.toCustomerDTO(customer);
		String rawInput = ObjectToJsonMapper.toJson(input);

		Customer after = new Customer();
		after.setId(this.id);
		after.setFirstname(firstname);
		after.setLastname(lastname);

		CustomerDTO result = this.customerMapper.toCustomerDTO(after);

		when(this.customerService.patchCustomer(this.id, input))
				.thenReturn(result);

		// When & Then
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.patch(uriStr)
						.contentType(MediaType.APPLICATION_JSON)
						.content(rawInput);

		ResultActions resultActions = this.mockMvc.perform(requestBuilder);

		resultActions.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", is(firstname)))
				.andExpect(jsonPath("$.lastname", is(lastname)))
				.andExpect(jsonPath("$.customerUrl", is(uriStr)));

	}// able_To_Handle_Put_Of_Existing_Customer()

	@Test
	public void able_To_Handle_Delete() throws Exception {

		// Given
		Long id = this.random.nextLong();
		String uriStr = getUriStr(id);

		// When
		MockHttpServletRequestBuilder requestBuilder =
				MockMvcRequestBuilders.delete(uriStr);

		ResultActions requestActions = this.mockMvc.perform(requestBuilder);

		requestActions.andExpect(status().isOk());
	}

	private String getUriStr(Long id) {
		return UriComponentsBuilder.newInstance()
				.path(Mappings.API_V1_CUSTOMERS)
				.path("/")
				.path(Long.toString(id))
				.toUriString();
	}

}///:~