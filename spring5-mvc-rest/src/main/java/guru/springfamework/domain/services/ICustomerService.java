//: guru.springfamework.domain.services.ICustomerService.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;


public interface ICustomerService {
	List<CustomerDTO> getAllCustomers();
	CustomerDTO getCustomerById(Long id);
	CustomerDTO createNewCustomer(CustomerDTO customerDTO);
	CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO);
	CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);
	void deleteCustomer(Long id);
}///:~