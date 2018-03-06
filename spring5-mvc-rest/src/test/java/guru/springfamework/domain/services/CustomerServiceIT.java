//: guru.springfamework.domain.services.CustomerServiceIT.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.mappers.ICustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.repositories.ICategoryRepository;
import guru.springfamework.domain.repositories.ICustomerRepository;
import guru.springfamework.domain.repositories.IVendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerServiceIT {

	@Autowired
	private ICustomerRepository customerRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private IVendorRepository vendorRepository;

	private CustomerService customerService;
	private ICustomerMapper customerMapper;

	@Before
	public void setUp() {

		log.debug(">>>>>>> Customer data size: " +
				this.customerRepository.findAll().size());
		log.debug(">>>>>>> Loading customer data for testing ... ... ");

		new Bootstrap(this.categoryRepository, this.customerRepository,
				this.vendorRepository).run();

		log.debug(">>>>>>> Customer data size after loading data: " +
				this.customerRepository.findAll().size());

		this.customerMapper = ICustomerMapper.INSTANCE;
		this.customerService = new CustomerService(this.customerRepository,
				this.customerMapper);
	}

	private Long get_ID_Of_The_First_Customer_In_Cuatomer_Repository() {
		return this.customerRepository.findAll().get(0).getId();
	}

	@Test
	public void is_Able_To_Patch_First_Name() {

		// Given
		String newFirstname = UUID.randomUUID().toString();
		Long id = this.get_ID_Of_The_First_Customer_In_Cuatomer_Repository();

		Customer origin = this.customerRepository.getOne(id);
		String originLastname = origin.getLastname();

		Customer customer = new Customer();
		customer.setId(id);
		customer.setFirstname(newFirstname);
		CustomerDTO input = this.customerMapper.toCustomerDTO(customer);

		// When
		CustomerDTO output = this.customerService.patchCustomer(id, input);

		// Then
		assertThat(output.getCustomerUrl(), containsString("/" + id));
		assertThat(output.getFirstname(), is(customer.getFirstname()));
		assertThat(output.getLastname(), is(originLastname));
	}

	@Test
	public void is_Able_To_Patch_Last_Name() {

		// Given
		String newLastname = UUID.randomUUID().toString();
		Long id = this.get_ID_Of_The_First_Customer_In_Cuatomer_Repository();
		Customer origin = this.customerRepository.getOne(id);
		String originFirstname = origin.getFirstname();

		Customer customer = new Customer();
		customer.setId(id);
		customer.setLastname(newLastname);
		CustomerDTO input = this.customerMapper.toCustomerDTO(customer);

		// When
		CustomerDTO output = this.customerService.patchCustomer(id, input);

		// Then
		assertThat(output.getCustomerUrl(), containsString("/" + id));
		assertThat(output.getFirstname(), is(originFirstname));
		assertThat(output.getLastname(), is(input.getLastname()));
	}

	@Test
	public void able_To_Delete_Customer_By_Id() {

		// Given
		Long id = this.get_ID_Of_The_First_Customer_In_Cuatomer_Repository();

		// When
		this.customerService.deleteCustomer(id);

		// Then
		boolean deleted = !this.customerRepository.existsById(id);
		assertThat(deleted, is(true));
	}

}///:~