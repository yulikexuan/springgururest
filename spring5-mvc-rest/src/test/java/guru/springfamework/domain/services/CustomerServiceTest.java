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

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
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
		Random random = new Random(System.currentTimeMillis());
		Long id = random.nextLong();
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

}///:~