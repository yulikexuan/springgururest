//: guru.springfamework.domain.services.CustomerService.java


package guru.springfamework.domain.services;


import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.repositories.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class CustomerService implements ICustomerService {

	private final ICustomerRepository customerRepository;

	@Autowired
	public CustomerService(ICustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public List<Customer> getAllCustomers() {
		return this.customerRepository.findAll();
	}

	@Override
	public Customer getCustomerById(Long id) {

		Customer customer = this.customerRepository
				.findById(id)
				.orElseThrow(RuntimeException::new);

		return customer;
	}

}///:~