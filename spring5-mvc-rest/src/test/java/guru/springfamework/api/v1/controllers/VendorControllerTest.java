//: guru.springfamework.api.v1.controllers.VendorControllerTest.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.RestResponseEntityExceptionHandler;
import guru.springfamework.api.v1.mappers.IVendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.model.Vendor;
import guru.springfamework.domain.services.IVendorService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class VendorControllerTest {

	@Mock
	private IVendorService vendorService;

	@InjectMocks
	private VendorController vendorController;

	private IVendorMapper vendorMapper;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		RestResponseEntityExceptionHandler exceptionHandler =
				new RestResponseEntityExceptionHandler();
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.vendorController)
				.setControllerAdvice(exceptionHandler)
				.build();
		this.vendorMapper = IVendorMapper.INSTANCE;
	}

	@Test
	public void able_To_Get_All_Vendors() throws Exception {

		// Given
		List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());
		List<VendorDTO> vendorDTOs = vendors.stream()
				.map(this.vendorMapper::toVendorDTO)
				.collect(Collectors.toList());

		when(this.vendorService.getAllVendors())
				.thenReturn(vendorDTOs);

		// When
		this.mockMvc.perform(get(Mappings.API_V1_VENDORS)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.vendors", hasSize(2)));
	}

}///:~