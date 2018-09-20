//: guru.springfamework.api.v1.controllers.VendorControllerTest.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.RestResponseEntityExceptionHandler;
import guru.springfamework.api.v1.mappers.IVendorMapper;
import guru.springfamework.api.v1.mappers.ObjectToJsonMapper;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import javax.print.attribute.standard.Media;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class VendorControllerTest {

    @Mock
    private IVendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private IVendorMapper vendorMapper;
    private MockMvc mockMvc;

    private Random random;
    private Long id;
    private String name;

    private Vendor vendor_1;
    private Vendor vendor_2;

    private VendorDTO vendorDTO_1;
    private VendorDTO vendorDTO_2;

    private String idUrl;
    private VendorDTO input;
    private String rawInput;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        RestResponseEntityExceptionHandler exceptionHandler = new RestResponseEntityExceptionHandler();
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.vendorController).setControllerAdvice(exceptionHandler).build();
        this.vendorMapper = IVendorMapper.INSTANCE;

        this.random = new Random(System.currentTimeMillis());
        this.id = this.random.nextLong();
        this.name = UUID.randomUUID().toString();

        this.vendor_1 = new Vendor();
        this.vendor_1.setId(this.id);
        this.vendor_1.setName(this.name);

        this.vendor_2 = new Vendor();
        this.vendor_2.setId(this.random.nextLong());
        this.vendor_2.setName(UUID.randomUUID().toString());

        this.vendorDTO_1 = this.vendorMapper.toVendorDTO(this.vendor_1);
        this.vendorDTO_2 = this.vendorMapper.toVendorDTO(this.vendor_2);

        this.idUrl = UriComponentsBuilder.fromUriString(Mappings.API_V1_VENDORS).path("/").path(Long.toString(this.id)).toUriString();

        String newName = UUID.randomUUID().toString();
        this.input = VendorDTO.VendorDTOBuilder.getInstance().setName(newName).createVendorDTO();
        this.rawInput = ObjectToJsonMapper.toJson(this.input);
    }

    private MockHttpServletRequestBuilder getRequestBuilder(String url) {
        return MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void able_To_Get_All_Vendors() throws Exception {

        // Given
        List<VendorDTO> vendorDTOs = Arrays.asList(this.vendorDTO_1, this.vendorDTO_2);

        when(this.vendorService.getAllVendors()).thenReturn(vendorDTOs);

        // When
        this.mockMvc.perform(get(Mappings.API_V1_VENDORS).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.vendors", hasSize(2))).andExpect(jsonPath("$.vendors[0].name", is(this.vendor_1.getName()))).andExpect(jsonPath("$.vendors[1].name", is(this.vendor_2.getName())));
    }

    @Test
    public void able_To_Get_Vendor_By_Its_Id() throws Exception {

        // Given
        when(this.vendorService.getVendorById(this.id)).thenReturn(this.vendorDTO_1);

        // When
        this.mockMvc.perform(get(this.idUrl).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(this.vendor_1.getName())));
    }

    @Test
    public void able_To_Create_New_Vendor() throws Exception {

        // Given
        when(this.vendorService.createNewVendor(this.input)).thenReturn(this.vendorDTO_1);

        // When
        this.mockMvc.perform(post(Mappings.API_V1_VENDORS).contentType(MediaType.APPLICATION_JSON).content(this.rawInput)).andExpect(status().isCreated()).andExpect(jsonPath("$.name", is(this.vendor_1.getName())));

    }

    @Test
    public void able_To_Update_An_Existing_Vendor() throws Exception {

        // Given
        when(this.vendorService.updateVendor(this.id, this.input)).thenReturn(this.vendorDTO_1);

        // When
        this.mockMvc.perform(put(this.idUrl).contentType(MediaType.APPLICATION_JSON).content(this.rawInput)).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(this.vendor_1.getName())));
    }

    @Test
    public void able_To_Patch_An_Existing_Vendor() throws Exception {

        // Given
        when(this.vendorService.patchVendor(this.id, this.input)).thenReturn(this.vendorDTO_1);

        // When
        this.mockMvc.perform(patch(this.idUrl).contentType(MediaType.APPLICATION_JSON).content(this.rawInput)).andExpect(status().isOk()).andExpect(jsonPath("$.name", is(this.vendor_1.getName())));
    }

    @Test
    public void able_To_Delete_An_Existing_Vendor() throws Exception {

        // Given

        // When
        this.mockMvc.perform(delete(this.idUrl).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        // Then
        verify(this.vendorService).deleteVendor(this.id);
    }

}///:~