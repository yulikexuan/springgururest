//: guru.springfamework.domain.services.CustomerServiceTest.java


package guru.springfamework.domain.services;


import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.repositories.ICustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


public class CustomerServiceTest {

	@Mock
	private ICustomerRepository customerRepository;

	@InjectMocks
	private CustomerService customerService;
	private Random random;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.random = new Random(System.currentTimeMillis());
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
		Long id = this.random.nextLong();
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstname("");
		customer.setLastname("");

		when(this.customerRepository.findById(id)).thenReturn(
				Optional.of(customer));

		// When
		Customer result = this.customerService.getCustomerById(id);

		// Then
		assertThat(result.getId(), is(id));

	}

	@Test
	public void able_To_Create_A_New_Customer() {

		// Given
		Long id = this.random.nextLong();
		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstname("");
		customer.setLastname("");

		// Given
		Customer newCreated = new Customer();
		customer.setId(id);

		when(this.customerRepository.save(customer)).thenReturn(newCreated);

		// When
		Customer result = this.customerService.createNewCustomer(customer);

		// Then
		assertThat(result, is(newCreated));
	}

}///:~