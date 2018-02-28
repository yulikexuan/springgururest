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

	@Override
	public Customer createNewCustomer(Customer customer) {
		Customer newCustomer = this.customerRepository.save(customer);
		return newCustomer;
	}

	@Override
	public Customer updateCustomer(Customer customer) {

		Long id = customer.getId();

		if (!this.customerRepository.existsById(id)) {
			String err = ">>>>>>> Customer Id not found! " + id;
			log.debug(err);
			throw new RuntimeException(err);
		}

		return this.customerRepository.save(customer);
	}

	@Override
	public Customer patchCustomer(Customer customer) {

		if (customer == null) {
			return customer;
		}

		Long id = customer.getId();

		return this.customerRepository.findById(id).map(existing -> {
			if (customer.getFirstname() != null) {
				existing.setFirstname(customer.getFirstname());
			} else if (customer.getLastname() != null) {
				existing.setLastname(customer.getLastname());
			}
			return this.customerRepository.save(existing);
		}).orElseThrow(() -> {
			String error = ">>>>>>> Customer does not exist: " + id;
			log.debug(error);
			return new RuntimeException(error);
		});
	}

}///:~