//: guru.springfamework.api.v1.controllers.CustomerController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping(Mappings.API_V1_CUSTOMERS)
public class CustomerController {

	private final ICustomerService customerService;

	@Autowired
	public CustomerController(ICustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping
	public ResponseEntity<CustomerListDTO> getAllCustomers() {
		List<CustomerDTO> customers = this.customerService.getAllCustomers();
		CustomerListDTO dto = new CustomerListDTO(customers);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
		CustomerDTO customer = this.customerService.getCustomerById(id);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(
			@RequestBody CustomerDTO customerDTO) {
		CustomerDTO newCreatedDTO = this.customerService.createNewCustomer(
				customerDTO);
		return new ResponseEntity<>(newCreatedDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> upateCustomer(
			@PathVariable Long id,
	        @RequestBody CustomerDTO customerDTO) {

		CustomerDTO updated = this.customerService.updateCustomer(id,
				customerDTO);

		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CustomerDTO> patchCustomer(
			@PathVariable Long id,
			@RequestBody CustomerDTO customerDTO) {

		CustomerDTO patched = this.customerService.patchCustomer(id,
				customerDTO);
		return new ResponseEntity<>(patched, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
		this.customerService.deleteCustomer(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}///:~