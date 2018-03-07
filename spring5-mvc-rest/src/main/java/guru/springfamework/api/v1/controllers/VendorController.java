//: guru.springfamework.api.v1.controllers.VendorController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.services.IVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(Mappings.API_V1_VENDORS)
public class VendorController {

	private final IVendorService vendorService;

	@Autowired
	public VendorController(IVendorService vendorService) {
		this.vendorService = vendorService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public VendorListDTO getAllVendors() {
		List<VendorDTO> vendors = this.vendorService.getAllVendors();
		VendorListDTO dto = new VendorListDTO(vendors);
		return dto;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO getVendorById(@PathVariable Long id) {
		return this.vendorService.getVendorById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public VendorDTO createNewVendor(@RequestBody VendorDTO vendorDTO) {
		return this.vendorService.createNewVendor(vendorDTO);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO updateVendor(@PathVariable Long id,
	                              @RequestBody VendorDTO vendorDTO) {
		return this.vendorService.updateVendor(id, vendorDTO);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public VendorDTO patchVendor(@PathVariable Long id,
	                             @RequestBody VendorDTO vendorDTO) {
		return this.vendorService.patchVendor(id, vendorDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Void deleteVendor(@PathVariable Long id) {
		this.vendorService.deleteVendor(id);
		return null;
	}

}///:~