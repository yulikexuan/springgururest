//: guru.springfamework.api.v1.controllers.VendorController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.domain.services.IVendorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(Mappings.API_V1_VENDORS)
@Api(value="Vendors of Online Fruit Store",
		description="Operations pertaining to Vendors of Online Fruit Store")
public class VendorController {

	private final IVendorService vendorService;

	@Autowired
	public VendorController(IVendorService vendorService) {
		this.vendorService = vendorService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a list of vendors.", notes = "View all vendors.",
			response = VendorListDTO.class, produces = "application/json")
	public VendorListDTO getAllVendors() {
		List<VendorDTO> vendors = this.vendorService.getAllVendors();
		VendorListDTO dto = new VendorListDTO(vendors);
		return dto;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get a vendor according to it's ID",
			notes = "May not be existed",
			response = VendorDTO.class,
			produces = "application/json")
	public VendorDTO getVendorById(@PathVariable Long id) {
		return this.vendorService.getVendorById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new vendor.",
			notes = "Add a new ventor to Online Fruit Store.",
			response = VendorDTO.class,
			produces = "application/json",
			consumes = "application/json")
	public VendorDTO createNewVendor(@Valid @RequestBody VendorDTO vendorDTO) {
		return this.vendorService.createNewVendor(vendorDTO);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update an existed vendor.",
			notes = "Change the content of a ventor in Online Fruit Store.",
			response = VendorDTO.class,
			produces = "application/json",
			consumes = "application/json")
	public VendorDTO updateVendor(@PathVariable Long id,
	                              @RequestBody VendorDTO vendorDTO) {
		return this.vendorService.updateVendor(id, vendorDTO);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update a single property of an existed vendor.",
			notes = "Property will not be changed by null value",
			response = VendorDTO.class,
			produces = "application/json",
			consumes = "application/json")
	public VendorDTO patchVendor(@PathVariable Long id,
	                             @RequestBody VendorDTO vendorDTO) {
		return this.vendorService.patchVendor(id, vendorDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Remove a vendor from Online Fruit Store.",
			notes = "Nothing will happen if the vendor does not exist",
			consumes = "path variable for id")
	public Void deleteVendor(@PathVariable Long id) {
		this.vendorService.deleteVendor(id);
		return null;
	}

}///:~