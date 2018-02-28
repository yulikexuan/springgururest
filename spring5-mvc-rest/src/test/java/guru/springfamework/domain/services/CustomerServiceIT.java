//: guru.springfamework.domain.services.CustomerServiceIT.java


package guru.springfamework.domain.services;


import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.repositories.ICategoryRepository;
import guru.springfamework.domain.repositories.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.UUID;

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

	private CustomerService customerService;

	@Before
	public void setUp() throws Exception {

		log.debug(">>>>>>> Customer data size: " +
				this.customerRepository.findAll().size());
		log.debug(">>>>>>> Loading customer data for testing ... ... ");

		new Bootstrap(this.categoryRepository, this.customerRepository).run();

		log.debug(">>>>>>> Customer data size after loading data: " +
				this.customerRepository.findAll().size());

		this.customerService = new CustomerService(this.customerRepository);
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

		Customer input = new Customer();
		input.setId(id);
		input.setFirstname(newFirstname);

		// When
		Customer output = this.customerService.patchCustomer(input);

		// Then
		assertThat(output.getId(), is(id));
		assertThat(output.getFirstname(), is(input.getFirstname()));
		assertThat(output.getLastname(), is(originLastname));
	}

	@Test
	public void is_Able_To_Patch_Last_Name() {

		// Given
		String newLastname = UUID.randomUUID().toString();
		Long id = this.get_ID_Of_The_First_Customer_In_Cuatomer_Repository();
		Customer origin = this.customerRepository.getOne(id);
		String originFirstname = origin.getFirstname();

		Customer input = new Customer();
		input.setId(id);
		input.setLastname(newLastname);

		// When
		Customer output = this.customerService.patchCustomer(input);

		// Then
		assertThat(output.getId(), is(id));
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