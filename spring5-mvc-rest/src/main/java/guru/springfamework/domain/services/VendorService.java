//: guru.springfamework.domain.services.VendorService.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.mappers.IVendorMapper;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.model.Vendor;
import guru.springfamework.domain.repositories.IVendorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class VendorService implements IVendorService {

    private final IVendorRepository vendorRepository;
    private final IVendorMapper vendorMapper;

    @Autowired
    public VendorService(IVendorRepository vendorRepository, IVendorMapper vendorMapper) {
        this.vendorRepository = vendorRepository;
        this.vendorMapper = vendorMapper;
    }

    @Override
    public List<VendorDTO> getAllVendors() {

        return this.vendorRepository.findAll().stream().map(this.vendorMapper::toVendorDTO).collect(Collectors.toList());
    }

    @Override
    public VendorDTO getVendorById(Long id) {

        Vendor vendor = this.vendorRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        return this.vendorMapper.toVendorDTO(vendor);
    }

    @Override
    public VendorDTO createNewVendor(VendorDTO vendorDTO) {

        Vendor vendor = this.vendorMapper.toVendor(vendorDTO);
        Vendor saved = this.vendorRepository.save(vendor);
        VendorDTO result = this.vendorMapper.toVendorDTO(saved);

        return result;
    }

    @Override
    public VendorDTO updateVendor(Long id, VendorDTO vendorDTO) {

        if (!this.vendorRepository.existsById(id)) {
            String err = ">>>>>>> Vendor Id not found! " + id;
            log.debug(err);
            throw new ResourceNotFoundException(err);
        }

        Vendor vendor = this.vendorMapper.toVendor(vendorDTO);
        vendor.setId(id);
        Vendor saved = this.vendorRepository.save(vendor);
        return this.vendorMapper.toVendorDTO(saved);
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {

        if ((id == null) || (vendorDTO == null)) {
            return null;
        }

        Vendor patched = this.vendorRepository.findById(id).map(v -> {
            String newName = vendorDTO.getName();
            if (newName != null) {
                v.setName(newName);
            }
            return this.vendorRepository.save(v);
        }).orElseThrow(ResourceNotFoundException::new);

        return this.vendorMapper.toVendorDTO(patched);
    }

    @Override
    public void deleteVendor(Long id) {

        if (id == null) {
            return;
        }

        this.vendorRepository.deleteById(id);
    }

}///:~