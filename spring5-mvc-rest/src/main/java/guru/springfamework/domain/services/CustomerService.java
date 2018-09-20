//: guru.springfamework.domain.services.CustomerService.java


package guru.springfamework.domain.services;


import guru.springfamework.api.v1.mappers.ICustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.model.Customer;
import guru.springfamework.domain.repositories.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final ICustomerMapper customerMapper;

    @Autowired
    public CustomerService(ICustomerRepository customerRepository, ICustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return this.customerRepository.findAll().stream().map(this.customerMapper::toCustomerDTO).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        Customer customer = this.customerRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

        return this.customerMapper.toCustomerDTO(customer);
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = this.customerMapper.toCustomer(customerDTO);
        Customer newCustomer = this.customerRepository.save(customer);
        return this.customerMapper.toCustomerDTO(newCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {

        if (!this.customerRepository.existsById(id)) {
            String err = ">>>>>>> Customer Id not found! " + id;
            log.debug(err);
            throw new ResourceNotFoundException(err);
        }

        Customer input = this.customerMapper.toCustomer(customerDTO);
        input.setId(id);
        Customer saved = this.customerRepository.save(input);

        return this.customerMapper.toCustomerDTO(saved);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {

        if ((id == null) || (customerDTO == null)) {
            return null;
        }

        Customer target = this.customerRepository.findById(id).map(existing -> {
            if (customerDTO.getFirstname() != null) {
                existing.setFirstname(customerDTO.getFirstname());
            } else if (customerDTO.getLastname() != null) {
                existing.setLastname(customerDTO.getLastname());
            }
            return this.customerRepository.save(existing);
        }).orElseThrow(() -> {
            String error = ">>>>>>> Customer does not exist: " + id;
            log.debug(error);
            return new ResourceNotFoundException(error);
        });

        return this.customerMapper.toCustomerDTO(target);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (id == null) {
            return;
        }
        this.customerRepository.deleteById(id);
    }

}///:~