//: guru.springfamework.domain.services.VendorServiceIT.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.mappers.IVendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.model.Vendor;
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
import static org.junit.Assert.assertThat;


@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class VendorServiceIT {

	@Autowired
	private ICustomerRepository customerRepository;

	@Autowired
	private ICategoryRepository categoryRepository;

	@Autowired
	private IVendorRepository vendorRepository;

	private VendorService vendorService;
	private IVendorMapper vendorMapper;

	@Before
	public void setUp() {

		log.debug(">>>>>>> Vendor data size: " +
				this.vendorRepository.findAll().size());
		log.debug(">>>>>>> Loading vendor data for testing ... ... ");

		new Bootstrap(this.categoryRepository, this.customerRepository,
				this.vendorRepository).run();

		log.debug(">>>>>>> Vendor data size after loading data: " +
				this.vendorRepository.findAll().size());

		this.vendorMapper = IVendorMapper.INSTANCE;
		this.vendorService = new VendorService(this.vendorRepository,
				this.vendorMapper);
	}

	private Long get_ID_Of_The_First_Vendor_In_Vendor_Repository() {
		return this.vendorRepository.findAll().get(0).getId();
	}

	@Test
	public void is_Able_To_Patch_Vendor_Name() {

		// Given
		String newVendorName = UUID.randomUUID().toString();
		Long id = this.get_ID_Of_The_First_Vendor_In_Vendor_Repository();

		Vendor origin = this.vendorRepository.getOne(id);
		String originVendorName = origin.getName();

		Vendor vendor = new Vendor();
		vendor.setId(id);
		vendor.setName(newVendorName);
		VendorDTO input = this.vendorMapper.toVendorDTO(vendor);

		// When
		VendorDTO output = this.vendorService.patchVendor(id, input);

		// Then
		assertThat(output.getVendorUrl(), containsString("/" + id));
		assertThat(output.getName(), is(vendor.getName()));
	}

	@Test
	public void able_To_Delete_Customer_By_Id() {

		// Given
		Long id = this.get_ID_Of_The_First_Vendor_In_Vendor_Repository();

		// When
		this.vendorService.deleteVendor(id);

		// Then
		boolean deleted = !this.vendorRepository.existsById(id);
		assertThat(deleted, is(true));
	}

}///:~