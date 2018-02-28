//: guru.springfamework.api.v1.controllers.CustomerController.java


package guru.springfamework.api.v1.controllers;


import guru.springfamework.api.Mappings;
import guru.springfamework.api.v1.mappers.ICustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(Mappings.API_V1_CUSTOMERS)
public class CustomerController {

	private final ICustomerService customerService;
	private final ICustomerMapper customerMapper;


	@Autowired
	public CustomerController(ICustomerService customerService,
	                          ICustomerMapper customerMapper) {
		this.customerService = customerService;
		this.customerMapper = customerMapper;
	}

	@GetMapping
	public ResponseEntity<CustomerListDTO> getAllCustomers() {

		List<Customer> customers = this.customerService.getAllCustomers();
		List<CustomerDTO> cdtos = customers.stream()
				.map(this.customerMapper::toCustomerDTO)
				.collect(Collectors.toList());
		CustomerListDTO dto = new CustomerListDTO(cdtos);

		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
		Customer customer = this.customerService.getCustomerById(id);
		CustomerDTO dto = this.customerMapper.toCustomerDTO(customer);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CustomerDTO> createNewCustomer(
			@RequestBody CustomerDTO customerDTO) {

		Customer customer = this.customerMapper.toCustomer(customerDTO);
		Customer newCreated = this.customerService.createNewCustomer(customer);
		CustomerDTO newCreatedDTO = this.customerMapper.toCustomerDTO(
				newCreated);

		return new ResponseEntity<>(newCreatedDTO, HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CustomerDTO> upateCustomer(
			@PathVariable Long id,
	        @RequestBody CustomerDTO customerDTO) {

		Customer input = this.customerMapper.toCustomer(customerDTO);
		input.setId(id);

		Customer updated = this.customerService.updateCustomer(input);

		return new ResponseEntity<>(this.customerMapper.toCustomerDTO(updated),
				HttpStatus.OK);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<CustomerDTO> patchCustomer(
			@PathVariable Long id,
			@RequestBody CustomerDTO customerDTO) {

		Customer input = this.customerMapper.toCustomer(customerDTO);
		input.setId(id);

		Customer patched = this.customerService.patchCustomer(input);

		return new ResponseEntity<>(this.customerMapper.toCustomerDTO(patched),
				HttpStatus.OK);
	}

}///:~