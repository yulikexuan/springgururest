//: guru.springfamework.domain.services.CustomerServiceTest.java


package guru.springfamework.domain.services;


import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.repositories.ICustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class CustomerServiceTest {

	@Mock
	private ICustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;
	private Random random;
	private Long id;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.random = new Random(System.currentTimeMillis());
		this.id = this.random.nextLong();
	}

	@Test
	public void getAllCustomers() {

		// Given
		List<Customer> customers = Arrays.asList(new Customer(), new Customer());

		when(this.customerRepository.findAll()).thenReturn(customers);

		// When
		List<Customer> result = this.customerService.getAllCustomers();

		// Then
		assertThat(customers.size(), is(result.size()));
		assertThat(result.get(0), is(customers.get(0)));
		assertThat(result.get(1), is(customers.get(1)));
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
		Customer result = this.customerService.getCustomerById(this.id);

		// Then
		assertThat(result.getId(), is(this.id));

	}

	@Test
	public void able_To_Create_A_New_Customer() {

		// Given
		Customer customer = new Customer();
		customer.setId(this.id);
		customer.setFirstname("");
		customer.setLastname("");

		// Given
		Customer newCreated = new Customer();
		customer.setId(this.id);

		when(this.customerRepository.save(customer)).thenReturn(newCreated);

		// When
		Customer result = this.customerService.createNewCustomer(customer);

		// Then
		assertThat(result, is(newCreated));
	}

	@Test
	public void able_To_Save_A_New_Customer_Into_Database() {

		// Given
		Customer customer = new Customer();
		customer.setFirstname(UUID.randomUUID().toString());
		customer.setLastname(UUID.randomUUID().toString());

		Customer savedCustomer = new Customer();

		savedCustomer.setId(this.id);
		savedCustomer.setFirstname(customer.getFirstname());
		savedCustomer.setLastname(customer.getLastname());

		when(this.customerRepository.save(customer)).thenReturn(savedCustomer);

		// When
		Customer result = this.customerService.createNewCustomer(customer);

		// Then
		assertThat(result.getId(), is(this.id));
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

		when(this.customerRepository.existsById(this.id)).thenReturn(true);

		Customer savedCustomer = new Customer();
		savedCustomer.setId(customer.getId());
		savedCustomer.setFirstname(customer.getFirstname());
		savedCustomer.setLastname(customer.getLastname());

		when(this.customerRepository.save(customer)).thenReturn(savedCustomer);

		// When
		Customer result = this.customerService.updateCustomer(customer);

		// Then
		assertThat(result, is(savedCustomer));
	}

	@Test(expected = RuntimeException.class)
	public void id_Should_Exist_In_Repository_When_Updating() {

		// Given
		Customer customer = new Customer();
		customer.setId(this.id);

		when(this.customerRepository.existsById(this.id)).thenReturn(false);

		// When
		this.customerService.updateCustomer(customer);
	}

	@Test
	public void able_To_Patch_An_Existing_Customer_With_First_Name() {

		// Given
		Customer input = new Customer();
		input.setId(this.id);
		String firstname = UUID.randomUUID().toString();
		input.setFirstname(firstname);

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
		Customer result = this.customerService.patchCustomer(input);

		// Then
		assertThat(result, is(existing));
		assertThat(existing.getId(), is(input.getId()));
		assertThat(existing.getFirstname(), is(input.getFirstname()));
	}

	@Test
	public void not_Able_To_Patch_Null() {

		// When
		this.customerService.patchCustomer(null);

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