//: guru.springfamework.domain.services.VendorServiceTest.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.mappers.IVendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.model.Vendor;
import guru.springfamework.domain.repositories.IVendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


public class VendorServiceTest {

    @Mock
    private IVendorRepository vendorRepository;

    @Mock
    private IVendorMapper vendorMapper;

    @InjectMocks
    private VendorService vendorService;

    private Vendor vendor;
    private Random random;
    private Long id;
    private String vendorName;

    @Mock
    private VendorDTO vendorDTO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.random = new Random(System.currentTimeMillis());
        this.id = this.random.nextLong();
        this.vendorName = UUID.randomUUID().toString();
        this.vendor = new Vendor();
        this.vendor.setId(this.id);
        this.vendor.setName(this.vendorName);
    }

    @Test
    public void able_To_Get_All_Vendors() {

        // Given
        List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor());
        vendors.get(0).setId(1L);
        vendors.get(1).setId(2L);
        given(this.vendorRepository.findAll()).willReturn(vendors);

        List<VendorDTO> vendorDTOs = Arrays.asList(mock(VendorDTO.class), mock(VendorDTO.class));
        given(this.vendorMapper.toVendorDTO(vendors.get(0))).willReturn(vendorDTOs.get(0));
        given(this.vendorMapper.toVendorDTO(vendors.get(1))).willReturn(vendorDTOs.get(1));

        // When
        List<VendorDTO> result = this.vendorService.getAllVendors();

        // Then
        then(this.vendorRepository).should().findAll();
        then(this.vendorRepository).shouldHaveNoMoreInteractions();

        then(this.vendorMapper).should().toVendorDTO(vendors.get(0));
        then(this.vendorMapper).should().toVendorDTO(vendors.get(1));
        then(this.vendorMapper).shouldHaveNoMoreInteractions();

        assertThat(result.size(), is(2));
        assertThat(result.get(0), is(vendorDTOs.get(0)));
        assertThat(result.get(1), is(vendorDTOs.get(1)));
    }

    @Test
    public void able_To_Get_The_Vendor_By_Id() {

        // Given
        given(this.vendorRepository.findById(this.id)).willReturn(Optional.of(this.vendor));

        given(this.vendorMapper.toVendorDTO(this.vendor)).willReturn(this.vendorDTO);

        // When
        VendorDTO result = this.vendorService.getVendorById(this.id);

        // Then
        then(this.vendorRepository).should().findById(this.id);
        then(this.vendorRepository).shouldHaveNoMoreInteractions();

        then(this.vendorMapper).should().toVendorDTO(this.vendor);
        then(this.vendorDTO).shouldHaveNoMoreInteractions();

        assertThat(result, is(this.vendorDTO));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void able_To_Throw_Exception_When_Cannot_Find_The_Vendor_By_Id() {

        // Given
        given(this.vendorRepository.findById(this.id)).willReturn(Optional.empty());

        // When
        this.vendorService.getVendorById(this.id);

        // Then
        then(this.vendorRepository).should().findById(this.id);
        then(this.vendorRepository).shouldHaveNoMoreInteractions();

        then(this.vendorMapper).should(never()).toVendorDTO(any(Vendor.class));
        then(this.vendorMapper).shouldHaveZeroInteractions();
    }

    @Test
    public void able_To_Create_New_Vendor() {

        // Given
        given(this.vendorMapper.toVendor(this.vendorDTO)).willReturn(this.vendor);

        Vendor saved = mock(Vendor.class);
        given(this.vendorRepository.save(this.vendor)).willReturn(saved);

        VendorDTO expect = mock(VendorDTO.class);
        given(this.vendorMapper.toVendorDTO(saved)).willReturn(expect);

        // When
        VendorDTO result = this.vendorService.createNewVendor(this.vendorDTO);

        // Then
        then(this.vendorMapper).should().toVendor(this.vendorDTO);
        then(this.vendorMapper).should().toVendorDTO(saved);
        then(this.vendorMapper).shouldHaveNoMoreInteractions();

        then(this.vendorRepository).should().save(this.vendor);
        then(this.vendorRepository).shouldHaveNoMoreInteractions();

        assertThat(result, is(expect));
    }

    @Test
    public void able_To_Update_An_Existing_Vendor() {

        // Given
        given(this.vendorRepository.existsById(this.id)).willReturn(true);

        Vendor source = mock(Vendor.class);
        given(this.vendorMapper.toVendor(this.vendorDTO)).willReturn(source);

        Vendor saved = mock(Vendor.class);
        given(this.vendorRepository.save(source)).willReturn(saved);

        VendorDTO expect = mock(VendorDTO.class);
        given(this.vendorMapper.toVendorDTO(saved)).willReturn(expect);

        // When
        VendorDTO result = this.vendorService.updateVendor(this.id, this.vendorDTO);

        // Then
        then(this.vendorRepository).should().existsById(this.id);
        then(this.vendorMapper).should().toVendor(this.vendorDTO);
        then(source).should().setId(this.id);
        then(this.vendorRepository).should().save(source);
        then(this.vendorMapper).should().toVendorDTO(saved);

        then(this.vendorMapper).shouldHaveNoMoreInteractions();
        then(this.vendorRepository).shouldHaveNoMoreInteractions();
        then(source).shouldHaveNoMoreInteractions();

        assertThat(result, is(expect));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void can_Throw_Exception_If_Updating_Non_Existing_Vendor() {

        // Given
        given(this.vendorRepository.existsById(this.id)).willReturn(false);

        // When
        this.vendorService.updateVendor(this.id, this.vendorDTO);

        // Then
        then(this.vendorRepository).should().existsById(this.id);
        then(this.vendorRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    public void able_To_Patch_Vendor() {

        // Given
        given(this.vendorRepository.findById(this.id)).willReturn(Optional.of(this.vendor));

        String newVendorName = UUID.randomUUID().toString();
        given(this.vendorDTO.getName()).willReturn(newVendorName);

        this.vendor.setName(newVendorName);

        Vendor patched = mock(Vendor.class);
        given(this.vendorRepository.save(this.vendor)).willReturn(patched);

        VendorDTO expect = mock(VendorDTO.class);
        given(this.vendorMapper.toVendorDTO(patched)).willReturn(expect);

        // When
        VendorDTO result = this.vendorService.patchVendor(this.id, this.vendorDTO);

        // Then
        assertThat(result, is(expect));
        assertThat(this.vendor.getName(), is(newVendorName));
    }

    @Test//(expected = ResourceNotFoundException.class)
    public void can_Throw_Exception_When_Patch_Non_Existing_Vendor() {

        try {
            // Given
            given(this.vendorRepository.findById(this.id)).willReturn(Optional.empty());

            // When
            this.vendorService.patchVendor(this.id, this.vendorDTO);

            fail("Should throw ResourceNotFoundException.");
        } catch (Exception e) {
            // Then
            assertThat(e instanceof ResourceNotFoundException, is(true));
        } finally {
            // Then
            then(this.vendorDTO).should(never()).getName();
            then(this.vendorRepository).should(never()).save(any(Vendor.class));
        }
    }

    @Test
    public void deleteVendor() {

        // Given

        // When
        this.vendorService.deleteVendor(this.id);

        // Then
        then(this.vendorRepository).should().deleteById(this.id);
    }

    @Test
    public void not_Able_To_Delete_Vendor_By_Null_Id() {

        // When
        this.vendorService.deleteVendor(null);

        // Then
        then(this.vendorRepository).should(never()).deleteById(null);
    }

}///:~