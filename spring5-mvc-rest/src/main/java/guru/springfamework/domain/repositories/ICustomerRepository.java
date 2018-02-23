//: guru.springfamework.domain.repositories.ICustomerRepository.java


package guru.springfamework.domain.repositories;


import guru.springfamework.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ICustomerRepository extends JpaRepository<Customer, Long> {
}///:~