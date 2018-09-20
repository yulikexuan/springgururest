//: guru.springfamework.domain.services.IVendorService.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.model.VendorDTO;

import java.util.List;


public interface IVendorService {

    List<VendorDTO> getAllVendors();

    VendorDTO getVendorById(Long id);

    VendorDTO createNewVendor(VendorDTO vendorDTO);

    VendorDTO updateVendor(Long id, VendorDTO vendorDTO);

    VendorDTO patchVendor(Long id, VendorDTO vendorDTO);

    void deleteVendor(Long id);

}///:~