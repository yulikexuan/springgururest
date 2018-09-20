//: guru.springfamework.domain.repositories.CustomerRepositoryIT.java


package guru.springfamework.domain.repositories;


import guru.springfamework.domain.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerRepositoryIT {

    @Autowired
    private ICustomerRepository customerRepository;

    @Test
    public void able_To_Get_All_Customers() {

        // When
        List<Customer> customers = this.customerRepository.findAll();

        // Then
        assertThat(customers.size(), greaterThan(0));
    }

    @Test
    public void able_To_Get_Customers_By_Id() {

        // Given
        List<Customer> customers = this.customerRepository.findAll();
        Long id = customers.get(0).getId();

        // When
        Customer result = this.customerRepository.findById(id).orElseThrow(RuntimeException::new);

        // Then
        assertThat(result.getId(), is(id));
    }

}///:~