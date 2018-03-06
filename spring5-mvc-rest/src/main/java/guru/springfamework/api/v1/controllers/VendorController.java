//: guru.springfamework.api.v1.controllers.VendorController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.services.IVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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

}///:~