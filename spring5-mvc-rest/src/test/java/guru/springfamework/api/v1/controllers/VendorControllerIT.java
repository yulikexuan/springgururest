//: guru.springfamework.api.v1.controllers.VendorControllerIT.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.services.IVendorService;
import guru.springfamework.domain.services.VendorService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/*
 * @WebMvcTest:
 *   Annotation that can be used in combination with
 *   @RunWith(SpringRunner.class) for a typical Spring MVC test
 *     - Can be used when a test focuses only on Spring MVC components
 *     - Using this annotation will disable full auto-configuration and instead
 *       apply only configuration relevant to MVC tests
 *       - @Controller
 *       - @ControllerAdvice
 *       - @JsonComponent
 *       - Converter/GenericConverter
 *       - Filter
 *       - WebMvcConfigurer
 *       - HandlerMethodArgumentResolver beans
 *       - but not @Component, @Service or @Repository beans
 *     - By default, tests annotated with @WebMvcTest will also auto-configure
 *       Spring Security and MockMvc
 *       - include support for HtmlUnit WebClient and Selenium WebDriver
 *     - Typically @WebMvcTest is used in combination with @MockBean or @Import
 *       to create any collaborators required by your @Controller beans
 *
 *     - If looking to load the full application configuration and use MockMVC,
 *       should consider @SpringBootTest combined with @AutoConfigureMockMvc
 *       rather than this annotation
 *
 *     - The controllers property of @WebMvcTest specifies the controllers to
 *       test
 *         - May be left blank if all @Controller beans should be added to the
 *           application context
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = {VendorController.class})
public class VendorControllerIT {

	/*
	 * @MockBean is an annotation that can be used to add mocks to a Spring
	 * ApplicationContext
	 *
	 * This annotation is going to tell Mockito and Spring to create a Mackito
	 * Mock and inject it into our test here
	 *
	 * So rather than doing the init-mocks, we are asking the Spring Contest to
	 * create that Mock for us and inject it into our class
	 *   - Can be used as a class level annotation or on fields in either
	 *     @Configuration classes, or test classes that are @RunWith the
	 *     SpringRunner
	 *   - Mocks can be registered by type or by bean name
	 *   - Any existing single bean of the same type defined in the context
	 *     will be replaced by the mock
	 *   - If no existing bean is defined a new one will be added
	 *   - When @MockBean is used on a field, as well as being registered in
	 *     the application context, the mock will also be injected into the
	 *     field
	 */
	@MockBean
	private IVendorService vendorService;

	/*
	 * Specifing that want the vendor controller class to be brought in and
	 * wired up
	 */
	@Autowired
	private MockMvc mockMvc;

	private VendorDTO vendorDTO_0;
	private VendorDTO vendorDTO_1;

	private String url_1 = Mappings.API_V1_VENDORS + "/1";
	private String url_2 = Mappings.API_V1_VENDORS + "/2";

	@Before
	public void setUp() throws Exception {
		this.vendorDTO_0 = VendorDTO.VendorDTOBuilder.getInstance()
				.setName(UUID.randomUUID().toString())
				.setVendorUrl(url_1)
				.createVendorDTO();
		this.vendorDTO_1 = VendorDTO.VendorDTOBuilder.getInstance()
				.setName(UUID.randomUUID().toString())
				.setVendorUrl(url_2)
				.createVendorDTO();
	}

	@Ignore
	@Test
	public void able_To_Get_All_Vendors() throws Exception {

		// Given
		VendorListDTO vendorListDTO = new VendorListDTO(Arrays.asList(
				this.vendorDTO_0, this.vendorDTO_1));

		given(this.vendorService.getAllVendors()).willReturn(
				vendorListDTO.getVendors());

		// When & Then
		this.mockMvc.perform(get(Mappings.API_V1_VENDORS)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.vendors", hasSize(2)));
	}

	@Ignore
	@Test
	public void getVendorById() throws Exception {

		// Given
		given(this.vendorService.getVendorById(anyLong()))
				.willReturn(this.vendorDTO_0);

		// When & Then
		this.mockMvc.perform(get(url_1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name",
						Matchers.is(this.vendorDTO_0.getName())));
	}

	@Test
	public void createNewVendor() throws Exception {
	}

	@Test
	public void updateVendor() throws Exception {
	}

	@Test
	public void patchVendor() throws Exception {
	}

	@Test
	public void deleteVendor() throws Exception {
	}

}///:~