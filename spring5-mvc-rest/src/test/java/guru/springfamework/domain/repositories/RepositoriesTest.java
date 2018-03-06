//: guru.springfamework.domain.repositories.RepositoriesTest.java


package guru.springfamework.domain.repositories;


import guru.springfamework.domain.model.Category;
import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.model.Vendor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RepositoriesTest {

	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private ICustomerRepository customerRepository;

	@Autowired
	private IVendorRepository vendorRepository;

	@Test
	public void able_To_Get_All_Categories() {

		// When
		List<Category> categories = this.categoryRepository.findAll();

		// Then
		assertThat(categories.size(), greaterThan(0));
	}

	@Test
	public void able_To_Get_Category_By_Name() {

		// Given
		List<Category> categories = this.categoryRepository.findAll();
		String name = categories.get(0).getName();

		// When
		Category category = this.categoryRepository.findByName(name);

		// Then
		assertThat(category.getName(), is(name));
	}

	@Test
	public void able_To_Get_All_Customers() {

		// When
		List<Customer> customers = this.customerRepository.findAll();

		// Then
		assertThat(customers.size(), greaterThan(0));
	}

	@Test
	public void able_To_Get_Customers_By_Id() {

		// Given
		List<Customer> customers = this.customerRepository.findAll();
		Long id = customers.get(0).getId();

		// When
		Customer result = this.customerRepository.findById(id).orElseThrow(
				RuntimeException::new);

		// Then
		assertThat(result.getId(), is(id));
	}

	@Test
	public void able_To_Get_All_Vendors() {

		// When
		List<Vendor> vendors = this.vendorRepository.findAll();

		// Then
		assertThat(vendors.size(), greaterThan(0));
	}

	@Test
	public void able_To_Get_Vendor_By_Id() {

		// Given
		List<Vendor> allVendors = this.vendorRepository.findAll();
		Long id = allVendors.get(0).getId();

		// When
		Vendor vendor = this.vendorRepository.getOne(id);

		// Then
		assertThat(vendor.getId(), is(allVendors.get(0).getId()));
	}

}///:~