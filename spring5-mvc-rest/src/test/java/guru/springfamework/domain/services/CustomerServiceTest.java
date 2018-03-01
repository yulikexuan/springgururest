//: guru.springfamework.domain.services.CustomerServiceTest.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.mappers.ICustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.repositories.ICustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CustomerServiceTest {

	@Mock
	private ICustomerRepository customerRepository;

	private ICustomerMapper customerMapper;

	private CustomerService customerService;

	private Random random;
	private Long id;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.customerMapper = ICustomerMapper.INSTANCE;
		this.customerService = new CustomerService(this.customerRepository,
				this.customerMapper);

		this.random = new Random(System.currentTimeMillis());
		this.id = this.random.nextLong();
	}

	@Test
	public void getAllCustomers() {

		// Given
		List<Customer> customers = Arrays.asList(new Customer(),
				new Customer());

		when(this.customerRepository.findAll()).thenReturn(customers);

		// When
		List<CustomerDTO> result = this.customerService.getAllCustomers();

		// Then
		assertThat(customers.size(), is(result.size()));
	}

	@Test
	public void getCustomerById() {

		// Given
		Customer customer = new Customer();
		customer.setId(this.id);
		customer.setFirstname("");
		customer.setLastname("");

		when(this.customerRepository.findById(this.id)).thenReturn(
				Optional.of(customer));

		// When
		CustomerDTO result = this.customerService.getCustomerById(this.id);

		// Then
		assertThat(result.getCustomerUrl(),
				containsString("/" + this.id));
	}

	@Test
	public void able_To_Create_A_New_Customer() {

		// Given
		Customer customer = new Customer();
		customer.setFirstname(UUID.randomUUID().toString());
		customer.setLastname(UUID.randomUUID().toString());

		CustomerDTO input = this.customerMapper.toCustomerDTO(customer);

		// Given
		Customer newCreated = new Customer();
		newCreated.setId(this.id);
		newCreated.setFirstname(customer.getFirstname());
		newCreated.setLastname(customer.getLastname());

		when(this.customerRepository.save(customer)).thenReturn(newCreated);

		// When
		CustomerDTO result = this.customerService.createNewCustomer(input);

		// Then
		assertThat(result.getCustomerUrl(), endsWith("/" + this.id));
		assertThat(result.getFirstname(), is(newCreated.getFirstname()));
		assertThat(result.getLastname(), is(newCreated.getLastname()));
	}

	@Test
	public void able_To_Save_A_New_Customer_Into_Database() {

		// Given
		Customer customer = new Customer();
		customer.setFirstname(UUID.randomUUID().toString());
		customer.setLastname(UUID.randomUUID().toString());

		CustomerDTO input = CustomerDTO.CustomerDTOBuilder.getInstance()
				.setFirstname(customer.getFirstname())
				.setLastname(customer.getLastname())
				.createCustomerDTO();

		Customer savedCustomer = new Customer();

		savedCustomer.setId(this.id);
		savedCustomer.setFirstname(customer.getFirstname());
		savedCustomer.setLastname(customer.getLastname());

		when(this.customerRepository.save(customer)).thenReturn(savedCustomer);

		// When
		CustomerDTO result = this.customerService.createNewCustomer(input);

		// Then
		assertThat(result.getFirstname(), is(customer.getFirstname()));
		assertThat(result.getLastname(), is(customer.getLastname()));
	}

	@Test
	public void able_To_Update_An_Existing_Customer() {

		// Given
		Customer customer = new Customer();
		customer.setId(this.id);
		customer.setFirstname(UUID.randomUUID().toString());
		customer.setLastname(UUID.randomUUID().toString());

		CustomerDTO input = this.customerMapper.toCustomerDTO(customer);

		when(this.customerRepository.existsById(this.id)).thenReturn(true);

		Customer savedCustomer = new Customer();
		savedCustomer.setId(customer.getId());
		savedCustomer.setFirstname(customer.getFirstname());
		savedCustomer.setLastname(customer.getLastname());

		when(this.customerRepository.save(customer)).thenReturn(savedCustomer);

		// When
		CustomerDTO result = this.customerService.updateCustomer(this.id, input);

		// Then
		assertThat(result.getFirstname(), is(savedCustomer.getFirstname()));
		assertThat(result.getLastname(), is(savedCustomer.getLastname()));
		assertThat(result.getCustomerUrl(),
				containsString("/" + this.id));
	}

	@Test(expected = RuntimeException.class)
	public void id_Should_Exist_In_Repository_When_Updating() {

		// Given
		CustomerDTO customerDTO = CustomerDTO.CustomerDTOBuilder
				.getInstance()
				.setFirstname("")
				.setLastname("")
				.createCustomerDTO();

		when(this.customerRepository.existsById(this.id)).thenReturn(false);

		// When
		this.customerService.updateCustomer(this.id, customerDTO);
	}

	@Test
	public void able_To_Patch_An_Existing_Customer_With_First_Name() {

		// Given
		Customer customer = new Customer();
		customer.setId(this.id);
		String firstname = UUID.randomUUID().toString();
		customer.setFirstname(firstname);

		CustomerDTO customerDTO = this.customerMapper.toCustomerDTO(customer);

		Customer existing = new Customer();
		existing.setId(this.id);
		String originalFirstname = UUID.randomUUID().toString();
		String originalLastname = UUID.randomUUID().toString();
		existing.setFirstname(originalFirstname);
		existing.setLastname(originalLastname);

		when(this.customerRepository.findById(this.id))
				.thenReturn(Optional.of(existing));

		when(this.customerRepository.save(existing)).thenReturn(existing);

		// When
		CustomerDTO result = this.customerService.patchCustomer(this.id,
				customerDTO);

		// Then
		assertThat(result.getLastname(), is(existing.getLastname()));
		assertThat(existing.getFirstname(), is(customer.getFirstname()));
	}

	@Test
	public void not_Able_To_Patch_Null() {

		// When
		this.customerService.patchCustomer(this.id, null);

		// Then
		verify(this.customerRepository, never()).findById(anyLong());
	}

	@Test
	public void able_Delete_An_Customer_By_Id() {

		// Given


		// When
		this.customerService.deleteCustomer(this.id);

		// Then
		verify(this.customerRepository).deleteById(this.id);

	}

}///:~