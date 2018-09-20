//: guru.springfamework.api.v1.mappers.IVendorMapperTest.java


package guru.springfamework.api.v1.mappers;


import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.model.Vendor;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;


public class IVendorMapperTest {

    private IVendorMapper vendorMapper;


    @Before
    public void setUp() throws Exception {
        this.vendorMapper = IVendorMapper.INSTANCE;
    }

    @Test
    public void null_VendorDTO_For_Null_Vendor() {

        // When
        VendorDTO dto = this.vendorMapper.toVendorDTO(null);

        // Then
        assertThat(dto, nullValue());
    }

    @Test
    public void able_To_Map_Vendor_To_VendorDTO() {

        // Given
        Random random = new Random(System.currentTimeMillis());
        Long id = random.nextLong();
        String name = UUID.randomUUID().toString();
        Vendor vendor = new Vendor();
        vendor.setId(id);
        vendor.setName(name);

        // When
        VendorDTO dto = this.vendorMapper.toVendorDTO(vendor);

        // Then
        assertThat(dto.getName(), is(name));
        assertThat(dto.getVendorUrl(), endsWith("/" + id));
    }

}///:~