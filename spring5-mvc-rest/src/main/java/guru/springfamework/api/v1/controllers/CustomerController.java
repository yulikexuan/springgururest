//: guru.springfamework.api.v1.controllers.CustomerController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.services.ICustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(description = "This is the REST Controller for Customer")
@RestController
@RequestMapping(Mappings.API_V1_CUSTOMERS)
public class CustomerController {

	private final ICustomerService customerService;

	@Autowired
	public CustomerController(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@ApiOperation(value="To get a list of customers.",
			notes="Will fetch all customers.")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CustomerListDTO getAllCustomers() {
		List<CustomerDTO> customers = this.customerService.getAllCustomers();
		CustomerListDTO dto = new CustomerListDTO(customers);
		return dto;
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO getCustomerById(@PathVariable Long id) {

		CustomerDTO customer = this.customerService.getCustomerById(id);
		return customer;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerDTO createNewCustomer(
			@RequestBody CustomerDTO customerDTO) {

		CustomerDTO newCreatedDTO = this.customerService.createNewCustomer(
				customerDTO);

		return newCreatedDTO;
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO upateCustomer(
			@PathVariable Long id,
	        @RequestBody CustomerDTO customerDTO) {

		CustomerDTO updated = this.customerService.updateCustomer(id,
				customerDTO);

		return updated;
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerDTO patchCustomer(
			@PathVariable Long id,
			@RequestBody CustomerDTO customerDTO) {

		CustomerDTO patched = this.customerService.patchCustomer(id,
				customerDTO);

		return patched;
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Void deleteCustomer(@PathVariable Long id) {
		this.customerService.deleteCustomer(id);
		return null;
	}

}///:~