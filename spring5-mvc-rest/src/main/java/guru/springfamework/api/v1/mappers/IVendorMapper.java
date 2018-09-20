//: guru.springfamework.api.v1.mappers.IVendorMapper.java


package guru.springfamework.api.v1.mappers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.model.Vendor;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.util.UriComponentsBuilder;


@Mapper
public interface IVendorMapper {

    IVendorMapper INSTANCE = Mappers.getMapper(IVendorMapper.class);

    Vendor toVendor(VendorDTO vendorDTO);

    default VendorDTO toVendorDTO(Vendor vendor) {
        if (vendor == null) {
            return null;
        }

        Long id = vendor.getId();
        String url = (id == null) ? null : UriComponentsBuilder.newInstance().path(Mappings.API_V1_VENDORS).path("/").path(Long.toString(id)).toUriString();

        return VendorDTO.VendorDTOBuilder.getInstance().setName(vendor.getName()).setVendorUrl(url).createVendorDTO();
    }

}///:~