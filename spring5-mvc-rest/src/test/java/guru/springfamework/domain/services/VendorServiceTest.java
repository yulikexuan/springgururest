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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
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
		when(this.vendorRepository.findAll()).thenReturn(vendors);

		List<VendorDTO> vendorDTOs = Arrays.asList(
				mock(VendorDTO.class),
				mock(VendorDTO.class));
		when(this.vendorMapper.toVendorDTO(vendors.get(0)))
				.thenReturn(vendorDTOs.get(0));
		when(this.vendorMapper.toVendorDTO(vendors.get(1)))
				.thenReturn(vendorDTOs.get(1));

		// When
		List<VendorDTO> result = this.vendorService.getAllVendors();

		// Then
		assertThat(result.size(), is(2));
		assertThat(result.get(0), is(vendorDTOs.get(0)));
		assertThat(result.get(1), is(vendorDTOs.get(1)));
	}

	@Test
	public void able_To_Get_The_Vendor_By_Id() {

		// Given
		when(this.vendorRepository.findById(this.id))
				.thenReturn(Optional.of(this.vendor));

		when(this.vendorMapper.toVendorDTO(this.vendor))
				.thenReturn(this.vendorDTO);

		// When
		VendorDTO result = this.vendorService.getVendorById(this.id);

		// Then
		assertThat(result, is(this.vendorDTO));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void able_To_Throw_Exception_When_Cannot_Find_The_Vendor_By_Id() {

		// Given
		when(this.vendorRepository.findById(this.id))
				.thenReturn(Optional.empty());

		// When
		this.vendorService.getVendorById(this.id);
	}

	@Test
	public void able_To_Create_New_Vendor() {

		// Given
		when(this.vendorMapper.toVendor(this.vendorDTO))
				.thenReturn(this.vendor);

		Vendor saved = mock(Vendor.class);
		when(this.vendorRepository.save(this.vendor))
				.thenReturn(saved);

		VendorDTO expect = mock(VendorDTO.class);
		when(this.vendorMapper.toVendorDTO(saved))
				.thenReturn(expect);

		// When
		VendorDTO result = this.vendorService.createNewVendor(this.vendorDTO);

		// Then
		assertThat(result, is(expect));
	}

	@Test
	public void able_To_Update_An_Existing_Vendor() {

		// Given
		when(this.vendorRepository.existsById(this.id)).thenReturn(true);

		Vendor source = mock(Vendor.class);
		when(this.vendorMapper.toVendor(this.vendorDTO)).thenReturn(source);

		Vendor saved = mock(Vendor.class);
		when(this.vendorRepository.save(source)).thenReturn(saved);

		VendorDTO expect = mock(VendorDTO.class);
		when(this.vendorMapper.toVendorDTO(saved)).thenReturn(expect);

		// When
		VendorDTO result = this.vendorService.updateVendor(this.id,
				this.vendorDTO);

		// Then
		assertThat(result, is(expect));
		verify(source).setId(this.id);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void can_Throw_Exception_If_Updating_Non_Existing_Vendor() {

		// Given
		when(this.vendorRepository.existsById(this.id)).thenReturn(false);

		// When
		this.vendorService.updateVendor(this.id, this.vendorDTO);
	}

	@Test
	public void able_To_Patch_Vendor() {

		// Given
		when(this.vendorRepository.findById(this.id)).thenReturn(
				Optional.of(this.vendor));

		String newVendorName = UUID.randomUUID().toString();
		when(this.vendorDTO.getName()).thenReturn(newVendorName);

		this.vendor.setName(newVendorName);

		Vendor patched = mock(Vendor.class);
		when(this.vendorRepository.save(this.vendor)).thenReturn(patched);

		VendorDTO expect = mock(VendorDTO.class);
		when(this.vendorMapper.toVendorDTO(patched)).thenReturn(expect);

		// When
		VendorDTO result = this.vendorService.patchVendor(this.id,
				this.vendorDTO);

		// Then
		assertThat(result, is(expect));
		assertThat(this.vendor.getName(), is(newVendorName));
	}

	@Test(expected = ResourceNotFoundException.class)
	public void can_Throw_Exception_When_Patch_Non_Existing_Vendor() {

		// Given
		when(this.vendorRepository.findById(this.id))
				.thenReturn(Optional.empty());

		// When
		this.vendorService.patchVendor(this.id, this.vendorDTO);

		// Then
		verify(this.vendorDTO, never()).getName();
		verify(this.vendorRepository, never()).save(any(Vendor.class));
	}

	@Test
	public void deleteVendor() {

		// Given

		// When
		this.vendorService.deleteVendor(this.id);

		// Then
		verify(this.vendorRepository).deleteById(this.id);
	}

	@Test
	public void not_Able_To_Delete_Vendor_By_Null_Id() {

		// When
		this.vendorService.deleteVendor(null);

		// Then
		verify(this.vendorRepository, never()).deleteById(null);

	}

}///:~