//: guru.springfamework.domain.services.ICustomerService.java


package guru.springfamework.domain.services;


import guru.springfamework.domain.model.Customer;

import java.util.List;


public interface ICustomerService {
	List<Customer> getAllCustomers();
	Customer getCustomerById(Long id);
	Customer createNewCustomer(Customer customer);
	Customer updateCustomer(Customer customer);
	Customer patchCustomer(Customer customer);
}///:~